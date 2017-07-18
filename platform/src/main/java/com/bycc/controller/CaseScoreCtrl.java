/**
 *
 */
package com.bycc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bycc.dto.caseScore.CaseScoreDto;
import com.bycc.dto.caseScore.CaseScoreItemDto;
import com.bycc.dto.scoreRank.CaseScoreRankDto;
import com.bycc.dto.scoreRank.PoliceScoreRankDto;
import com.bycc.dto.scoreRank.PoliceStationScoreRankDto;
import com.bycc.service.bdmEvaluation.BdmEvaluationService;
import com.bycc.service.caseScore.CaseScoreService;

/**
 * @author gaoningbo
 * @description 打分
 * @date 2017年4月24日
 */
@Controller
@RequestMapping("/caseScores")
public class CaseScoreCtrl {

    @Autowired
    private CaseScoreService service;
    @Autowired
    private BdmEvaluationService evalService;

    /**
     * @description 首页
     */
    @RequestMapping("/mark")
    public String index() {
        return "/pages/caseScore/mark/index";
    }

    /**
     * @description 评价打分
     */
    @RequestMapping("/score")
    public String score(@RequestParam("caseId") Integer caseId, Model model) {
        model.addAttribute("score", service.findByCaseId(caseId));
        model.addAttribute("bigItems", evalService.findAllScoreStandards());
        return "/pages/caseScore/mark/score";
    }

    /**
     * @description 我的积分|积分详情
     */
    @RequestMapping("/detail")
    public String detail() {
        return "/pages/caseScore/detail";
    }

    /**
     * @description 查询
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    public CaseScoreDto findById(@RequestParam("id") Integer id) {
        return service.findById(id);
    }

    /**
     * @description 子表查询
     */
    @RequestMapping(value = "/findSubsById", method = RequestMethod.GET)
    @ResponseBody
    public List<CaseScoreItemDto> findSubsById(@RequestParam("id") Integer id) {
        return service.findSubsById(id);
    }

    /**
     * @description 保存
     * @date 2017年4月25日下午3:54:03
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public CaseScoreDto save(@RequestBody CaseScoreDto dto) {
        return service.save(dto);
    }

    /**
     * @description 我的积分
     * @date 2017年4月26日下午2:59:55
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(@RequestParam("queryBean") QueryBean qb, @RequestParam(value = "policeId", required = false) Integer policeId) {
        List<CaseScoreRankDto> dtos = service.query(qb, policeId);
        Map<String, Object> map = new HashMap<>();
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }
    
    /**
     * @description 民警积分排名
     */
    @RequestMapping("/rank/bdmPoliceRank")
    public String bdmPoliceRank() {
        return "/pages/caseScore/rank/bdmPoliceRank/rank";
    }
    
    /**
     * @description 民警积分排名详情
     */
    @RequestMapping("/rank/bdmPoliceRank/detail")
    public String bdmPoliceRankDetail() {
        return "/pages/caseScore/rank/bdmPoliceRank/detail";
    }
    
    /**
     * @description 民警积分排名
     * @date 2017年4月26日下午2:59:55
     */
    @RequestMapping("/rank/bdmPoliceRank/queryRank")
    @ResponseBody
    public Map<String, Object> bdmPoliceQueryRank(@RequestParam("queryBean") QueryBean qb) {
        List<PoliceScoreRankDto> dtos = service.bdmPoliceQueryRank(qb);
        Map<String, Object> map = new HashMap<>();
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }
    
    /**
     * @description 警局积分排名
     */
    @RequestMapping("/rank/bdmPoliceStationRank")
    public String bdmPoliceStationRank() {
        return "/pages/caseScore/rank/bdmPoliceStationRank/rank";
    }
    
    /**
     * @description 警局积分排名详情
     */
    @RequestMapping("/rank/bdmPoliceStationRank/detail")
    public String bdmPoliceStationRankDetail() {
        return "/pages/caseScore/rank/bdmPoliceStationRank/detail";
    }
    
    /**
     * @description 警局积分排名
     * @date 2017年4月26日下午2:59:55
     */
    @RequestMapping("/rank/bdmPoliceStationRank/queryRank")
    @ResponseBody
    public Map<String, Object> bdmPoliceStationQueryRank(@RequestParam("queryBean") QueryBean qb) {
        List<PoliceStationScoreRankDto> dtos = service.bdmPoliceStationQueryRank(qb);
        Map<String, Object> map = new HashMap<>();
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }
    
    /**
     * @description 警局积分排名详情
     * @date 2017年4月26日下午2:59:55
     */
    @RequestMapping("/rank/bdmPoliceStationRank/query")
    @ResponseBody
    public Map<String, Object> bdmPoliceStationQuery(@RequestParam("queryBean") QueryBean qb, @RequestParam(value = "policeStationId", required = false) Integer policeStationId) {
        List<CaseScoreRankDto> dtos = service.bdmPoliceStationQuery(qb, policeStationId);
        Map<String, Object> map = new HashMap<>();
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }
    
    /**
     * @description 警局积分排名子表查询
     */
    @RequestMapping(value = "/rank/bdmPoliceStationRank/findSubsById", method = RequestMethod.GET)
    @ResponseBody
    public List<CaseScoreItemDto> bdmPoliceStationFindSubsById(@RequestParam("id") Integer id) {
        return service.findSubsById(id);
    }
    
    /**
     * @description 案件积分排名
     */
    @RequestMapping("/rank/caseRank")
    public String caseRank() {
        return "/pages/caseScore/rank/caseRank/rank";
    }
    
    /**
     * @description 案件积分排名
     * @date 2017年4月26日下午2:59:55
     */
    @RequestMapping("/rank/caseRank/query")
    @ResponseBody
    public Map<String, Object> caseQueryRank(@RequestParam("queryBean") QueryBean qb) {
        List<CaseScoreRankDto> dtos = service.caseQueryRank(qb);
        Map<String, Object> map = new HashMap<>();
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }
    
    /**
     * @description 警局积分排名子表查询
     */
    @RequestMapping(value = "/rank/caseRank/findSubsById", method = RequestMethod.GET)
    @ResponseBody
    public List<CaseScoreItemDto> caseRankFindSubsById(@RequestParam("id") Integer id) {
        return service.findSubsById(id);
    }
}
