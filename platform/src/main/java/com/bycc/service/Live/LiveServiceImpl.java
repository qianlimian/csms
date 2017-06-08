package com.bycc.service.Live;

import com.bycc.service.streamServer.ClearServerBuffer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by wanghaidong on 2017/5/3.
 */
@Service
public class LiveServiceImpl implements LiveService {
    @Override
    public String startLive(String cIp, String cUsername, String cPassword) throws IOException, InterruptedException {
        System.out.println("__________________In Start Live Service_______________");
        String ip = cIp;
        String username = cUsername;
        String password = cPassword;
        //流媒体服务器地址
        String streamServer = "172.19.5.228";
        //直播切片文件的存放位置,Nginx的根目录
        String desDirectory = "/usr/share/nginx/html";
        //摄像头rtsp流端口
        String port = "554";

        String videoDes = "http://" + streamServer + "/" + getIpString(ip) + "/playlist.m3u8";

        //判断该camera是否已经打开流
        //保证一个摄像头不会打开很多切片进程
        if ((ProcessHolder.liveProcesses.get(ip) != null)&&isRunning(ProcessHolder.liveProcesses.get(ip))) {
            return videoDes;
        }



        File file = new File(desDirectory + "/" + getIpString(ip));
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        } else {
            //如果该目录已经存在先将目录清空
            String clearCommand = "rm -rf " + desDirectory + "/" + getIpString(ip) + "/*";
            Runtime.getRuntime().exec(clearCommand);
        }


        //启动切片进程
        String ipAddress = "rtsp://" + username + ":" + password + "@" + ip + ":" + port;
        String command = "ffmpeg -rtsp_transport tcp -stimeout 2000000 -i " + ipAddress + " -vcodec copy -acodec copy -strict 2 -f hls -hls_time 0.5 -hls_list_size 5 -hls_wrap 100 -g 15 " + desDirectory + "/" + getIpString(ip) + "/playlist.m3u8";
        Process process = Runtime.getRuntime().exec(command);
        //链接摄像头，设置3秒的等待时间
        Thread.sleep(3000);
        //3秒后判断是否连接成功
        if (isRunning(process)) {
            ClearServerBuffer.clearBuffer(process);
            ProcessHolder.liveProcesses.put(ip, process);
            ProcessHolder.IP_PASSWORD.put(ip, password);
            ProcessHolder.IP_USERNAME.put(ip, username);
            ProcessHolder.IP_TIME.put(ip,System.currentTimeMillis());
            return videoDes;
        } else {
            return "Failure";
        }


    }

    @Override
    public Boolean endLive(String cIp) {
        System.out.println("__________________In End Live Service_______________");
        String ip = cIp;
        Process process = ProcessHolder.liveProcesses.get(ip);
        if (process != null) {
            process.destroy();
            ProcessHolder.liveProcesses.remove(ip);
            return true;
        }
        return false;
    }

    /**
     * 判断进程是否在运行
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

    /**
     * 获取Camera的ip作为目录名
     *
     * @param ip
     * @return
     */
    private String getIpString(String ip) {
        return StringUtils.replace(ip,".","_");
    }
}
