package com.bycc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bycc.dto.caseAlarm.CaseAlarmDto;
import com.bycc.service.caseAlarm.CaseAlarmService;

/**
 * 
 * @description 告警
 * @author liuxunhua
 * @date 2017年7月13日 上午10:13:50
 *
 */
@Controller
@RequestMapping("/caseAlarm")
public class CaseAlarmCtrl {
	@Autowired
	private CaseAlarmService service;
	
	@RequestMapping("/find")
	@ResponseBody
	public List<CaseAlarmDto> find() throws Exception {
		return service.find();
	}
}
