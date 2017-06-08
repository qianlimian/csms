package com.bycc.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bycc.demo.dto.StudentDto;
import com.bycc.demo.service.student.StudentService;

/**
 * 单表示例（学生）
 * @author yumingzhe
 * @date 2017年3月14日 下午2:56:57
 */
@Controller
@RequestMapping("/students")
public class StudentCtrl {

	@Autowired
	private StudentService service;

	/**
	 * 首页
	 */
	@RequestMapping
	public String index() {
		return "/pages/demo/student/index";
	}
	
	/**
	 * 新增|编辑页
	 */
	@RequestMapping("/edit")
	public String edit() {
		return "/pages/demo/student/edit";
	}

	/**
	 * 按条件查询
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
		//查询学生列表
		List<StudentDto> dtoList = service.query(qb);

		Map<String, Object> map = new HashMap<String, Object>();
		//总记录数
		map.put("total", qb.getTotal());
		map.put("items", dtoList);

		return map;
	}

	/**
	 * 按id查找
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public StudentDto findById(@RequestParam("id") Integer id) {
		//查询用户信息
		return service.findById(id);
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public StudentDto save(@RequestBody StudentDto dto) throws Exception {
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
