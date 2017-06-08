package org.smartframework.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dto.common.CheckboxDto;
import org.smartframework.manager.dto.group.GroupDto;
import org.smartframework.manager.service.group.GroupService;
import org.smartframework.manager.service.menu.MenuService;
import org.smartframework.manager.service.module.ModuleService;
import org.smartframework.manager.service.role.RoleService;
import org.smartframework.utils.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @description 组管理
 * @author zhaochuanfeng
 */
@Controller
@RequestMapping("/groups")
public class GroupCtrl {

    @Autowired
    private GroupService service;

	@Autowired
	private MenuService menuService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private RoleService roleService;

    /**
     * 首页
     */
    @RequestMapping
    public String index(Model model) {
    	model.addAttribute("menus", JsonHelper.getJson(menuService.getMenus()));
		model.addAttribute("operates", JsonHelper.getJson(moduleService.getModules()));
		model.addAttribute("roles", roleService.findAll());
        return "/smart/frame/group/index";
    }

    /**
     * 新增|编辑页
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/smart/frame/group/edit";
    }
    
	/**
	 * 查询用户组列表
	 */
	@RequestMapping(value = "query")
	@ResponseBody
	public Map<String,Object> query(@RequestParam("queryBean") QueryBean qb) {
		List<GroupDto> list = service.query(qb);

		Map<String,Object> map = new HashMap<String,Object>();
		//总记录数
		map.put("total", qb.getTotal());
		map.put("items", list);
		return map;
	}

	/**
	 * 查询用户组
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public GroupDto findById(@RequestParam("id")Integer id) {
		//查询用户组信息
		return service.findById(id);
	}

	/**
	 * 保存用户组
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public GroupDto save(@RequestBody GroupDto dto) throws Exception {
		return service.save(dto);
	}

	/**
	 * 删除用户组
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestParam("ids") String ids){
		service.delete(ids);
	}

	/**
	 * 添加组用户
	 */
	@RequestMapping(value = "{groupId}/addUsers", method = RequestMethod.POST)
	@ResponseBody
	public void addUsers(@PathVariable("groupId")Integer groupId, @RequestParam("userIds") String userIds) {
		service.addUsers(groupId, userIds);
	}

	/**
	 * 删除组用户
	 */
	@RequestMapping(value = "{groupId}/removeUsers", method = RequestMethod.DELETE)
	@ResponseBody
	public void removeUsers(@PathVariable("groupId")Integer groupId, @RequestParam("userIds") String userIds) {
		service.removeUsers(groupId, userIds);
	}

	/**
	 * 查询组菜单
	 */
	@RequestMapping("{groupId}/getMenus")
	@ResponseBody
	public List<Integer> getMenus(@PathVariable("groupId")Integer groupId) {
		return service.getMenus(groupId);
	}

	/**
	 * 保存组菜单
	 */
	@RequestMapping(value = "{groupId}/saveMenus", method = RequestMethod.POST)
	@ResponseBody
	public void saveMenus(@PathVariable("groupId")Integer groupId, @RequestBody CheckboxDto checkboxDto) {
		service.saveMenus(groupId, checkboxDto);
	}

	/**
	 * 查询组操作
	 */
	@RequestMapping("{groupId}/getOperates")
	@ResponseBody
	public List<Integer> getOperates(@PathVariable("groupId")Integer groupId) {
		return service.getOperates(groupId);
	}

	/**
	 * 保存组操作
	 */
	@RequestMapping(value = "{groupId}/saveOperates", method = RequestMethod.POST)
	@ResponseBody
	public void saveOperates(@PathVariable("groupId")Integer groupId, @RequestBody CheckboxDto checkboxDto) {
		service.saveOperates(groupId, checkboxDto);
	}

	/**
	 * 查询组角色
	 */
	@RequestMapping("{groupId}/getRoles")
	@ResponseBody
	public List<String> getRoles(@PathVariable("groupId")Integer groupId) {
		return service.getRoles(groupId);
	}

	/**
	 * 保存组角色
	 */
	@RequestMapping(value = "{groupId}/saveRoles", method = RequestMethod.POST)
	@ResponseBody
	public void saveRoles(@PathVariable("groupId")Integer groupId, @RequestParam("roles") String roles) {
		service.saveRoles(groupId, roles);
	}
}
