package com.bycc.controller;

import com.bycc.dto.bdmRoom.BdmRoomDto;
import com.bycc.service.bdRoomLayout.BdmRoomLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by yangqingming on 2017/5/8.
 */
@Controller
@RequestMapping(value = "bdmRoomLayout")
public class BdmRoomLayoutCtrl {
    @Autowired
    private BdmRoomLayoutService service;

    /**
     * 房间管理页面
     */
    @RequestMapping
    public String index() {
        return "/pages/bdmRoomLayout/index";
    }

    /**
     * 房间编辑页面
     *
     * @return
     */
    @RequestMapping("edit")
    public String edit() {
        return "/pages/bdmRoomLayout/edit";
    }

    /**
     * 查找所有房间
     *
     * @return
     */
    @RequestMapping("query")
    @ResponseBody
    public List<BdmRoomDto> queryLayout(@RequestParam("areaId") Integer areaId) {
        return service.queryRooms(areaId);
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
     * @param list
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public void saveLayout(@RequestBody List<Map<String, Object>> list) throws Exception{
        service.saveLayout(list);
    }
}
