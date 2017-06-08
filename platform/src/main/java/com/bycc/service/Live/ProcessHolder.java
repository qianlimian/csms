package com.bycc.service.Live;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaidong on 2017/5/3.
 */
public class ProcessHolder {
    //直播ip地址与进程的对应关系
    public static Map<String,Process> liveProcesses= new HashMap<>();
    //切片ip地址与进程的对应关系
    public static Map<String,Process> sectionProcesses= new HashMap<>();
    //ip与用户名的对应关系
    public static Map<String,String> IP_USERNAME = new HashMap<>();
    //ip与密码的对应关系
    public static Map<String,String> IP_PASSWORD = new HashMap<>();
    //ip与最后一次m3u8请求时间的对应关系
    public static Map<String,Long> IP_TIME = new HashMap<>();
}
