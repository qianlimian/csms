package com.bycc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bycc.dto.BdmClassificationDto;
import com.bycc.service.bdmClassification.BdmClassificationService;

/**
 * 案件分级标准
 */
@Controller
@RequestMapping("/bdmClassifications")
public class BdmClassificationCtrl {

    @Autowired
    private BdmClassificationService service;

    /**
     * 首页
     */
    @RequestMapping
    public String index() {
        return "/pages/bdmClassification/index";
    }

    /**
     * 新增|编辑页
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/pages/bdmClassification/edit";
    }

    /**
     * 按条件查询
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {

        List<BdmClassificationDto> dtos = service.query(qb);
        Map<String, Object> map = new HashMap<String, Object>();
        //总记录数
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }

    /**
     * 按id查询
     */
    @RequestMapping("/findById")
    @ResponseBody
    public BdmClassificationDto findById(@RequestParam("id") Integer bdmClassificationId) {
        return service.findById(bdmClassificationId);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BdmClassificationDto save(@RequestBody BdmClassificationDto dto) throws Exception {
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
}
