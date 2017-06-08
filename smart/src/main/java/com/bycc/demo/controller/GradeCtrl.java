package com.bycc.demo.controller;

import com.bycc.demo.dto.GradeDto;
import com.bycc.demo.service.grade.GradeService;
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
 * 单表示例（班级）
 */
@Controller
@RequestMapping("/grades")
public class GradeCtrl {

    @Autowired
    private GradeService service;

    @Autowired
    private TeacherService teacherService;

    /**
     * 首页
     */
    @RequestMapping
    public String index(Model model) {
        model.addAttribute("teachers", teacherService.findAll()); //combo列表项
        return "/pages/demo/grade/index";
    }

    /**
     * 新增|编辑页
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/pages/demo/grade/edit";
    }

    /**
     * 按条件查询
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
        List<GradeDto> dtoList = service.query(qb);

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
    public GradeDto findById(@RequestParam("id") Integer id) {
        return service.findById(id);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public GradeDto save(@RequestBody GradeDto dto) throws Exception {
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

    /**
     * 按过滤条件查询 --for searchBox
     */
    @RequestMapping("/find4SearchBox")
    @ResponseBody
    public Map<String, Object> find4SearchBox(@RequestParam("queryBean") QueryBean queryBean) {
        List<GradeDto> dtoList = service.findByQueryBean(queryBean);

        Map<String, Object> map = new HashMap<String, Object>();
        //总记录数
        map.put("total", queryBean.getTotal());
        map.put("items", dtoList);

        return map;
    }
}
