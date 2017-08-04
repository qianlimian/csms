package com.bycc.core.net.udp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bycc.dto.LocateDto;
import com.bycc.dto.RFParams;
import com.bycc.utils.CollusionCacheUtil;
import com.bycc.utils.HttpUtil;
import com.bycc.utils.JsonHelper;
import com.bycc.utils.LocateCacheUtil;
import com.bycc.utils.MessageCacheUtil;
import com.bycc.utils.PropertiesReaderUtil;
import com.bycc.utils.RFHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by Administrator on 2017/6/16.
 */
public class UdpHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    public void  channelRead0(ChannelHandlerContext ctx,  DatagramPacket packet) throws Exception {
    	ByteBuf buf = ReferenceCountUtil.releaseLater(Unpooled.copiedBuffer(packet.content()));
    	byte[] contents = new byte[buf.readableBytes()];
    	buf.readBytes(contents);
    	if (contents.length >= 32) {
    		List<RFParams> rfs = RFHelper.getRFParams(contents);
    		for (RFParams rfParams : rfs) {
    			if (rfParams.getAntenna1ID() - rfParams.getAntenna2ID() == 1) {
    				//进房间或在房间内
    				//从缓存获取房间内人员信息
    				LocateDto dto = (LocateDto) MessageCacheUtil.get(rfParams.getDevId());
    				if (dto != null && !dto.getTagId().equals(rfParams.getTagId())) {
    					Map<String, Object> map = (Map<String, Object>) CollusionCacheUtil.get(rfParams.getTagId());
    					if (map == null) {
							map = new HashMap<>();
							map.put("tag1Id", dto.getTagId()); //房间内人员手环ID
	    					List<Integer> rfpList =  new ArrayList<>();
	    					rfpList.add(rfParams.getTagId());
	    					map.put("tag2Id", rfpList); //进入房间人员手环信息
						} else {
							map.put("tag1Id", dto.getTagId()); //房间内人员手环信息
							List<Integer> rfpList = (List<Integer>) map.get("tag2Id");
							if (!rfpList.contains(rfParams.getTagId())) {
								rfpList.add(rfParams.getTagId());
								map.put("tag2Id", rfpList); //进入房间人员手环信息
							}
						}
    					//记录多人共处一室
						CollusionCacheUtil.put(rfParams.getDevId(), map);
						continue;
					}
    				//房间内只有一人时，删除共处一室记录
    				CollusionCacheUtil.remove(rfParams.getDevId());
    				
    				//请求市局消息并记录人员进入房间时间
    				if (dto == null) {
    					String url = "";
    					if (LocateCacheUtil.get(rfParams.getDevId()) == null) {
    						url = "http://" + PropertiesReaderUtil.readProperty("platformIp") + ":8080/platform/locate/findLocate.do?devId=" + rfParams.getDevId() + "&tagId=" + rfParams.getTagId() + "&person=0";
						} else {
    						url = "http://" + PropertiesReaderUtil.readProperty("platformIp") + ":8080/platform/locate/findLocate.do?devId=" + rfParams.getDevId() + "&tagId=" + rfParams.getTagId() + "&person=1";
						}
    					String result = HttpUtil.sendGet(url, "utf-8");
    					if (result != null && !"Failure".equals(result)) {
    						dto = JsonHelper.getBean(result, LocateDto.class);
    						MessageCacheUtil.put(rfParams.getDevId(), dto);
						}
					}
    				//保存定位信息
    				LocateCacheUtil.put(rfParams.getDevId(), rfParams); 
    				
    				if (dto != null) {
    					MessageCacheUtil.put(rfParams.getDevId(), dto);
    					//开启视频截取
    					//HttpUtil.sendGet("http://172.19.5.229:8888/video/startSection?params=" + JsonHelper.getJson(dto), "utf-8");
					}
				} else if (rfParams.getAntenna1ID() - rfParams.getAntenna2ID() == -1) {
					//出房间或在房间门口
					LocateDto dto = (LocateDto) MessageCacheUtil.get(rfParams.getDevId());
					
					if (dto != null && rfParams.getTagId().equals(dto.getTagId())) {
						//通知人员离开房间时间
						HttpUtil.sendGet("http://" + PropertiesReaderUtil.readProperty("platformIp") + ":8080/platform/locate/leave.do?traceId=" + dto.getTraceId(), "utf-8");
						//关闭视频截取
						//HttpUtil.sendGet("http://172.19.5.229:8888/video/endSection?params=" + JsonHelper.getJson(dto), "utf-8");
						LocateCacheUtil.remove(rfParams.getDevId());  //删除定位信息
					}
				}
			}
		}
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //ctx.close();
        cause.printStackTrace();
    }
}
