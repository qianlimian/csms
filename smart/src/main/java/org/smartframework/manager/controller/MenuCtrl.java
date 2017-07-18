package org.smartframework.manager.controller;

import org.smartframework.manager.dto.menu.MenuCondition;
import org.smartframework.manager.dto.menu.MenuDto;
import org.smartframework.manager.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @description 菜单管理
 * @author zhaochuanfeng
 */
@Controller
@RequestMapping("/smart/menus")
public class MenuCtrl {

    @Autowired
    private MenuService menuService;

    /**
     * 首页
     */
    @RequestMapping
    public String index() {
        return "/smart/frame/menu/index";
    }

    /**
     * 新增|编辑页
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/smart/frame/menu/edit";
    }

    /**
     * 查询用户列表
     */
    @RequestMapping("/findByCondition")
    @ResponseBody
    public List<MenuDto> findByCondition(@RequestParam("condition")MenuCondition condition) {
        return menuService.findByCondition(condition);
    }
}
