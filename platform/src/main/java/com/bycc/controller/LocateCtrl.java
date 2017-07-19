/**
 * 
 */
package com.bycc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bycc.dto.locate.LocateDto;
import com.bycc.service.locate.LocateService;

/**
 * @description
 * @author gaoningbo
 * @date 2017年6月29日
 * 
 */
@Controller
@RequestMapping("/locate")
public class LocateCtrl {

	@Autowired
	LocateService ser;
	/**
	 * 
	 * @description 进入房间时查询信息并记录进入时间
	 * @author liuxunhua
	 * @date 2017年7月5日 上午8:43:44
	 *
	 */
	@RequestMapping("/findLocate")
	@ResponseBody
	public LocateDto findLocate(@RequestParam("tagId") Integer strapCode, @RequestParam("devId") Integer stationCode, @RequestParam("person") Integer person){
		return ser.getLocateInfo(strapCode, stationCode, person);
	}
	
	/**
	 * 
	 * @description 离开房间时记录离开时间
	 * @author liuxunhua
	 * @date 2017年7月5日 上午8:43:59
	 *
	 */
	@RequestMapping("/leave")
	@ResponseBody
	public void leave(@RequestParam("traceId") Integer traceId){
		ser.leave(traceId);
	}
}
