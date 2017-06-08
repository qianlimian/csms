package com.bycc.controller;

import com.bycc.dto.bdmRoom.BdmCameraDto;
import com.bycc.dto.bdmRoom.BdmRoomDto;
import com.bycc.dto.bdmRoom.BdmStationDto;
import com.bycc.dto.bdmRoom.RoomCondition;
import com.bycc.service.bdmRoom.BdmRoomService;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaidong on 2017/4/18.
 */
@Controller
@RequestMapping(value = "bdmRooms")
public class BdmRoomCtrl {
    @Autowired
    private BdmRoomService service;

    /**
     * 房间管理页面
     */
    @RequestMapping
    public String index() {
        return "/pages/bdmRoom/index";
    }

    /**
     * 房间编辑页面
     *
     * @return
     */
    @RequestMapping("edit")
    public String edit() {
        return "/pages/bdmRoom/edit";
    }

    /**
     * 查找所有房间
     *
     * @return
     */
    @RequestMapping("query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb,
                                     @RequestParam("condition") RoomCondition condition) {
        List<BdmRoomDto> list = service.query(condition, qb);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", qb.getTotal());
        map.put("items", list);
        return map;
    }

    /**
     * 通过房间的id进行查找
     *
     * @param id
     * @return
     */
    @RequestMapping("findById")
    @ResponseBody
    public BdmRoomDto findById(@RequestParam("id") Integer id) {
        return service.findById(id);
    }

    /**
     * 保存房间信息
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public BdmRoomDto save(@RequestBody BdmRoomDto dto) throws Exception {
        return service.save(dto);
    }

    /**
     * 删除房间信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@RequestParam("ids") String ids) {
        service.delete(ids);
    }

    /**
     * 通过roomId查找station
     *
     * @param id
     * @return
     */
    @RequestMapping("findSubStationsById")
    @ResponseBody
    public List<BdmStationDto> findStationByRoomId(@RequestParam("id") Integer id) {
        return service.findStationByRoomId(id);
    }

    /**
     * 通过roomId查找camera
     *
     * @param id
     * @return
     */
    @RequestMapping("findSubCamerasById")
    @ResponseBody
    public List<BdmCameraDto> findCameraByRoomId(@RequestParam("id") Integer id) {
        return service.findCameraByRoomId(id);
    }

}
