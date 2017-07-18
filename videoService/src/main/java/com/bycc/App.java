package com.bycc;

import com.bycc.core.net.http.HttpServer;
import com.bycc.mgr.action.streamServer.HeartbeatChecker;
import com.bycc.mgr.action.streamServer.Reconnector;
import com.bycc.mgr.action.streamServer.SectionChecker;

/**
 * Main class
 */
public class App {

    public static void main(String[] args) {
//        开始流异常重连进程
        Thread reconnector =new Thread(new Reconnector());
        reconnector.setDaemon(true);
//        reconnector.start();
//        开始心跳检测进程
        Thread heartChecker = new Thread(new HeartbeatChecker());
        heartChecker.setDaemon(true);
        heartChecker.start();

        Thread sectionChecker = new Thread(new SectionChecker());
        sectionChecker.setDaemon(true);
        sectionChecker.start();
//        开始监听8080端口的通信
        try {
            new HttpServer(8888).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
