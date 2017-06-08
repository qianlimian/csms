package com.bycc.controller;

import com.bycc.dto.bdmHandlingArea.BdmCabinetDto;
import com.bycc.dto.bdmHandlingArea.BdmHandlingAreaDto;
import com.bycc.dto.bdmHandlingArea.BdmStrapDto;
import com.bycc.service.bdmHandlingArea.BdmHandlingAreaService;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 办案区维护
 */
@Controller
@RequestMapping("/bdmHandlingAreas")
public class BdmHandlingAreaCtrl {
    
    @Autowired
    private BdmHandlingAreaService service;

    /**
     * 首页
     */
    @RequestMapping
    public String index() {
        return "/pages/bdmHandlingArea/index";
    }

    /**
     * 新增|编辑页
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/pages/bdmHandlingArea/edit";
    }

    /**
     * 返回所有的办案区
     */
    @RequestMapping("query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
        List<BdmHandlingAreaDto> list = service.query(qb);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", qb.getTotal());
        map.put("items", list);
        return map;
    }

    /**
     * 通过办案区id查找
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "findById")
    @ResponseBody
    public BdmHandlingAreaDto findById(@RequestParam("id") Integer id) {
        return service.findById(id);

    }

    /**
     * 保存办案区
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public BdmHandlingAreaDto save(@RequestBody BdmHandlingAreaDto dto) throws Exception {
        return service.save(dto);

    }

    /**
     * 删除办案区
     *
     * @param ids
     */
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@RequestParam("ids") String ids) {
        service.delete(ids);

    }

    /**
     * 根据policeStationId查找Straps
     *
     * @param id
     * @return
     */
    @RequestMapping("findSubStrapsById")
    @ResponseBody
    public List<BdmStrapDto> findStrapsByAreaId(@RequestParam("id") Integer id) {
        return service.findStrapsByAreaId(id);
    }

    /**
     * 根据policeStationId查找Cabinets
     *
     * @param id
     * @return
     */
    @RequestMapping("findSubCabinetsById")
    @ResponseBody
    public List<BdmCabinetDto> findCabinetsByAreaId(@RequestParam("id") Integer id) {
        return service.findCabinetByAreaId(id);
    }
}
