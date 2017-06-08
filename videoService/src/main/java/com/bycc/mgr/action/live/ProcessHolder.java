package com.bycc.mgr.action.live;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wanghaidong on 2017/5/3.
 */
public class ProcessHolder {
    //直播ip地址与进程的对应关系
    public static Map<String,Process> liveProcesses = new ConcurrentHashMap<>();
    //切片ip地址与进程的对应关系
    public static Map<String,Process> sectionProcesses= new ConcurrentHashMap<>();
    //ip与用户名的对应关系
    public static Map<String,String> IP_USERNAME = new ConcurrentHashMap<>();
    //ip与密码的对应关系
    public static Map<String,String> IP_PASSWORD = new ConcurrentHashMap<>();
    //ip与最后一次m3u8请求时间的对应关系
    public static Map<String,Long> IP_TIME = new ConcurrentHashMap<>();
    //ip与切片开始时间的对应关系
    public static Map<String,String> IP_START = new ConcurrentHashMap<>();
}
