package com.bycc.mgr.action.streamServer;

import com.bycc.mgr.action.live.ProcessHolder;
import com.bycc.mgr.action.section.SectionServiceImpl;
import org.apache.log4j.Logger;

/**
 * Created by wanghaidong on 2017/6/29.
 */
public class SectionChecker implements Runnable{
    private static Logger logger = Logger.getLogger(SectionChecker.class);
    @Override
    public void run() {
        while (true){
            logger.debug("点播状态检查...");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String ip : ProcessHolder.IP_SectionTime.keySet()){
                Long lastTime = ProcessHolder.IP_SectionTime.get(ip);
                //如果超过5秒没有请求，则关闭该流
                logger.debug(ip+":距离上次点播请求时间（毫秒）:"+(System.currentTimeMillis()-lastTime));
                if(System.currentTimeMillis()-lastTime>60000){
                    //System.out.println("关闭流！！！");
                    logger.debug(ip+": 超过等待时间，结束视频录制!");
//                    LiveServiceImpl.endLive(ip);
//                    ProcessHolder.IP_TIME.remove(ip);
                    SectionServiceImpl.endSection(ip);
                }
            }
        }
    }
}
