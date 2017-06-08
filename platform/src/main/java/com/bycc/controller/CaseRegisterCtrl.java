package com.bycc.controller;

import com.bycc.dto.caseRegister.CasePeopleBelongsDto;
import com.bycc.dto.caseRegister.CasePeopleBelongsSaveDto;
import com.bycc.dto.caseRegister.CasePeopleDto;
import com.bycc.dto.caseRegister.CasePeopleInspectDto;
import com.bycc.service.bdmCabinet.BdmCabinetService;
import com.bycc.service.caseRegister.CaseRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 办案区信息采集
 * User: yumingzhe
 * Time: 2017-4-24 11:06
 */
@Controller
@RequestMapping("/caseRegister")
public class CaseRegisterCtrl {
    private Logger logger = LoggerFactory.getLogger(CaseRegisterCtrl.class);

    @Autowired
    private CaseRegisterService service;

    @Autowired
    private BdmCabinetService bdmCabinetService;

    /**
     * 预览模板名
     */
    private static final String HANDLING_AREA_REGISTER_REPORT = "handling_area_register";

    /**
     * 首页
     */
    @RequestMapping
    public String index(Model model) {
        model.addAttribute("cabinets", bdmCabinetService.findAll());
        return "/pages/caseRegister/index";
    }

    /**
     * 人员编辑页面
     */
    @RequestMapping("edit")
    public String edit() {
        return "/pages/caseRegister/edit";
    }


    /**
     * 绑定手环页面
     */
    @RequestMapping("bindStrap/load")
    public String showBindStrap() {
        return "pages/caseRegister/partial/bindStrap";
    }

    /**
     * 解绑手环页面
     */
    @RequestMapping("unbindStrap/load")
    public String unBindStrap() {
        return "pages/caseRegister/partial/unbindStrap";
    }

    //---------------------------涉案人员-CRUD---------------------------
    /**
     * 查询涉案人员
     */
    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query(@RequestParam(value = "caseRecordId", required = false) Integer caseRecordId,
                                     @RequestParam(value = "caseId", required = false) Integer caseId,
                                     @RequestParam("queryBean") QueryBean qb) {
        //查询涉案人员列表
        List<CasePeopleDto> dtos = service.query(caseRecordId, caseId, qb);
        Map<String, Object> map = new HashMap<>();
        //总记录数
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }

    /**
     * 按ID查询涉案人员
     *
     * @param id 涉案人员id
     * @return 涉案人员信息
     */
    @RequestMapping(value = "findById", method = RequestMethod.GET)
    @ResponseBody
    public CasePeopleDto findById(@RequestParam("id") Integer id) {
        return service.findById(id);
    }

    /**
     * 保存涉案人员
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public CasePeopleDto saveCasePeople(@RequestBody CasePeopleDto dto) throws Exception {
        return service.save(dto);
    }

    /**
     * 按ID删除涉案人员
     *
     * @param ids 涉案人员id
     */
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@RequestParam("ids") String ids) {
        service.delete(ids);
    }
    //---------------------------涉案人员-END---------------------------

    //---------------------------人身检查-CRUD---------------------------
    /**
     * 查询人员详细信息(随身物品、人身安全检查、信息采集)
     *
     * @param peopleId 涉案人员id
     */
    @RequestMapping(value = "findPeopleInspect", method = RequestMethod.GET)
    @ResponseBody
    public CasePeopleInspectDto findDetail(@RequestParam("casePeopleId") Integer peopleId) {
        return service.findPeopleInspect(peopleId);
    }

    /**
     * 保存人员详细信息(随身物品、人身安全检查、信息采集)
     *
     * @param dto
     */
    @RequestMapping(value = "savePeopleInspect", method = RequestMethod.POST)
    @ResponseBody
    public CasePeopleInspectDto savePeopleInspect(@RequestBody CasePeopleInspectDto dto) throws BusinessException{
        return service.savePeopleInspect(dto);
    }
    //---------------------------人身检查-END---------------------------

    //---------------------------随身财物-CRUD---------------------------
    /**
     * 保存涉案人员随身财物信息
     *
     * @param dto 随身财物
     */
    @RequestMapping(value = "savePeopleBelongs", method = RequestMethod.POST)
    @ResponseBody
    public List<CasePeopleBelongsDto> saveOrUpdatePeopleBelongs(@RequestBody CasePeopleBelongsSaveDto dto) throws Exception {
        return service.saveOrUpdatePeopleBelongs(dto);
    }

    /**
     * 按涉案人员查询随身财物
     *
     * @param peopleId 涉案人员id
     * @return 涉案人员的随身财物
     */
    @RequestMapping(value = "findPeopleBelongs", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findPeopleBelongs(@RequestParam(value = "casePeopleId", required = false) Integer peopleId,
                                                 @RequestParam("queryBean") QueryBean qb) {
        List<CasePeopleBelongsDto> dtos = service.findPeopleBelongs(peopleId, qb);
        Map<String, Object> map = new HashMap<>();
        // 总记录数
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }

    /**
     * 归还涉案人员随身财物
     *
     * @param belongIds 随身财物id
     */
    @RequestMapping(value = "returnBelongs", method = RequestMethod.PUT)
    @ResponseBody
    public void returnPeopleBelongs(@RequestParam("belongIds") List<Integer> belongIds) {
        service.returnPeopleBelongs(belongIds);
    }
    //---------------------------随身财物-END---------------------------

    //---------------------------手环-CRUD---------------------------
    /**
     * 绑定手环
     *
     * @param peopleId 涉案人员id
     * @param strapId  手环id
     */
    @RequestMapping(value = "bindStrap", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> bindStrap(@RequestParam("casePeopleId") Integer peopleId, @RequestParam("strapId") Integer strapId) {
        Map<String, Object> result = new HashMap<>();
        try {
            service.bindStrap(peopleId, strapId);
            result.put("success", true);
        } catch (BusinessException e) {
            logger.warn("{}", e);
            result.put("success", false);
            result.put("errorInfo", e.getMessage());
        }
        return result;
    }

    /**
     * 解绑手环
     *
     * @param dto 涉案人员
     */
    @RequestMapping(value = "unBindStrap", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> unBindStrap(@RequestBody CasePeopleDto dto) {
        Map<String, Object> result = new HashMap<>();
        try {
            service.unBindStrap(dto);
            result.put("success", true);
        } catch (Exception e) {
            logger.warn("{}", e);
            result.put("success", false);
            result.put("errorInfo", e.getMessage());
        }
        return result;
    }
    //---------------------------手环-END---------------------------

    //---------------------------预览、打印----------------------------
    /**
     * 预览
     *
     * @param peopleId 涉案人员id
     */
    @RequestMapping(value = "print", method = RequestMethod.GET)
    @ResponseBody
    public void print(@RequestParam("casePeopleId") Integer peopleId) throws BusinessException {
        service.print(peopleId, HANDLING_AREA_REGISTER_REPORT);
    }

}
