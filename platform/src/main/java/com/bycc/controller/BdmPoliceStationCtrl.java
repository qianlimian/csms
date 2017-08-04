package com.bycc.controller;

import com.bycc.dto.bdmPoliceStation.BdmPoliceDto;
import com.bycc.dto.bdmPoliceStation.BdmPoliceStationDto;
import com.bycc.service.bdmPoliceStation.BdmPoliceStationService;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公安局（派出所）维护
 */
@Controller
@RequestMapping("/bdmPoliceStations")
public class BdmPoliceStationCtrl {

    @Autowired
    private BdmPoliceStationService service;

    /**
     * 首页
     */
    @RequestMapping
    public String index(Model model) {
        return "/pages/bdmPoliceStation/index";
    }

    /**
     * 新增|编辑页
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/pages/bdmPoliceStation/edit";
    }

    /**
     * 选择窗口
     */
    @RequestMapping("/load")
    public String load() {
        return "/pages/bdmPoliceStation/select";
    }

    /**
     * 按条件查询
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
        //查询教师列表
        List<BdmPoliceStationDto> dtos = service.query(qb);
        Map<String, Object> map = new HashMap<String, Object>();
        //总记录数
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }

    /**
     * 查询所有
     */
    @RequestMapping("/find4Select")
    @ResponseBody
    public List<BdmPoliceStationDto> find4Select(@RequestParam(value="areaType", required=false) String areaType){
        return service.find4Select(areaType);
    }

    /**
     * 按id查找
     */
    @RequestMapping("/findById")
    @ResponseBody
    public BdmPoliceStationDto findById(@RequestParam("id") Integer id) {
        return service.findById(id);
    }

    /**
     * 按id查询子表
     */
    @RequestMapping("/findSubsById")
    @ResponseBody
    public List<BdmPoliceDto> findSubsById(@RequestParam(value="id",required=false) Integer id) {
        return service.findSubsById(id);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BdmPoliceStationDto save(@RequestBody BdmPoliceStationDto dto) throws Exception {
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
