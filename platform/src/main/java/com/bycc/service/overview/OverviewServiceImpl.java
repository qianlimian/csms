package com.bycc.service.overview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.smartframework.utils.helper.JsonHelper;
import org.smartframework.utils.helper.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bycc.dao.BdmHandlingAreaDao;
import com.bycc.dao.BdmRoomDao;
import com.bycc.dao.BdmStationDao;
import com.bycc.dao.BdmStrapDao;
import com.bycc.dao.CasePeopleDao;
import com.bycc.dao.CaseRecordDao;
import com.bycc.dto.bdmRoom.BdmRoomDto;
import com.bycc.dto.caseRegister.CasePeopleDto;
import com.bycc.dto.locate.RFParams;
import com.bycc.dto.overview.CaseRecordDto4Overview;
import com.bycc.dto.overview.PoliceStationDto4Overview;
import com.bycc.entity.BdmCamera;
import com.bycc.entity.BdmHandlingArea;
import com.bycc.entity.BdmPoliceStation;
import com.bycc.entity.BdmRoom;
import com.bycc.entity.BdmStation;
import com.bycc.entity.BdmStrap;
import com.bycc.entity.CasePeople;
import com.bycc.entity.CaseRecord;
import com.bycc.enumitem.CaseHandle;
import com.bycc.service.permission.PermissionService;
import com.bycc.tools.HttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class OverviewServiceImpl implements OverviewService {
	
	@Autowired
	PermissionService permissionService;

	@Autowired
	private BdmHandlingAreaDao handlingAreaDao;
	
	@Autowired
	private BdmRoomDao roomDao;
	
	@Autowired
	private CaseRecordDao recordDao;
	
	@Autowired
	private CasePeopleDao peopleDao;
	
	@Autowired
	private BdmStationDao stationDao;
	
	@Autowired
	private BdmStrapDao strapDao;
	
	/**
	 * 
	 * @description 根据登录用户查出派出所和正在办理的案件及涉案人
	 *              1.如果是市局用户，返回市局、分局和所有派出所
	 *              2.如果是分局用户，返回该用户所属分局及辖区内派出所
	 *              3.如果是派出所用户，返回该用户所属派出所
	 * @author liuxunhua
	 * @date 2017年5月3日 上午11:21:01
	 *
	 */
	@Override
	public String findPoliceStationList() {
		
		List<PoliceStationDto4Overview> results = new ArrayList<PoliceStationDto4Overview>();
		//根据登录用户查询派出所列表
		List<BdmPoliceStation> stations = permissionService.findSubPoliceStations();
		BdmPoliceStation policeStation = permissionService.findPoliceStation();
		stations.add(0, policeStation);
		
		//封装树数据
		for (BdmPoliceStation bdmPoliceStation : stations) {
			PoliceStationDto4Overview policeStationDto = new PoliceStationDto4Overview();
			List<BdmHandlingArea> bdmHandlingAreas = handlingAreaDao.findByPoliceStation(bdmPoliceStation);
			if (!bdmHandlingAreas.isEmpty()) {
				BdmHandlingArea bdmHandlingArea = bdmHandlingAreas.get(0);

				policeStationDto.setId(bdmHandlingArea.getId());
				policeStationDto.setName(bdmPoliceStation.getName());

				List<CaseRecordDto4Overview> recordDtos = new ArrayList<CaseRecordDto4Overview>();
				List<CaseRecord> records = recordDao.findByMasterUnitId(bdmPoliceStation.getId());
				for (CaseRecord caseRecord : records) {
					if (CaseHandle.HANDLING.equals(caseRecord.getCaseHandle())) {
						CaseRecordDto4Overview recordDto = new CaseRecordDto4Overview();
						recordDto.setId(caseRecord.getId());
						recordDto.setName(caseRecord.getCaseName());
						List<CasePeople> peoples = peopleDao.findByCaseRecordId(caseRecord.getId());
						List<CasePeopleDto> dtos = new ArrayList<CasePeopleDto>();
						for (CasePeople casePeople : peoples) {
							dtos.add(CasePeopleDto.toDto(casePeople));
						}
						recordDto.setPeopleDtos(dtos);
						recordDtos.add(recordDto);
					}
				}
				policeStationDto.setRecordDtos(recordDtos);
				results.add(policeStationDto);
			}
		}
		
		return JsonHelper.getJson(results);
	}

	/**
	 * 
	 * @description 根据房间获取摄像头直播地址
	 * @author liuxunhua
	 * @date 2017年5月3日 下午2:17:14
	 *
	 */
	@Override
	public Map<Integer, String> findCamerasByRoom(Integer roomId) {
		Map<Integer, String> resultMap = new HashMap<Integer, String>();
		BdmRoom room = roomDao.findOne(roomId);
		if (room != null) {
			List<BdmCamera> cameras = room.getCameras();
			for (BdmCamera bdmCamera : cameras) {
				if (room.getHandlingArea().getPoliceStation().getIp() != null && bdmCamera.getIp() != null) {
					String url = "http://" + room.getHandlingArea().getPoliceStation().getIp() + ":8888/video/startLive?cIp=" + 
		                    bdmCamera.getIp() + "&cUsername=" + bdmCamera.getUserName() + "&cPassword="+ bdmCamera.getPassword();
					resultMap.put(bdmCamera.getId(), HttpUtil.sendGet(url, "UTF-8"));
				}
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * @description 获取房间布局
	 * @author liuxunhua
	 * @date 2017年6月27日 上午9:32:05
	 *
	 * @param roomId
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> findRoomLayout(Integer areaId, Integer peopleId) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BdmHandlingArea bdmHandlingArea = handlingAreaDao.findOne(areaId);
		BdmPoliceStation bdmPoliceStation = bdmHandlingArea.getPoliceStation();
		boolean locateState = true;
		//获取办案区布局
		List<BdmRoom> rooms =  roomDao.findByHandlingArea(bdmHandlingArea);
        List<BdmRoomDto> dtos = new ArrayList<>();
        for (BdmRoom room : rooms) {
            BdmRoomDto dto = BdmRoomDto.toDto(room);
            dtos.add(dto);
        }
		
		//获取定位信息
		Map<Integer, Object> locateInfo = new HashMap<Integer,Object>();
		CasePeople casePeople = null;
		if (peopleId != null) {
			casePeople = peopleDao.findOne(peopleId);
		}
		if (dtos.size() > 0) {
			if (!StringHelper.isAllEmpty(bdmPoliceStation.getIp())) {
				String locateResult = HttpUtil.sendGet("http://" + bdmPoliceStation.getIp() + ":8088/locate/get", "utf-8");
		        if (!"Failure".equals(locateResult)) {
		        	Map<Integer, RFParams> locateMap = JsonHelper.getBean(locateResult, new TypeReference<HashMap<Integer, RFParams>>(){}); 
		    		if (casePeople != null) {
		    			for (Map.Entry<Integer, RFParams> entry : locateMap.entrySet()) {  
		    				BdmStrap strap = strapDao.findByCode(entry.getValue().getTagId());
		    				if (strap != null) {
		    					CasePeople people = peopleDao.findByStrap(strap);
		        				if (casePeople.equals(people) && entry.getValue().isActivate()) {
		        					BdmStation station = stationDao.findByCode(entry.getKey());
		        					BdmRoom room = station.getRoom();
		        		        	locateInfo.put(room.getId(), CasePeopleDto.toDto(people));
		        				}
							}
		    	        }
		    		} else {
		    			for (Map.Entry<Integer, RFParams> entry : locateMap.entrySet()) { 
		    				if (entry.getValue().isActivate()) {
		    					BdmStrap strap = strapDao.findByCode(entry.getValue().getTagId());
		    					if (strap != null) {
		    						CasePeople people = peopleDao.findByStrap(strap);
		        					BdmStation station = stationDao.findByCode(entry.getKey());
		        					BdmRoom room = station.getRoom();
		        		        	locateInfo.put(room.getId(), CasePeopleDto.toDto(people));
								}
		    				}
		    	        }
		    		}
				} else {
					locateState = false;
				}
			} else {
				locateState = false;
			}
		}
		
        resultMap.put("roomDtos", dtos);
        resultMap.put("locateInfo", locateInfo);
        resultMap.put("locateState", locateState);
        
		return resultMap;
	}

    /**
     * @description 判读是否启用告警
     */
    @Override
    public Boolean enableWarning() {
        return true;
    }

    /**
	 * @description 获取定位告警
	 */
    @Override
    public Map<String, Object> findLocateWarning() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("case", null);
        resultMap.put("warning", "单人讯问！！！");
        return resultMap;
    }

}
