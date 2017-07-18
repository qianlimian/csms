/**
 *
 */
package com.bycc.service.caseScore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.bycc.dao.*;
import com.bycc.entity.*;
import com.bycc.service.permission.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.common.kendo.Sort;
import org.smartframework.manager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bycc.dto.caseScore.CaseScoreDto;
import com.bycc.dto.caseScore.CaseScoreItemDto;
import com.bycc.dto.scoreRank.CaseScoreRankDto;
import com.bycc.dto.scoreRank.PoliceScoreRankDto;
import com.bycc.dto.scoreRank.PoliceStationScoreRankDto;

/**
 * @author gaoningbo
 * @description
 * @date 2017年4月25日
 */
@Service
public class CaseScoreServiceImpl implements CaseScoreService {
	
	@PersistenceContext  
    private EntityManager em;
    @Autowired
    private CaseScoreDao dao;
    @Autowired
    private CaseDao caseDao;
    @Autowired
    private BdmEvaluationDao evalDao;
    @Autowired
    private CaseScoreItemDao itemDao;
    @Autowired
    private BdmPoliceDao policeDao;
    @Autowired
    private PermissionService permissionService;

    private Logger logger = LoggerFactory.getLogger(CaseScoreServiceImpl.class);

    /**
     * 打分
     */
    public CaseScoreDto save(CaseScoreDto dto) {
        CaseScore entity;
        Case caze = caseDao.findOne(dto.getCaseId());

        if (dto.getId() == null) {
            entity = dto.toEntity();
            entity.setCaze(caze);
        } else {
            entity = dao.findOne(dto.getId());
            dto.toEntity(entity);
        }
        //保存案件积分
        CaseScore result = dao.save(entity);

        //保存案件积分项
        List<CaseScoreItem> items = new ArrayList<>();
        for (CaseScoreItemDto itemDto : dto.getItemDtos()) {
            if (!itemDto.isSelected()) {
                if (itemDto.getId() != null) {
                    try {
                        if (itemDao.findOne(itemDto.getId()) == null) {
                        } else {
                            itemDao.delete(itemDto.getId());
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            } else {
                CaseScoreItem item = null;
                if (itemDto.getId() == null) {
                    item = itemDto.toEntity();
                } else {
                    item = itemDao.findOne(itemDto.getId());
                    if (item == null) {
                        item = itemDto.toEntity();
                    } else {
                        itemDto.toEntity(item);
                    }
                }
                item.setCaseScore(result);
                item.setEval(itemDto.getEvalId() != null ? evalDao.getOne(itemDto.getEvalId()) : null);
                item = itemDao.save(item);

                items.add(item);
            }
        }
        result.setCaseScoreItems(items);
        return CaseScoreDto.toDto(result);
    }

    public CaseScoreDto findById(Integer id) {
        CaseScore score = dao.getOne(id);
        return CaseScoreDto.toDto(score);
    }

    /**
     * 根据案件记录查询打分信息
     */
    public CaseScoreDto findByCaseId(Integer id) {
        CaseScore score = dao.findByCazeId(id);
        if (score == null) {
            Case caze = caseDao.findOne(id);
            return CaseScoreDto.toDto(caze, null);
        } else {
            return CaseScoreDto.toDto(score);
        }
    }

    /**
     * 子表查询
     */
    public List<CaseScoreItemDto> findSubsById(Integer id) {
        List<CaseScoreItem> items = itemDao.findByCaseScoreId(id);
        List<CaseScoreItemDto> dtos = new ArrayList<>();
        for (CaseScoreItem item : items) {
            dtos.add(CaseScoreItemDto.toDto(item));
        }
        return dtos;
    }

    /**
     * 我的积分||积分详情
     */
    public List<CaseScoreRankDto> query(QueryBean qb, Integer policeId) {

        //获取当前用户id
        if (policeId == null) {
            policeId = policeDao.findByUserId(User.getCurrentUser().getUserId()).getId();
        }

        //传参
        String[] array = new String[]{"(caze.masterPolice.id = :policeId or caze.slavePolice.id = :policeId)"};
        //赋值
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("policeId", policeId);
        if (qb.getSort() == null || qb.getSort().size() == 0) {
	        Sort sort = new Sort();
	        sort.setField("totalScore");
	        sort.setDir("desc");
	        List<Sort> sorts = new ArrayList<Sort>();
	        sorts.add(sort);
	        qb.setSort(sorts);
        }
        List<CaseScore> entities = dao.findByQueryBeanCondition(array, map, qb);
        List<CaseScoreRankDto> dtos = new ArrayList<>();
        for (CaseScore entity : entities) {
            dtos.add(CaseScoreRankDto.toDto(entity));
        }
        return dtos;
    }

    /**
     * 民警积分排名
     */
    public List<PoliceScoreRankDto> bdmPoliceQueryRank(QueryBean qb) {
    	List<PoliceScoreRankDto> scoreRankDtos = new ArrayList<PoliceScoreRankDto>();
    	//获取所有单位
    	List<BdmPoliceStation> policeStations = new ArrayList<BdmPoliceStation>();
    	BdmPoliceStation policeStation = permissionService.findPoliceStation();
    	if (policeStation != null) {
    		policeStations.add(policeStation);
    		List<BdmPoliceStation> subPoliceStations = permissionService.findSubPoliceStations();
        	for (BdmPoliceStation bdmPoliceStation : subPoliceStations) {
        		policeStations.add(bdmPoliceStation);
    		}
		}
    	//获取所有警员
        Set<Integer> policeIds = new HashSet<Integer>();
    	for (BdmPoliceStation bdmPoliceStation : policeStations) {
			List<BdmPolice> bdmPolices = bdmPoliceStation.getPolices();
			for (BdmPolice bdmPolice : bdmPolices) {
				policeIds.add(bdmPolice.getId());
			}
		}

        // 构造查询语句
 		StringBuilder queryBuilder = new StringBuilder("select police_id_, police_name_, police_station_name_, sum(total_score_) totalScore from view_police_score_statistics where police_id_ in :policeIds");
		//grid过滤参数
		Map<String, Object> filters = qb.getFilterMap();
		if (filters != null) {
			queryBuilder.append(" and " + filters.get("keys"));
		}
		queryBuilder.append(" group by police_id_ ");
		//grid排序参数
		String sorts = qb.getSortString();
		if (sorts != null) {
			queryBuilder.append("order by " + sorts);
		} else {
			queryBuilder.append("order by totalScore desc");
		}
		// 构建查询对象
		Query query = em.createNativeQuery(queryBuilder.toString());
		if (filters != null) {
			//查询条件参数赋值
			Map<String, Object> values = (Map) filters.get("values");
			for (String key : values.keySet()) {
				query.setParameter(key, values.get(key).toString());
			}
		}
		query.setParameter("policeIds", new ArrayList<Integer>(policeIds));
 		//总记录数
        qb.setTotal(query.getResultList().size());
        //查询结果
		List<Object> results = query.setMaxResults(qb.getPageSize()).setFirstResult(qb.getSkip()).getResultList();
		
 		for (Object object : results) {
 			PoliceScoreRankDto dto = new PoliceScoreRankDto();
 			Object[] row = (Object[]) object;
			dto.setPoliceId((Integer) row[0]);
 			dto.setPoliceName((String) row[1]);
 			dto.setPoliceStation((String) row[2]);
 			dto.setTotalScore((BigDecimal) row[3]);
 			scoreRankDtos.add(dto);
		}
        
        return scoreRankDtos;
    }
    
    /**
     * 警局积分排名
     */
	@Override
	public List<PoliceStationScoreRankDto> bdmPoliceStationQueryRank(QueryBean qb) {
    	List<PoliceStationScoreRankDto> scoreRankDtos = new ArrayList<PoliceStationScoreRankDto>();
    	//获取所有单位
    	List<Integer> stationIds = new ArrayList<Integer>();
    	BdmPoliceStation policeStation = permissionService.findPoliceStation();
    	if (policeStation != null) {
    		stationIds.add(policeStation.getId());
    		List<BdmPoliceStation> subPoliceStations = permissionService.findSubPoliceStations();
        	for (BdmPoliceStation bdmPoliceStation : subPoliceStations) {
        		stationIds.add(bdmPoliceStation.getId());
    		}
		}
    	// 构造查询语句
 		StringBuilder queryBuilder = new StringBuilder("select unit_id_, unit_name_, unit_area_type_, sum(total_score_) totalScore from view_police_station_score_statistics where unit_id_ in :stationIds");
 		//grid过滤参数
		Map<String, Object> filters = qb.getFilterMap();
		if (filters != null) {
			queryBuilder.append(" and " + filters.get("keys"));
		}
		queryBuilder.append(" group by unit_id_ ");
		//grid排序参数
		String sorts = qb.getSortString();
		if (sorts != null) {
			queryBuilder.append("order by " + sorts);
		} else {
			queryBuilder.append("order by totalScore desc");
		}
		// 构建查询对象
		Query query = em.createNativeQuery(queryBuilder.toString());
		if (filters != null) {
			//查询条件参数赋值
			Map<String, Object> values = (Map) filters.get("values");
			for (String key : values.keySet()) {
				query.setParameter(key, values.get(key).toString());
			}
		}
 		query.setParameter("stationIds", new ArrayList<Integer>(stationIds));
 		//总记录数
        qb.setTotal(query.getResultList().size());
        //查询结果
		List<Object> results = query.setMaxResults(qb.getPageSize()).setFirstResult(qb.getSkip()).getResultList();
		
 		for (Object object : results) {
 			PoliceStationScoreRankDto dto = new PoliceStationScoreRankDto();
 			Object[] row = (Object[]) object;
			dto.setPoliceStationId((Integer) row[0]);
 			dto.setPoliceStationName((String) row[1]);
 			dto.setAreaType((String) row[2]);
 			dto.setTotalScore((BigDecimal) row[3]);
 			scoreRankDtos.add(dto);
		}
    	
		return scoreRankDtos;
	}

    /**
     * 警局积分详情
     */
	@Override
	public List<CaseScoreRankDto> bdmPoliceStationQuery(QueryBean qb, Integer policeStationId) {
        //传参
        String[] array = new String[]{"(caze.masterUnit.id = :policeStationId or caze.slaveUnit.id = :policeStationId)"};
        //赋值
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("policeStationId", policeStationId);
        if (qb.getSort() == null || qb.getSort().size() == 0) {
	        Sort sort = new Sort();
	        sort.setField("totalScore");
	        sort.setDir("desc");
	        List<Sort> sorts = new ArrayList<Sort>();
	        sorts.add(sort);
	        qb.setSort(sorts);
        }
        List<CaseScore> entities = dao.findByQueryBeanCondition(array, map, qb);
        List<CaseScoreRankDto> dtos = new ArrayList<>();
        for (CaseScore entity : entities) {
            dtos.add(CaseScoreRankDto.toDto(entity));
        }
        return dtos;
	}

	/**
	 * 
	 * @description 案件积分 
	 * @author liuxunhua
	 * @date 2017年7月10日 上午10:49:42
	 *
	 * @param qb
	 * @return
	 */
	@Override
	public List<CaseScoreRankDto> caseQueryRank(QueryBean qb) {
		List<PoliceScoreRankDto> scoreRankDtos = new ArrayList<PoliceScoreRankDto>();
    	//获取所有单位
    	List<BdmPoliceStation> policeStations = new ArrayList<BdmPoliceStation>();
    	BdmPoliceStation policeStation = permissionService.findPoliceStation();
    	if (policeStation != null) {
    		policeStations.add(policeStation);
    		List<BdmPoliceStation> subPoliceStations = permissionService.findSubPoliceStations();
        	for (BdmPoliceStation bdmPoliceStation : subPoliceStations) {
        		policeStations.add(bdmPoliceStation);
    		}
		}
    	Set<Integer> caseIds = new HashSet<Integer>();
    	for (BdmPoliceStation bdmPoliceStation : policeStations) {
    		List<Case> cases = caseDao.findByMasterUnitOrSlaveUnit(bdmPoliceStation, bdmPoliceStation);
    		for (Case caze : cases) {
    			caseIds.add(caze.getId());
			}
		}
    	
    	//传参
        String[] array = new String[]{"(caze.id in (:caseIds))"};
        //赋值
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("caseIds", caseIds);
        if (qb.getSort() == null || qb.getSort().size() == 0) {
        	Sort sort = new Sort();
            sort.setField("totalScore");
            sort.setDir("desc");
            List<Sort> sorts = new ArrayList<Sort>();
            sorts.add(sort);
            qb.setSort(sorts);
		}
        List<CaseScore> entities = dao.findByQueryBeanCondition(array, map, qb);
        List<CaseScoreRankDto> dtos = new ArrayList<>();
        for (CaseScore entity : entities) {
            dtos.add(CaseScoreRankDto.toDto(entity));
        }
        return dtos;
    	
    	
	}


}
