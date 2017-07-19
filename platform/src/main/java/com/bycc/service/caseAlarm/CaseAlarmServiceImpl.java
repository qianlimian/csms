package com.bycc.service.caseAlarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.smartframework.utils.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bycc.dao.BdmPoliceDao;
import com.bycc.dao.CasePeopleDao;
import com.bycc.dao.CaseRecordDao;
import com.bycc.dto.caseAlarm.CaseAlarmDto;
import com.bycc.entity.BdmPolice;
import com.bycc.entity.BdmPoliceStation;
import com.bycc.entity.CasePeople;
import com.bycc.entity.CaseRecord;
import com.bycc.enumitem.CaseAlarmType;
import com.bycc.enumitem.CaseHandle;
import com.bycc.service.permission.PermissionService;
import com.bycc.tools.HttpUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class CaseAlarmServiceImpl implements CaseAlarmService{
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private BdmPoliceDao policeDao;
	@Autowired
	private CaseRecordDao caseRecordDao;
	@Autowired
	private CasePeopleDao casePeopleDao;
	

	/**
	 * 
	 * @description 获取告警信息 
	 * @author liuxunhua
	 * @date 2017年7月13日 上午10:16:08
	 *
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<CaseAlarmDto> find() throws Exception {
		List<CaseAlarmDto> dtos = new ArrayList<>();
		BdmPolice police = policeDao.findByUserId(permissionService.findUser().getId());
		List<CaseRecord> caseRecords = caseRecordDao.findByMasterPoliceOrSlavePolice(police, police);
		for (CaseRecord caseRecord : caseRecords) {
			if(CaseHandle.HANDLING.equals(caseRecord.getCaseHandle())){
				List<CasePeople> casePeoples = casePeopleDao.findByCaseRecordId(caseRecord.getId());
				if (casePeoples != null && casePeoples.size() > 0) {
					BdmPoliceStation policeStation = police.getPoliceStation();
					String result = HttpUtil.sendGet("http://" + policeStation.getIp() + ":8088/locate/alarm", "utf-8");
					Map<Integer, Map<String, Object>> alarmMap = new HashMap<>();
					if (!"Failure".equals(result)) {
						alarmMap = JsonHelper.getBean(result, new TypeReference<HashMap<Integer, Map<String, Object>>>(){});
					}
					for (CasePeople casePeople : casePeoples) {
						//防串供告警
						if (casePeople.getStrap() != null) {
							Integer strapId = casePeople.getStrap().getCode();
							for (Map<String, Object> value : alarmMap.values()) {  
							    if (value.get("tag1Id") != null && value.get("tag1Id").equals(strapId)) {
							    	CaseAlarmDto dto = new CaseAlarmDto();
							    	dto.setCaseAlarmType(CaseAlarmType.FCGGJ.value());
							    	dto.setCaseName(caseRecord.getCaseName());
							    	List<Integer> tagIds = (List<Integer>) value.get("tag2Id");
							    	String partnerName = "";
							    	for (Integer tagId : tagIds) {
										CasePeople partner = casePeopleDao.findByStrapCode(tagId);
										if (partner != null) {
											partnerName = partnerName + partner.getName() + ",";
										}
									}
							    	dto.setContent("涉案人：" + casePeople.getName() + "与" + partnerName.substring(0, partnerName.length() - 1) + "串供");
							    	dtos.add(dto);
								}
							} 
						}
						
						//羁押超时告警
						if ((casePeople.getLeaveDate() == null || casePeople.getLeaveDate().getTime() < casePeople.getEnterDate().getTime()) && System.currentTimeMillis() - casePeople.getEnterDate().getTime() > 8 * 60 * 60 * 10000) {
							CaseAlarmDto dto = new CaseAlarmDto();
					    	dto.setCaseAlarmType(CaseAlarmType.JYCSGJ.value());
					    	dto.setCaseName(caseRecord.getCaseName());
					    	dto.setContent("涉案人：" + casePeople.getName() + "羁押超时");
					    	dtos.add(dto);
						}
					}
				}
			}
		}
		return dtos;
	}

}
