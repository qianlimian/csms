package com.bycc.mgr.action.live;



import com.bycc.utils.PropertiesReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by wanghaidong on 2017/5/3.
 */

public class LiveServiceImpl  {
    public static String startLive(String cIp, String cUsername, String cPassword) throws IOException, InterruptedException {
        System.out.println("__________________In Start Live Service_______________");
        String ip = cIp;
        String username = cUsername;
        String password = cPassword;
        //流媒体服务器地址
//        String streamServer = "172.19.5.228";
        String streamServer = PropertiesReader.readProperty("streamServerIp");
        //直播切片文件的存放位置,Nginx的根目录
//        String desDirectory = "/usr/share/nginx/html";
        String desDirectory = PropertiesReader.readProperty("rootDirectory");
        //摄像头rtsp流端口
//        String port = "554";
        String port = PropertiesReader.readProperty("cameraPort");

        String videoDes = "http://" + streamServer + "/" + getIpString(ip) + "/playlist.m3u8";

        //判断该camera是否已经打开流
        //保证一个摄像头不会打开很多切片进程
        if ((ProcessHolder.liveProcesses.get(ip) != null)&&isRunning(ProcessHolder.liveProcesses.get(ip))) {
            return videoDes;
        }



        File file = new File(desDirectory + "/" + getIpString(ip));
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        } else {
            deleteAllFilesOfDir(file);
        }


        //启动切片进程
        String ipAddress = "rtsp://" + username + ":" + password + "@" + ip + ":" + port;
        String command = "ffmpeg  -loglevel quiet -rtsp_transport tcp -stimeout 2000000 -i " + ipAddress + " -vcodec copy -acodec copy -strict 2 -f hls -hls_time 1 -hls_list_size 5 -hls_wrap 100 -g 15 " + desDirectory + "/" + getIpString(ip) + "/playlist.m3u8";
        System.out.println("command: "+command);
        Process process = Runtime.getRuntime().exec(command);
        //process.waitFor();
        //链接摄像头，设置3秒的等待时间
        Thread.sleep(3000);
        //3秒后判断是否连接成功
        if (isRunning(process)) {
            //ClearServerBuffer.clearBuffer(process);
            ProcessHolder.liveProcesses.put(ip, process);
            ProcessHolder.IP_PASSWORD.put(ip, password);
            ProcessHolder.IP_USERNAME.put(ip, username);
            ProcessHolder.IP_TIME.put(ip,System.currentTimeMillis());
            return videoDes;
        } else {
            return "Failure";
        }


    }


    public static Boolean endLive(String cIp) {
        System.out.println("__________________In End Live Service_______________");
        String ip = cIp;
        if (ProcessHolder.liveProcesses.containsKey(ip)){
            Process process = ProcessHolder.liveProcesses.get(ip);
            if (process != null) {
                process.destroy();
                ProcessHolder.liveProcesses.remove(ip);
                return true;
            }
        }

        return false;
    }

    /**
     * 判断进程是否在运行
     *
     * @param process
     * @return
     */
    private static boolean isRunning(Process process) {
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
    private static String getIpString(String ip) {
        return ip.replaceAll("\\.","_");
    }

    /**
     * 删除目录下的所有文件
     * @param path
     */
    private static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
//        path.delete();
    }

    /**
     * 接收心跳
     * @param ip
     * @return
     */
    public static Boolean processHeartbeat(String ip){
        ProcessHolder.IP_TIME.put(ip,System.currentTimeMillis());
        if (ProcessHolder.IP_TIME.get(ip)!=null){
            return true;
        }else {
            return false;
        }
    }


}
