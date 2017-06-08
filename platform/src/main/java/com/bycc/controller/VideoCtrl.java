package com.bycc.controller;

import com.bycc.service.Live.LiveService;
import com.bycc.service.Live.ProcessHolder;
import com.bycc.service.section.SectionService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wanghaidong on 2017/5/3.
 */
@Controller
@RequestMapping("video")
public class VideoCtrl {
    @Autowired
    private LiveService liveService;
    @Autowired
    private SectionService sectionService;

    /**
     * 开始直播
     *
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "startLive", method = RequestMethod.GET)
    @ResponseBody
    String startLive(@RequestParam("cIp") String cIp, @RequestParam("cUsername") String cUserame, @RequestParam("cPassword") String cPassword) throws IOException, InterruptedException {
        return liveService.startLive(cIp, cUserame, cPassword);
    }

    /**
     * 结束直播
     *
     * @param cIp
     * @return
     */
    @RequestMapping(value = "endLive", method = RequestMethod.GET)
    @ResponseBody
    Boolean endLive(@RequestParam("cIp") String cIp) {
        return liveService.endLive(cIp);
    }

    /**
     * 开始截取视频
     * @param ip
     * @param username
     * @param password
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "startSection",method = RequestMethod.GET)
    @ResponseBody
    Boolean startSection(@RequestParam("cIp") String ip,@RequestParam("cUsername") String username,@RequestParam("cPassword") String password,@RequestParam("caseName") String caseName,@RequestParam("pName") String pName) throws IOException, InterruptedException {
        return sectionService.startSection(ip,username,password,caseName,pName);
    }

    /**
     * 停止截取视频
     * @return
     */
    @RequestMapping(value = "endSection",method = RequestMethod.GET)
    @ResponseBody
    Boolean endSection(@RequestParam("cIp") String ip){
        return sectionService.endSection(ip);
    }
    /**
     *
     */
    @RequestMapping(value = "heartBeat")
    public void getHeartBeat(HttpServletRequest req, HttpServletResponse res,@RequestParam("cIp") String cIp){
        res.setContentType("application/x-javascript");
        String callbackFunName =req.getParameter("callbackparam");//得到js函数名称
        ProcessHolder.IP_TIME.put(cIp,System.currentTimeMillis());
        try {
            res.getWriter().write(callbackFunName + "([{result:\"success\"}])");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
