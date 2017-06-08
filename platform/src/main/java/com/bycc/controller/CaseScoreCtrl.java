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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bycc.dto.CaseScoreDto;
import com.bycc.dto.CaseScoreItemDto;
import com.bycc.dto.ScoreRankDto;
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
    @RequestMapping
    public String index() {
        return "/pages/caseScore/index";
    }

    /**
     * @description 打分
     */
    @RequestMapping("/load")
    public String score(@RequestParam("caseRecordId") Integer caseRecordId, Model model) {
        model.addAttribute("score", service.findByCaseRecordId(caseRecordId));
        return "/pages/caseScore/score";
    }

    /**
     * @description 我的积分|积分详情
     */
    @RequestMapping("/detail")
    public String detail() {
        return "/pages/caseScore/detail";
    }

    /**
     * @description 积分排名
     */
    @RequestMapping("/rank")
    public String rank() {
        return "/pages/caseScore/rank";
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
        List<CaseScoreDto> dtos = service.query(qb, policeId);
        Map<String, Object> map = new HashMap<>();
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }

    /**
     * @description 积分排名
     * @date 2017年4月26日下午2:59:55
     */
    @RequestMapping("/queryRank")
    @ResponseBody
    public Map<String, Object> queryRank(@RequestParam("queryBean") QueryBean qb) {
        List<ScoreRankDto> dtos = service.queryRank(qb);
        Map<String, Object> map = new HashMap<>();
        map.put("total", qb.getTotal());
        map.put("items", dtos);
        return map;
    }

}
