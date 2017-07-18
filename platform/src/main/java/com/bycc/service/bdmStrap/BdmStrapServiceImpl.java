package com.bycc.service.bdmStrap;

import com.bycc.dao.BdmHandlingAreaDao;
import com.bycc.dao.BdmStrapDao;
import com.bycc.dto.bdmHandlingArea.BdmStrapDto;
import com.bycc.entity.BdmHandlingArea;
import com.bycc.entity.BdmPoliceStation;
import com.bycc.entity.BdmStrap;
import com.bycc.enumitem.UsageStatus;
import com.bycc.service.permission.PermissionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartframework.common.kendo.Filter;
import org.smartframework.common.kendo.LogicFilter;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-5-4 10:45
 */
@Service
public class BdmStrapServiceImpl implements BdmStrapService {
	@Autowired
	private BdmStrapDao bdmStrapDao;
	
	@Autowired
	private BdmHandlingAreaDao handingAreaDao;
	
	@Autowired
	private PermissionService permissionSer;
	
	private Logger logger = LoggerFactory.getLogger(BdmStrapServiceImpl.class);

	@Override
	public List<BdmStrapDto> findUnusedStraps(QueryBean qb) {		
		//办案区ID
		Integer id= getHandingAreaId();
		if(id==null){
			logger.error("该登陆用户未绑定办案区！");
			return null;
		}
		String[] hql = new String[]{"status = :status","handlingArea.id = :id"};
		Map<String, Object> param = new HashMap<String, Object>() {
			{
				put("status", UsageStatus.UNUSED);
				put("id",id);
			}
		};
		
		List<BdmStrap> straps = bdmStrapDao.findByQueryBeanCondition(hql, param, qb);
		return straps.stream().map(BdmStrapDto::toDto).collect(Collectors.toList());
	}

	@Override
	public List<BdmStrapDto> query(QueryBean qb) {
		//办案区ID
		Integer id= getHandingAreaId();
		if(id==null){
			logger.error("该登陆用户未绑定办案区！");
			return null;
		}
		String[] hql = new String[]{"handlingArea.id = :id"};
		Map<String, Object> param = new HashMap<String, Object>() {
			{				
				put("id",id);
			}
		};
		
		List<BdmStrap> straps = bdmStrapDao.findByQueryBeanCondition(hql, param, qb);	
		List<BdmStrapDto> dtos = new ArrayList<>();
		for (BdmStrap strap : straps) {
			dtos.add(BdmStrapDto.toDto(strap));
		}
		return dtos;
	}
	/**	
	 * @description 获得办案区ID
	 * @date 2017年6月27日下午3:07:39
	 */
	private Integer getHandingAreaId(){
		//登录用户所在派出所、办案区
		BdmPoliceStation policeStation=permissionSer.findPoliceStation();
		List<BdmHandlingArea> handlingAreas=handingAreaDao.findByPoliceStation(policeStation);
		if(handlingAreas.isEmpty()){
			return null;
		}		
		return handlingAreas.get(0).getId();
	}
	
}
