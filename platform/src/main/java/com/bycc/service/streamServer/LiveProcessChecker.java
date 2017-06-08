package com.bycc.service.streamServer;

import com.bycc.service.Live.LiveService;
import com.bycc.service.Live.ProcessHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;

/**
 * Created by wanghaidong on 2017/5/5.
 */
public class LiveProcessChecker implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = Logger.getLogger(LiveProcessChecker.class);
    @Autowired
    private LiveService liveService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //没有if判断event在Spring初始化的过程中会触发2次
        if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
            //检测摄像头流是否非正常中断的线程
            //检测到非正常中断的流，一直尝试重新连接
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (ProcessHolder.liveProcesses.isEmpty()) {
                            //System.out.println("没有摄像头正在直播...");
                            logger.debug("没有摄像头正在直播...");
                        } else {
                            for (String ip : ProcessHolder.liveProcesses.keySet()) {
                                Process process = ProcessHolder.liveProcesses.get(ip);
                                if (!isRunning(process)) {
                                    Integer returnValue = process.exitValue();
//                                System.out.println("该Process的返回值:" + returnValue);
                                    if (returnValue != 0) {
                                        //System.out.println("摄像头Ip:" + ip + " 已经正常结束...");
                                        logger.debug("摄像头Ip:" + ip + " 已经正常结束...");
                                        ProcessHolder.liveProcesses.remove(ip);
                                    } else {
                                        //System.out.println("摄像头Ip:" + ip + " 异常中断..");
                                        logger.debug("摄像头Ip:" + ip + " 异常中断..");
                                        //System.out.println("重新连接:"+ip);
                                        logger.debug("重新连接:" + ip);
                                        String username = ProcessHolder.IP_USERNAME.get(ip);
                                        String password = ProcessHolder.IP_PASSWORD.get(ip);
                                        try {
                                            try {
                                                liveService.startLive(ip, username, password);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                } else {
                                    //System.out.println("摄像头Ip:" + ip + " 正在正常运行...");
                                    logger.debug("摄像头Ip:" + ip + " 正在正常运行...");
                                }
                            }
                        }
                    }
                }
            }).start();


            //每五秒检测每个摄像头的m3u8文件访问情况
            //每10秒判断一次每个摄像头的请求时间，超过一分钟没有请求，则关闭该流
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        //System.out.println("流状态检查...");
                        logger.debug("流状态检查...");
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for (String ip : ProcessHolder.IP_TIME.keySet()){
                            Long lastTime = ProcessHolder.IP_TIME.get(ip);
                            //如果超过5秒没有请求，则关闭该流
                            //System.out.println(System.currentTimeMillis()-lastTime);
                            logger.debug(ip+":距离上次心跳时间（毫秒）:"+(System.currentTimeMillis()-lastTime));
                            if(System.currentTimeMillis()-lastTime>60000){
                                //System.out.println("关闭流！！！");
                                logger.debug(ip+": 该流的最大刷新时间已到，关闭流!");
                                liveService.endLive(ip);
                                ProcessHolder.IP_TIME.remove(ip);
                            }
                        }
                    }
                }
            }).start();
        }


    }

    /**
     * 判断进程是否结束
     *
     * @param process
     * @return
     */
    private boolean isRunning(Process process) {
        try {
            process.exitValue();
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
