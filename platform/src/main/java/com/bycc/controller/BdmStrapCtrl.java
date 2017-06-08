package com.bycc.controller;

import com.bycc.dto.bdmHandlingArea.BdmStrapDto;
import com.bycc.service.bdmStrap.BdmStrapService;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 手环控制器
 * User: yumingzhe
 * Time: 2017-5-11 10:48
 */
@Controller
@RequestMapping("/bdmStrap")
public class BdmStrapCtrl {
	@Autowired
	private BdmStrapService service;

	/**
	 * 查询手环
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
		List<BdmStrapDto> dtos = service.query(qb);
		Map<String, Object> map = new HashMap<>();
		// 总记录数
		map.put("total", qb.getTotal());
		map.put("items", dtos);

		return map;
	}

	/**
	 * 查询未使用的手环
	 *
	 * @return 未使用的手环
	 */
	@RequestMapping(value = "/findUnusedStraps", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findUnusedStraps(@RequestParam("queryBean") QueryBean qb) {
		List<BdmStrapDto> dtos = service.findUnusedStraps(qb);
		Map<String, Object> map = new HashMap<>();
		// 总记录数
		map.put("total", qb.getTotal());
		map.put("items", dtos);

		return map;
	}
}
