package com.bycc.mgr.action.streamServer;

import com.bycc.mgr.action.live.LiveServiceImpl;
import com.bycc.mgr.action.live.ProcessHolder;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by wanghaidong on 2017/5/18.
 */
public class Reconnector implements Runnable {
    private static Logger logger = Logger.getLogger(Reconnector.class);
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
                                    LiveServiceImpl.startLive(ip, username, password);
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
