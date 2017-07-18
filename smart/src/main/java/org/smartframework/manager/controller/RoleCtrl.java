package org.smartframework.manager.controller;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dto.role.RoleDto;
import org.smartframework.manager.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 角色管理
 * @author zhaochuanfeng
 */
@Controller
@RequestMapping("/smart/roles")
public class RoleCtrl {

	@Autowired
	private RoleService roleService;
	
	/**
	 * 首页
	 */
	@RequestMapping
    public String index() {
		return "/smart/frame/role/index";
    }
	
	/**
	 * 新增|编辑页
	 */
	@RequestMapping("/edit")
    public String edit() {
		return "/smart/frame/role/edit";
    }

	/**
	 * 查询角色列表
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String,Object> query(@RequestParam("queryBean") QueryBean qb) {

		//查询角色列表
		List<RoleDto> dtoList = roleService.query(qb);

		Map<String,Object> map = new HashMap<String,Object>();
		//总记录数
		map.put("total", qb.getTotal());
		map.put("items", dtoList);
		return map;
	}

	/**
	 * 查询角色列表
	 */
	@RequestMapping("/queryByGroupId")
	@ResponseBody
	public Map<String,Object> findByGroupId(@RequestParam("queryBean") QueryBean qb, @RequestParam(value = "groupId", required = false)Integer groupId) {
		//查询角色列表
		List<RoleDto> dtoList = roleService.queryByGroupId(qb, groupId);

		Map<String,Object> map = new HashMap<String,Object>();
		//总记录数
		map.put("total", qb.getTotal());
		map.put("items", dtoList);
		return map;
	}

	/**
	 * 查询角色
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public RoleDto findById(@RequestParam("id")Integer id) {
		//查询角色信息
		return roleService.findById(id);
	}

	/**
	 * 保存角色
	 */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
	public RoleDto save(@RequestBody RoleDto dto) throws Exception {
    	return roleService.save(dto);
	}

    /**
     * 删除角色
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@RequestParam("ids") String ids){
    	roleService.delete(ids);
    }

}
