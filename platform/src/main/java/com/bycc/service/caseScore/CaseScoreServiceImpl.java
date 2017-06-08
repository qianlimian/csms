/**
 *
 */
package com.bycc.service.caseScore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bycc.dao.BdmEvaluationDao;
import com.bycc.dao.BdmPoliceDao;
import com.bycc.dao.CaseRecordDao;
import com.bycc.dao.CaseScoreDao;
import com.bycc.dao.CaseScoreItemDao;
import com.bycc.dto.CaseScoreDto;
import com.bycc.dto.CaseScoreItemDto;
import com.bycc.dto.ScoreRankDto;
import com.bycc.entity.BdmEvaluation;
import com.bycc.entity.BdmPolice;
import com.bycc.entity.CaseRecord;
import com.bycc.entity.CaseScore;
import com.bycc.entity.CaseScoreItem;
import com.bycc.enumitem.CaseType;
import com.bycc.enumitem.RiskLevel;

/**
 * @author gaoningbo
 * @description
 * @date 2017年4月25日
 */
@Service
public class CaseScoreServiceImpl implements CaseScoreService {

    @Autowired
    private CaseScoreDao dao;
    @Autowired
    private CaseRecordDao caseRecordDao;
    @Autowired
    private BdmEvaluationDao evalDao;
    @Autowired
    private CaseScoreItemDao itemDao;
    @Autowired
    private BdmPoliceDao policeDao;
    
    /**
     * 打分
     */
    public CaseScoreDto save(CaseScoreDto dto) {
        CaseScore entity = null;
        if (dto.getId() == null) {
            entity = dto.toEntity();
            entity.setCaseRecord(caseRecordDao.findOne(dto.getCaseRecordId()));
        } else {
            entity = dao.findOne(dto.getId());
            dto.toEntity(entity);
        }
        //保存案件积分
        CaseScore result = dao.save(entity);
        //保存案件积分项
        List<CaseScoreItemDto> itemDtos = dto.getItemDtos();
        List<CaseScoreItem> items = new ArrayList<>();
        for (CaseScoreItemDto itemDto : itemDtos) {
            CaseScoreItem item = null;
            if (itemDto.getId() == null) {
                item = itemDto.toEntity();
            } else {
                item = itemDao.findOne(itemDto.getId());
                itemDto.toEntity(item);
            }
            item.setCaseScore(result);
            item.setEval(itemDto.getEvalId() != null ? evalDao.getOne(itemDto.getEvalId()) : null);
            item = itemDao.save(item);
            
            items.add(item);
        }
        result.setCaseScoreItems(items);
        return CaseScoreDto.toDto(result);
    }

    public CaseScoreDto findById(Integer id) {
        CaseScore score = dao.getOne(id);
        return CaseScoreDto.toDto(score);
    }

    /**
     * 根据办案记录查询打分信息
     */
    public CaseScoreDto findByCaseRecordId(Integer id) {
        CaseScore score = dao.findByCaseRecordId(id);
        if (score == null) {
            CaseRecord record = caseRecordDao.findOne(id);
            List<BdmEvaluation> evals = evalDao.findAll();
            List<BdmEvaluation> resultEvals=new ArrayList<>();
            CaseRecord cr= caseRecordDao.findOne(id);
            if(cr.getCaze()!=null){
            	CaseType caseType=cr.getCaze().getCaseType();
            	RiskLevel riskLevel=cr.getCaze().getRiskLevel();
            	
            	/**
            	 * 根据案件风险和案件类型进行了筛选
            	 */
            	for(BdmEvaluation eval:evals){
            		String standard=eval.getStandard();
            		if(standard.contains("案件风险")){
            			if(riskLevel!=null&&standard.contains(riskLevel.value())){
            				resultEvals.add(eval);
            			}
            		}else if(caseType!=null&&standard.endsWith("案件")){
            			if(standard.contains(caseType.value()))
            				resultEvals.add(eval);            			 
            		}else{
            			resultEvals.add(eval);
            		}            		
            	}            	
            }
            
            return CaseScoreDto.toDto(record, resultEvals);
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
    public List<CaseScoreDto> query(QueryBean qb, Integer policeId) {

        //获取当前用户id
        if (policeId == null) {
            policeId = policeDao.findByUserId(User.getCurrentUser().getUserId()).getId();
        }

        //传参
        String[] array = new String[]{"(caseRecord.masterPolice.id = :policeId or caseRecord.slavePolice.id = :policeId)"};
        //赋值
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("policeId", policeId);

        List<CaseScore> entities = dao.findByQueryBeanCondition(array, map, qb);
        List<CaseScoreDto> dtos = new ArrayList<>();
        for (CaseScore entity : entities) {
            dtos.add(CaseScoreDto.toDto(entity));
        }
        return dtos;
    }

    /**
     * 积分排名
     */
    public List<ScoreRankDto> queryRank(QueryBean qb) {
        //获取所有警员
        List<BdmPolice> polices = policeDao.findByQueryBean(qb);
        List<ScoreRankDto> scoreDtos = new ArrayList<>();
        for (BdmPolice police : polices) {
            ScoreRankDto scoreDto = ScoreRankDto.toDto(police);
            //计算积分
            BigDecimal totalScore = BigDecimal.ZERO;
            //主办积分
            List<CaseRecord> records = caseRecordDao.findByMasterPoliceId(police.getId());
            for (CaseRecord record : records) {
                CaseScore cs = dao.findByCaseRecordId(record.getId());
                if(cs!=null){
                	 totalScore = totalScore.add(cs.getTotalScore());
                }               
            }
            //从办积分.....
            //其他积分.....
            scoreDto.setTotalScore(totalScore);
            scoreDtos.add(scoreDto);
        }
        return scoreDtos;
    }


}
