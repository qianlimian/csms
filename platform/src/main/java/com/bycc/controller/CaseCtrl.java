package com.bycc.controller;

import java.io.*;
import java.util.*;

import com.bycc.dto.CaseFindParamDto;
import com.bycc.dto.CaseRecordDto;
import com.bycc.dto.bdmPoliceStation.BdmPoliceStationDto;
import com.bycc.entity.BdmPoliceStation;
import com.bycc.entity.CaseRecord;
import com.bycc.enumitem.CaseHandle;
import com.bycc.service.bdmPoliceStation.BdmPoliceStationService;
import com.bycc.service.caserecord.CaseRecordExcelService;
import com.bycc.service.caserecord.CaseRecordService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.smartframework.common.kendo.Filter;
import org.smartframework.common.kendo.LogicFilter;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.utils.helper.DateHelper;
import org.smartframework.utils.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    CaseRecordService caseRecordService;

    /**
     * 案件-首页
     */
    @RequestMapping
    public String index() {
        return "/pages/case/index";
    }

    /**
     * 分类案件-首页
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
        return "/pages/case/edit";
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
     * 查询案件（查询caseRecordId为空的）
     */
    @RequestMapping("/query4Select")
    @ResponseBody
    public Map<String, Object> query4Select(@RequestParam("queryBean") QueryBean qb) {
        List<CaseDto> dtos = service.query4Select(qb);
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
        return "/pages/case/export";
    }


    /**
     * 生成Excel文件
     */
    @RequestMapping(value = "excel", method = RequestMethod.GET)
    public void generateExcel(@RequestParam(value = "param") String param, HttpServletResponse response ) throws Exception {
        CaseFindParamDto caseFindParamDto = JsonHelper.getBean(param,CaseFindParamDto.class);
        String handleStatus = caseFindParamDto.getHandleStatus();
        String masterUnit = caseFindParamDto.getMasterUnit();
        String acceptStart = caseFindParamDto.getAcceptStart();
        String acceptEnd = caseFindParamDto.getAcceptEnd();
        String closeStart = caseFindParamDto.getCloseStart();
        String closeEnd = caseFindParamDto.getCloseEnd();
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

    /**
     * 案件导出查询
     * @return
     */
    @RequestMapping("search")
    @ResponseBody
    public List<CaseRecordDto> search(@RequestParam("param") String param) throws Exception {
        CaseFindParamDto caseFindParamDto = JsonHelper.getBean(param,CaseFindParamDto.class);
        String status = caseFindParamDto.getHandleStatus();
        String masterUnits = caseFindParamDto.getMasterUnit();
        String acceptStart = caseFindParamDto.getAcceptStart();
        String acceptEnd = caseFindParamDto.getAcceptEnd();
        String closeStart = caseFindParamDto.getCloseStart();
        String closeEnd = caseFindParamDto.getCloseEnd();
        QueryBean queryBean = new QueryBean();
        LogicFilter logicFilter = new LogicFilter();
        List<Filter> filterList = new ArrayList<>();
        if (status!=null && !status.equals("")){
            Filter filterStatus = new Filter();
            filterStatus.setField("caseHandle");
            filterStatus.setOperator("eq");
            filterStatus.setType("string");
            CaseHandle caseHandle = CaseHandle.getMatchByKey(status);
            filterStatus.setValue(caseHandle);
            filterList.add(filterStatus);
        }
        if (masterUnits!=null && !masterUnits.equals("")){
            Filter filterUnits = new Filter();
            filterUnits.setField("masterUnit.id");
            filterUnits.setOperator("eq");
            filterUnits.setType("string");
            filterUnits.setValue(Integer.parseInt(masterUnits));
            filterList.add(filterUnits);
        }
        if (acceptStart!=null && !acceptStart.equals("")){
            Filter filterAS = new Filter();
            filterAS.setField("acceptDate");
            filterAS.setOperator("gte");
            filterAS.setType("date");
            System.out.println(acceptStart);
            Date asDate = DateHelper.formatStringToDate(acceptStart,"yyyy-MM-dd");
            filterAS.setValue(asDate);
            filterList.add(filterAS);
        }
        if (acceptEnd!=null && !acceptEnd.equals("")){
            Filter filterAE = new Filter();
            filterAE.setField("acceptDate");
            filterAE.setOperator("lte");
            filterAE.setType("date");

            Date aeDate = DateHelper.formatStringToDate(acceptEnd,"yyyy-MM-dd");
            filterAE.setValue(aeDate);
            filterList.add(filterAE);
        }
        if (closeStart!=null && !closeStart.equals("")){
            Filter filterCS = new Filter();
            filterCS.setField("closeDate");
            filterCS.setOperator("gte");
            filterCS.setType("date");
            Date csDate = DateHelper.formatStringToDate(closeStart,"yyyy-MM-dd");
            filterCS.setValue(csDate);
            filterList.add(filterCS);
        }
        if (closeEnd!=null && !closeEnd.equals("")){
            Filter filterCE = new Filter();
            filterCE.setField("closeDate");
            filterCE.setOperator("lte");
            filterCE.setType("date");
            Date ceDate = DateHelper.formatStringToDate(closeEnd,"yyyy-MM-dd");
            filterCE.setValue(ceDate);
            filterList.add(filterCE);
        }



        if (filterList.size()>0){
            logicFilter.setFilters(filterList);
            queryBean.setFilter(logicFilter);
        }
        List<CaseRecordDto> caseRecordList = caseRecordService.query(queryBean);
        System.out.println(caseRecordList.size());
        return caseRecordList;

    }
}
