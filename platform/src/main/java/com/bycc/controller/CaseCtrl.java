package com.bycc.controller;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bycc.service.bdmPoliceStation.BdmPoliceStationService;
import com.bycc.service.caserecord.CaseRecordExcelService;
import com.bycc.service.caserecord.CaseRecordService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bycc.dto.CaseDto;
import com.bycc.service.caze.CaseService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gaoningbo
 * @description
 * @date 2017年4月14日
 */
@Controller
@RequestMapping("/cases")
public class CaseCtrl {

    @Autowired
    CaseService service;
    @Autowired
    CaseRecordExcelService caseRecordExcelService;
    @Autowired
    BdmPoliceStationService bdmPoliceStationService;

    /**
     * 首页
     */
    @RequestMapping("/{type}")
    public String index(@PathVariable("type") String type) {
        return "/pages/case/" + type + "/index";
    }

    /**
     * 查看
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/pages/case/partial/edit";
    }

    /**
     * 查询案件
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam(value="type",required=false) String type, @RequestParam("queryBean") QueryBean qb) {
        List<CaseDto> dtos = service.query(type, qb);
        Map<String, Object> map = new HashMap<>();
        // 总记录数
        map.put("total", qb.getTotal());
        map.put("items", dtos);

        return map;
    }
    
    /**
     * 
     * @description 按ID查询案件
     * @author liuxunhua
     * @date 2017年5月24日 上午8:02:35
     *
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
	@ResponseBody
	public CaseDto findById(@RequestParam("id") Integer id) {
		return service.findById(id);
	}


    /**
     * 返回导出条件选择
     * @return
     */
    @RequestMapping(value = "caseExport",method = RequestMethod.GET)
    public String caseExport(Model model){
        model.addAttribute("policeStations",bdmPoliceStationService.findAll()); //combo列表项
        return "/pages/caseOpening/option";
    }


    /**
     * 生成Excel文件
     */
    @RequestMapping(value = "excel", method = RequestMethod.GET)
    public void generateExcel(@RequestParam String handleStatus,
                              @RequestParam String masterUnit,
                              @RequestParam String acceptStart,
                              @RequestParam String acceptEnd,
                              @RequestParam String closeStart,
                              @RequestParam String closeEnd,HttpServletRequest request, HttpServletResponse response ) throws Exception {
        HSSFWorkbook wb = caseRecordExcelService.getExcel(handleStatus,masterUnit,acceptStart,acceptEnd,closeStart,closeEnd);
        String fileName = "案件信息";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }


    }
}
