package com.bycc.demo.controller;

import com.bycc.demo.dto.CourseDto;
import com.bycc.demo.dto.ScoreDto;
import com.bycc.demo.service.course.CourseService;
import com.bycc.demo.service.student.StudentService;
import com.bycc.demo.service.teacher.TeacherService;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主子表示例（课程）
 */
@Controller
@RequestMapping("/courses")
public class CourseCtrl {

    @Autowired
    private CourseService service;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    /**
     * 首页
     */
    @RequestMapping
    public String index(Model model) {
        model.addAttribute("teachers", teacherService.findAll()); //combo列表项
        model.addAttribute("students", studentService.findAll()); //combo列表项
        return "/pages/demo/course/index";
    }

    /**
     * 新增|编辑页
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/pages/demo/course/edit";
    }

    /**
     * 按条件查询
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
        //查询教师列表
        List<CourseDto> dtos = service.query(qb);
        Map<String, Object> map = new HashMap<String, Object>();
        //总记录数
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }

    /**
     * 按id查找课程
     */
    @RequestMapping("/findById")
    @ResponseBody
    public CourseDto findById(@RequestParam("id") Integer id) {
        return service.findById(id);
    }

    /**
     * 按id查询成绩
     */
    @RequestMapping("/findSubsById")
    @ResponseBody
    public List<ScoreDto> findSubsById(@RequestParam("id") Integer id) {
        return service.findSubsById(id);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public CourseDto save(@RequestBody CourseDto dto) throws Exception {
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
