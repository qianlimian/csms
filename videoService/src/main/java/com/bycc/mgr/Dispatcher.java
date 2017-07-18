package com.bycc.mgr;

import com.bycc.core.net.http.HttpInHandler;
import com.bycc.core.router.RouteResult;
import com.bycc.core.router.Router;
import com.bycc.dto.CameraDto;
import com.bycc.dto.LocateDto;
import com.bycc.mgr.action.live.LiveServiceImpl;
import com.bycc.mgr.action.section.SectionServiceImpl;
import com.bycc.utils.JsonHelper;
import com.bycc.utils.RequestParser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;

/**
 * 请求路由分发
 */
public class Dispatcher {

    private Router router;

    public Dispatcher() {
        //定义路由规则
        this.router = new Router()
                .GET("/video/startLive", LiveServiceImpl.class)
                .GET("/video/endLive", LiveServiceImpl.class)
                .GET("/video/heartbeat", LiveServiceImpl.class)
                .GET("/video/startSection", SectionServiceImpl.class)
                .GET("/video/endSection", SectionServiceImpl.class);
    }

    public void route(ChannelHandlerContext ctx, FullHttpRequest req) throws IOException, InterruptedException {


        //从FullHttpRequest中解析出请求参数
        Map<String, String> parmMap = new RequestParser(req).parse();
        System.out.println(parmMap);
        String ip = parmMap.get("cIp");
        String username = parmMap.get("cUsername");
        String password = parmMap.get("cPassword");
        String caseName = parmMap.get("caseName");
        String pName = parmMap.get("pName");
        String roomType = parmMap.get("roomType");
        String params = parmMap.get("params");
        LocateDto locateDto = new LocateDto();
        List<CameraDto> cameraDtoList = null;
        String caseNameSection = null;
        String pNameSection = null;
        String roomTypeSection = null;
        if (params!=null){
            locateDto = JsonHelper.getBean(params, LocateDto.class);
            cameraDtoList = locateDto.getCameraDtos();
            caseNameSection = locateDto.getCaseRecoredId().toString();
            pNameSection = locateDto.getCasePeopleId().toString();
            roomTypeSection = locateDto.getRoomType();
        }



        if (req.uri().equals("/favicon.ico")) {
            return;
        }

        //查询匹配router
        RouteResult<Class<?>> routeResult = router.route(req.method(), req.uri());


        if (routeResult == null) {
            HttpInHandler.writeJSON(ctx, NOT_FOUND, "Not Found");
            return;
        }

        Class<?> action = routeResult.target();
        System.out.println("action handler:" + action);

        //判断逻辑：由不同的action handler来处理不同的request请求
        //TODO 需要自己定义不同的route规则（可以根据path，参数，CONST等）

//        处理直播请求
        String base = req.uri().split("\\?")[0];
        if (action == LiveServiceImpl.class) {
            if ("/video/startLive".equals(base)) {

                HttpInHandler.writeJSON(ctx, LiveServiceImpl.startLive(ip, username, password));
            }
            if ("/video/endLive".equals(base)) {
                HttpInHandler.writeJSON(ctx, LiveServiceImpl.endLive(ip));
            }
            if ("/video/heartbeat".equals(base)) {
                HttpInHandler.writeJSON(ctx, LiveServiceImpl.processHeartbeat(ip));
            }
        }
//        处理截取请求
        if (action == SectionServiceImpl.class) {
            if ("/video/startSection".equals(base)) {
                for (CameraDto cameraDto : cameraDtoList) {
                    HttpInHandler.writeJSON(ctx, SectionServiceImpl.startSection(cameraDto.getCameraIp(), cameraDto.getCameraUserName(), cameraDto.getCameraUserPassword(), caseNameSection, pNameSection, roomTypeSection));
                }

            }
            if ("/video/endSection".equals(base)) {
                for (CameraDto cameraDto : cameraDtoList) {
                    HttpInHandler.writeJSON(ctx, SectionServiceImpl.endSection(cameraDto.getCameraIp()));
                }


            }
        }
    }
}
