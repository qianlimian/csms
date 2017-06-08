package com.bycc.mgr.action.streamServer;

import com.bycc.mgr.action.live.LiveServiceImpl;
import com.bycc.mgr.action.live.ProcessHolder;
import org.apache.log4j.Logger;

/**
 * Created by wanghaidong on 2017/5/18.
 */
public class HeartbeatChecker implements Runnable{
    private static Logger logger = Logger.getLogger(HeartbeatChecker.class);
    @Override
    public void run() {
        while (true){
            logger.debug("流状态检查...");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String ip : ProcessHolder.IP_TIME.keySet()){
                Long lastTime = ProcessHolder.IP_TIME.get(ip);
                //如果超过5秒没有请求，则关闭该流
                logger.debug(ip+":距离上次心跳时间（毫秒）:"+(System.currentTimeMillis()-lastTime));
                if(System.currentTimeMillis()-lastTime>60000){
                    //System.out.println("关闭流！！！");
                    logger.debug(ip+": 该流的最大刷新时间已到，关闭流!");
                    LiveServiceImpl.endLive(ip);
                    ProcessHolder.IP_TIME.remove(ip);
                }
            }
        }
    }
}
