/**
 * 
 */
package com.bycc.service.locate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bycc.dao.BdmStationDao;
import com.bycc.dao.CasePeopleDao;
import com.bycc.dao.CasePeopleTraceDao;
import com.bycc.dto.locate.CameraDto;
import com.bycc.dto.locate.LocateDto;
import com.bycc.entity.BdmCamera;
import com.bycc.entity.BdmRoom;
import com.bycc.entity.BdmStation;
import com.bycc.entity.CasePeople;
import com.bycc.entity.CasePeopleTrace;

/**
 * @description
 * @author gaoningbo
 * @date 2017年6月29日
 * 
 */
@Service
public class locateServiceImpl implements LocateService {

	@Autowired
	BdmStationDao stationDao;

	@Autowired
	CasePeopleDao peopleDao;
	
	@Autowired 
	CasePeopleTraceDao peopleTraceDao;

	public LocateDto getLocateInfo(Integer strapCode, Integer stationCode, Integer person) {

		CasePeople people = peopleDao.findByStrapCode(strapCode);
		BdmStation station = stationDao.findByCode(stationCode);

		if (people == null || station == null) {
			return null;
		}
		
		LocateDto dto = new LocateDto();
		dto.setCasePeopleId(people.getId());
		dto.setCaseRecoredId(people.getCaseRecord() != null ? people.getCaseRecord().getId() : null);
		dto.setTagId(strapCode);

		BdmRoom room = station.getRoom();
		if (room != null) {
			
			//记录人员轨迹
			if (person == 0) {
				CasePeopleTrace peopleTrace = new CasePeopleTrace();
				peopleTrace.setCasePeople(people);
				peopleTrace.setEnterTime(new Date());
				peopleTrace.setRoomName(room.getName());
				peopleTrace = peopleTraceDao.save(peopleTrace);
				dto.setTraceId(peopleTrace.getId());
			}
			
			dto.setRoomType(room.getCode());
			List<BdmCamera> cameras = room.getCameras();
			List<CameraDto> dtos = new ArrayList<>();

			for (BdmCamera entity : cameras) {
				dtos.add(CameraDto.toDto(entity));
			}
			dto.setCameraDtos(dtos);
		}

		return dto;
	}

	@Override
	public void leave(Integer traceId) {
		CasePeopleTrace peopleTrace = peopleTraceDao.findOne(traceId);
		if (peopleTrace.getLeaveTime() == null) {
			peopleTrace.setLeaveTime(new Date());
			peopleTraceDao.save(peopleTrace);
		}
	}

}
