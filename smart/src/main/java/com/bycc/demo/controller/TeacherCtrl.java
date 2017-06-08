package com.bycc.demo.controller;

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

import com.bycc.demo.dto.TeacherDto;
import com.bycc.demo.service.teacher.TeacherService;

/**
 * 单表示例（教师）
 * @author yumingzhe
 * @date 2017年3月15日 上午9:34:03
 */
@Controller
@RequestMapping("/teachers")
public class TeacherCtrl {

	@Autowired
	private TeacherService service;

	/**
	 * 首页
	 */
	@RequestMapping
	public String index() {
		return "/pages/demo/teacher/index";
	}

	/**
	 * 新增|编辑页
	 */
	@RequestMapping("/edit")
	public String edit() {
		return "/pages/demo/teacher/edit";
	}

	/**
	 * 按条件查询
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
		//查询教师列表
		List<TeacherDto> dtos = service.query(qb);
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
	public TeacherDto findById(@RequestParam("id") Integer teacherId) {
		return service.findById(teacherId);
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public TeacherDto save(@RequestBody TeacherDto dto) throws Exception {
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
