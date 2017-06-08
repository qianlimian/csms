package com.bycc.service.streamServer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wanghaidong on 2017/5/5.
 */
public class ClearServerBuffer {
    public static void clearBuffer(Process process) {
        //将流媒体服务器缓冲区的内容清空
        new Thread(new Runnable() {

            @Override
            public void run() {
                BufferedReader reader = null;
                String lineOne = "lineOne";
                try {
                    //清空标准输出
                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    while ((lineOne = reader.readLine()) != null) {
                        //logger.error("lineOne:"+lineOne);
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                BufferedReader reader = null;
                String lineTwo = "lineTwo";
                try {
                    //清空错误流
                    reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    while ((lineTwo = reader.readLine()) != null) {
                        //logger.error("lineTwo:"+lineTwo);
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

}
