package com.bycc.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.bycc.service.overview.OverviewService;

/**
 * 监控总览
 */
@Controller
@RequestMapping("/overviews")
public class OverviewCtrl {
	@Autowired
	private OverviewService overviewService;

    /**
     * 首页
     */
    @RequestMapping
    public ModelAndView index() {
    	ModelAndView modelAndView = new ModelAndView("/pages/overview/index");
    	modelAndView.addObject("policeStations", overviewService.findPoliceStationList());
        return modelAndView;
    }
    
    /**
     * 根据房间获取摄像头直播地址
     */
    @RequestMapping("findCamerasByRoom/{roomId}") 
    @ResponseBody
    public Map<Integer, String> findCamerasByRoom(@PathVariable Integer roomId){
    	return overviewService.findCamerasByRoom(roomId);
    }
    
}
