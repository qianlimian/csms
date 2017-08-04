/**
 *
 */
package com.bycc.controller;

import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bycc.dto.CaseFindParamDto;
import com.bycc.entity.CaseRecord;
import com.bycc.service.bdmPoliceStation.BdmPoliceStationService;
import com.bycc.service.caserecord.CaseRecordExcelService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bycc.dto.CaseRecordDto;
import com.bycc.service.caserecord.CaseRecordService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gaoningbo
 * @description
 * @date 2017年4月14日
 */
@Controller
@RequestMapping("/caseRecords")
public class CaseRecordCtrl {

    private Logger logger = LoggerFactory.getLogger(CaseRecordCtrl.class);

    @Autowired
    CaseRecordService service;

    @Autowired
    BdmPoliceStationService psService;

    @Autowired
    CaseRecordExcelService caseRecordExcelService;

    /**
     * @return
     * @description 首页
     * @date 2017年4月14日上午10:00:37
     */
    @RequestMapping
    public String index(Model model) {
        model.addAttribute("polices", psService.findAllSubs());
        return "/pages/caseRecord/index";
    }

    /**
     * @description 新增||修改
     * @date 2017年4月14日上午10:03:19
     */
    @RequestMapping("/edit")
    public String edit() {
        return "/pages/caseRecord/edit";
    }

    /**
     * 选择窗口 (for group module)
     */
    @RequestMapping("/load")
    public String load() {
        return "/pages/caseRecord/select";
    }

    /**
     * @description 保存
     * @date 2017年4月17日上午8:27:04
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public CaseRecordDto save(@RequestBody CaseRecordDto dto) throws Exception {
        return service.saveCaseRecord(dto);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@RequestParam("ids") String ids) {
        service.deleteCaseRecordById(ids);
    }

    /**
     * 按id查询
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    public CaseRecordDto findById(@RequestParam("id") Integer id) {
        return service.findById(id);
    }

    /**
     * 分页查询
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb) {
        List<CaseRecordDto> dtos = service.query(qb);
        Map<String, Object> map = new HashMap<>();
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }

    //---------------------------开始、结束办案----------------------------

    /**
     * 开始办案
     */
    @RequestMapping(value = "startCase", method = RequestMethod.GET)
    @ResponseBody
    public CaseRecordDto startCase(@RequestParam(value = "caseId", required = false) Integer caseId,
                                   @RequestParam(value = "caseRecordId", required = false) Integer caseRecordId) throws Exception {
        return service.startCase(caseId, caseRecordId);
    }

    /**
     * 结束办案
     */
    @RequestMapping(value = "finishCase", method = RequestMethod.GET)
    @ResponseBody
    public CaseRecordDto finishCase(@RequestParam(value = "caseId", required = false) Integer caseId,
                                    @RequestParam(value = "caseRecordId", required = false) Integer caseRecordId) throws Exception {
        return service.finishCase(caseId, caseRecordId);
    }


    /**
     * 查找CaseRecord(QueryBean,Condition)
     * @return
     */
    @RequestMapping("search")
    @ResponseBody
    public Map<String, Object> search(@RequestParam("queryBean") QueryBean qb,
                                   @RequestParam("condition") CaseFindParamDto caseFindParamDto) throws ParseException {

        List<CaseRecordDto> dtos = service.search(qb,caseFindParamDto);

        Map<String, Object> map = new HashMap<>();
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }

    /**
     * 导出excel文件
     */
    @RequestMapping("excel")
    @ResponseBody
    public void excel(@RequestParam("param") CaseFindParamDto caseFindParamDto,@RequestParam("qb") QueryBean queryBean,HttpServletResponse response) throws IOException, ParseException {
        HSSFWorkbook wb = service.getExcel(queryBean,caseFindParamDto);
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
