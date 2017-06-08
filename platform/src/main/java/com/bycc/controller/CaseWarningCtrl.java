package com.bycc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bycc.dto.caseWarning.CaseWarningDto;
import com.bycc.service.caseWarning.CaseWarningService;

/**
 * 受立案监督
 */
@Controller
@RequestMapping("/caseWarnings")
public class CaseWarningCtrl {
	
	@Autowired
	private CaseWarningService service;

    /**
     * 首页
     */
    @RequestMapping
    public String index() {
        return "/pages/caseWarning/index";
    }
    
    /**
     * 查询
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> queryRank(@RequestParam("queryBean") QueryBean qb) {
        List<CaseWarningDto> dtos = service.query(qb);
        Map<String, Object> map = new HashMap<>();
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }
}
