package com.bycc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bycc.dto.CaseDto;
import com.bycc.service.caze.CaseService;

/**
 * @author gaoningbo
 * @description
 * @date 2017年4月14日
 */
@Controller
@RequestMapping("/cases")
public class CaseCtrl {

    @Autowired
    CaseService service;

    /**
     * 首页
     */
    @RequestMapping("/{type}")
    public String index(@PathVariable("type") String type) {
        return "/pages/case/" + type + "/index";
    }

    /**
     * 查看
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/pages/case/partial/edit";
    }

    /**
     * 查询案件
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam(value="type",required=false) String type, @RequestParam("queryBean") QueryBean qb) {
        List<CaseDto> dtos = service.query(type, qb);
        Map<String, Object> map = new HashMap<>();
        // 总记录数
        map.put("total", qb.getTotal());
        map.put("items", dtos);

        return map;
    }
    
    /**
     * 
     * @description 按ID查询案件
     * @author liuxunhua
     * @date 2017年5月24日 上午8:02:35
     *
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
	@ResponseBody
	public CaseDto findById(@RequestParam("id") Integer id) {
		return service.findById(id);
	}
}
