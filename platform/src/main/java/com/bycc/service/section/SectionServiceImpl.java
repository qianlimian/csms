package com.bycc.service.section;

import com.bycc.service.Live.ProcessHolder;
import com.bycc.service.streamServer.ClearServerBuffer;
import org.smartframework.utils.helper.DateHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by wanghaidong on 2017/5/3.
 */
@Service
public class SectionServiceImpl implements SectionService{
    @Override
    public Boolean startSection(String cIp,String cUsername,String cPassword,String caseName,String pName) throws IOException, InterruptedException {
        String ip = cIp;
        String username = cUsername;
        String password = cPassword;
        //截取的mp4文件存放的位置
        String desDirectory = "/root/tmpVideo"+"/"+caseName+"/"+pName+"/"+getDay();
        //摄像头rtsp流端口
        String port = "554";

        //查看该案件下该人员的视频目录是否存在
        File file = new File(desDirectory);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }

        //启动切片进程
        String ipAddress = "rtsp://"+username+":"+password+"@"+ip+":"+port;
        String command = "ffmpeg -y -i "+ipAddress+" -vcodec copy -acodec copy -f mp4 "+desDirectory+"/"+getSec()+".mp4";
        Process process=Runtime.getRuntime().exec(command);
        Thread.sleep(3000);
        //3秒后判断是否连接成功
        if (isRunning(process)) {
            ClearServerBuffer.clearBuffer(process);
            ProcessHolder.sectionProcesses.put(ip,process);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean endSection(String cIp) {
        String ip = cIp;
        Process process = ProcessHolder.sectionProcesses.get(ip);
        if (process!=null){
            process.destroy();
            ProcessHolder.sectionProcesses.remove(ip);
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
     * 获取当天的日期
     * @return
     */
    private String getDay(){
        String s = DateHelper.formatDateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        String pre = s.split(" ")[0];
        return StringUtils.replace(pre,"-","_");
    }

    /**
     * 获取当前的时秒
     * @return
     */
    private String getSec(){
        String s = DateHelper.formatDateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        String pre = s.split(" ")[1];
        return StringUtils.replace(pre,":","_");
    }
}
