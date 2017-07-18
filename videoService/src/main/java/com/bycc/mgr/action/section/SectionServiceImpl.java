package com.bycc.mgr.action.section;

import com.bycc.mgr.action.live.ProcessHolder;
import com.bycc.utils.PropertiesReader;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wanghaidong on 2017/5/3.
 */
public class SectionServiceImpl {
    public static Boolean startSection(String cIp,String cUsername,String cPassword,String caseName,String pName,String roomType) throws IOException, InterruptedException {
        String ip = cIp;
        String username = cUsername;
        String password = cPassword;
        //截取的mp4文件存放的位置
        String sectionDirectory = PropertiesReader.readProperty("sectionDirectory");
        String desDirectory = sectionDirectory+"/"+caseName+"/"+pName;
        //摄像头rtsp流端口
        String port = "554";

        //查看该案件下该人员的视频目录是否存在
        File file = new File(desDirectory);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }

        //如果该摄像头的切片进程已经启动
        //先更新请求时间，然后返回
        if (ProcessHolder.sectionProcesses.get(ip)!=null){
            ProcessHolder.IP_SectionTime.put(ip,System.currentTimeMillis());
            return true;
        }

        //启动切片进程
        String ipAddress = "rtsp://"+username+":"+password+"@"+ip+":"+port;
        String startTime = System.currentTimeMillis()+"";
        String command = "ffmpeg -loglevel quiet -y -i "+ipAddress+" -vcodec copy -acodec copy -f mp4 "+desDirectory+"/"+roomType+"_"+startTime+".mp4";
        ProcessHolder.IP_START.put(cIp,desDirectory+"/"+roomType+"_"+startTime);
        Process process=Runtime.getRuntime().exec(command);
        Thread.sleep(3000);
        //3秒后判断是否连接成功
        if (isRunning(process)) {
            //ClearServerBuffer.clearBuffer(process);
            ProcessHolder.sectionProcesses.put(ip,process);
            ProcessHolder.IP_SectionTime.put(ip,System.currentTimeMillis());
            return true;
        } else {
            return false;
        }

    }

    public static Boolean endSection(String cIp) {
        String ip = cIp;
        Process process = ProcessHolder.sectionProcesses.get(ip);
        if (process!=null){
            process.destroy();
            ProcessHolder.sectionProcesses.remove(ip);
            ProcessHolder.IP_SectionTime.remove(ip);
            String endTime = System.currentTimeMillis()+"";
            String oldfile = ProcessHolder.IP_START.get(cIp);
            String newfile = oldfile+"_"+endTime+".mp4";
            if(renameFile(oldfile,newfile)){
                ProcessHolder.IP_START.remove(cIp);
            }else {
                return false;
            }
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
    private static boolean isRunning(Process process) {
        try {
            process.exitValue();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 获取当天的日期
     * @return
     */
    private static String getDay(){
        String s = formatDateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        String pre = s.split(" ")[0];
        return pre.replaceAll("-","_");
    }

    /**
     * 获取当前的时秒
     * @return
     */
    private static String getSec(){
        String s = formatDateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        String pre = s.split(" ")[1];
        return pre.replaceAll(":","_");
    }

    /**
     * 转换日期为字符串
     * @param changeDate
     * @param pattern
     * @return
     */
    private static String formatDateToString(Date changeDate, String pattern){
        if(changeDate == null){
            return null;
        } else {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(changeDate);
        }
    }

    /**
     * 重命名视频文件，添加结束时间
     * @param oldName
     * @param newName
     * @return
     */
    private static boolean renameFile(String oldName,String newName){
        File oldfile = new File(oldName+".mp4");
        File newfile = new File(newName);
        if (oldfile.exists()){
            oldfile.renameTo(newfile);
            return true;
        }else {
            return false;
        }
    }
}
