package com.bycc.controller;

import com.bycc.dto.BdmVideoCgDto;
import com.bycc.service.bdmVideoCg.BdmVideoCgService;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bdmVideoCategories")
public class BdmVideoCgCtrl {

    @Autowired
    private BdmVideoCgService service;

    /**
     * 首页
     */
    @RequestMapping
    public String index() {
        return "/pages/bdmVideoCg/index";
    }

    /**
     * 新增|编辑页
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/pages/bdmVideoCg/edit";
    }

    /**
     * 按条件查询
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {

        List<BdmVideoCgDto> dtos = service.query(qb);
        Map<String, Object> map = new HashMap<String, Object>();
        //总记录数
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }
    /**
     * 查询所有类别
     */
    @RequestMapping("/findAllCg")
    @ResponseBody
    public List<BdmVideoCgDto> findAllCg(){
    	return service.query(null);
    }
    
    /**
     * 按id查询
     */
    @RequestMapping("/findById")
    @ResponseBody
    public BdmVideoCgDto findById(@RequestParam("id") Integer bdmVideoCgId) {
        return service.findById(bdmVideoCgId);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BdmVideoCgDto save(@RequestBody BdmVideoCgDto dto) throws Exception {
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
