package org.smartframework.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dto.user.UserDto;
import org.smartframework.manager.dto.user.UserSettingDto;
import org.smartframework.manager.entity.UserSetting;
import org.smartframework.manager.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @description 用户管理
 * @author zhaochuanfeng
 */
@Controller
@RequestMapping("/users")
public class UserCtrl {

	@Autowired
	private UserService userService;
	
	/**
	 * 首页
	 */
	@RequestMapping
    public String index() {
		return "/smart/frame/user/index";
    }
	
	/**
	 * 新增|编辑页
	 */
	@RequestMapping("/edit")
    public String edit() {
		return "/smart/frame/user/edit";
    }

	/**
	 * 选择窗口 (for group module)
	 */
	@RequestMapping("/load")
	public String load() {
		return "/smart/frame/user/select";
	}

	/**
	 * 查询用户列表
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String,Object> query(@RequestParam("queryBean") QueryBean qb) {
		//查询用户列表
		List<UserDto> dtoList = userService.query(qb);

		Map<String,Object> map = new HashMap<String,Object>();
		//总记录数
		map.put("total", qb.getTotal());
		map.put("items", dtoList);
		return map;
	}

	/**
	 * 查询用户
	 */
	@RequestMapping("/queryByGroupId")
	@ResponseBody
	public Map<String,Object> findByGroupId(@RequestParam("queryBean") QueryBean qb, @RequestParam(value = "groupId", required = false)Integer groupId) {
		//查询用户列表
		List<UserDto> dtoList = userService.queryByGroupId(qb, groupId);

		Map<String,Object> map = new HashMap<String,Object>();
		//总记录数
		map.put("total", qb.getTotal());
		map.put("items", dtoList);
		return map;
	}

	/**
	 * 查询用户
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public UserDto findById(@RequestParam("id")Integer id) {
		//查询用户信息
		return userService.findById(id);
	}

	/**
	 * 保存用户
	 */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
	public UserDto save(@RequestBody UserDto dto) throws Exception {
    	return userService.save(dto);
	}

	/**
	 * 保存用户设置
	 */
	@RequestMapping(value = "/saveSetting", method = RequestMethod.POST)
	@ResponseBody
	public void saveSetting(UserSettingDto dto) throws Exception {
		userService.saveSetting(dto);
	}

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@RequestParam("ids") String ids){
    	userService.delete(ids);
    }


	/**
	 * 重置密码
	 */
    @RequestMapping(value = "/resetPsw", method = RequestMethod.POST)
    @ResponseBody
	public void resetPsw(@RequestParam("ids") String ids) throws Exception{
    	userService.resetPsw(ids);
	}

}
