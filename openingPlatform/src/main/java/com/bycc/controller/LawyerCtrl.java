/**
 *
 */
package com.bycc.controller;

import com.bycc.dto.LawyerDto;
import com.bycc.service.lawyer.LawyerService;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @date 2017年7月11日
 *
 */
@Controller
@RequestMapping("/lawyers")
public class LawyerCtrl {

    @Autowired
    LawyerService service;

    /**
     * 首页
     */
    @RequestMapping
    public String index() {
        return "/pages/lawyer/index";
    }

    @RequestMapping("/impIndex")
    public String impIndex() {
        return "/pages/lawyer/impIndex";
    }
    /**
     * 修改
     */
    @RequestMapping("/edit")
    public String edit(){return "/pages/lawyer/edit"; }

    /**
     * 分页查询
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String,Object> query(@RequestParam("queryBean") QueryBean qb){
        List<LawyerDto> dtos = service.query(qb);
        Map<String, Object> map = new HashMap<>();
        map.put("total",qb.getTotal());
        map.put("items",dtos);
        return map;
    }

    /**
     * 按id查询
     */
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    @ResponseBody
    public LawyerDto findById(@RequestParam("id") Integer id){return  service.findById(id);}

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public LawyerDto save(@RequestBody LawyerDto dto)throws Exception{
        return  service.save(dto);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@RequestParam("ids") String ids){service.deleteLawyerById(ids);}

    /**
     *
     */
    @RequestMapping(value = "/impExcel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importExcel(@RequestBody MultipartFile file, HttpServletResponse response){
        Map<String, Object> result = new HashMap<>();
        try {
            int number=service.importExcel(file);
            result.put("success", Boolean.TRUE);
            result.put("number", number);
        } catch (Exception e) {
            result.put("success", Boolean.FALSE);
            result.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("export")
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception{
        File file = new File(Paths.get(request.getRealPath("/")+"/views/pages/lawyer/templates/lawyer.xls").toUri());
        InputStream in = new FileInputStream(file.getPath());
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode("律师模版", "utf8") + ".xls");
        int len = 0;
        byte[] buffer = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer,0,len);
        }
        in.close();
    }
}
