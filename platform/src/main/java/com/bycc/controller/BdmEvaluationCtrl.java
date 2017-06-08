package com.bycc.controller;


import com.bycc.dto.BdmEvaluationDto;
import com.bycc.service.bdmEvaluation.BdmEvaluationService;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/17 0017.
 */
@Controller
@RequestMapping("/bdmEvaluations")
public class BdmEvaluationCtrl {

    @Autowired
    private BdmEvaluationService service;

    /**
     * 首页
     */
    @RequestMapping
    public String index(Model model) {
        return "/pages/bdmEvaluation/index";
    }

    /**
     * 新增|编辑页
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/pages/bdmEvaluation/edit";
    }

    /**
     * 按条件查询
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
        //
        List<BdmEvaluationDto> dtos = service.query(qb);
        Map<String, Object> map = new HashMap<String, Object>();
        //总记录数
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }

    /**
     * 按id查找
     */
    @RequestMapping("/findById")
    @ResponseBody
    public BdmEvaluationDto findById(@RequestParam("id") Integer id) {
        //查询用户信息
        return service.findById(id);
    }

    /**
     * 保存
	 */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BdmEvaluationDto save(@RequestBody BdmEvaluationDto dto) throws Exception {
        return service.save(dto);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@RequestParam("ids") String ids) {
        service.delete(ids);
    }
    
    @RequestMapping("/findAllScoreStandards")
    @ResponseBody
    public List<BdmEvaluationDto> findAllScoreStandards(){
    	return service.findAllScoreStandards();
    }
    
}
