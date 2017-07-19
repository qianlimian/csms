package com.bycc.service.caseAlarm;

import java.util.List;

import com.bycc.dto.caseAlarm.CaseAlarmDto;

public interface CaseAlarmService {
	public List<CaseAlarmDto> find() throws Exception;
}
