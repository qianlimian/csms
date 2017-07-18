package com.bycc;

import com.bycc.core.net.http.HttpServer;
import com.bycc.core.net.udp.UdpServer;


public class App {

    public static void main(String[] args) {
        new Thread(new UdpServerThread()).start();
        new Thread(new HttpServerThread()).start();
    }
}

class UdpServerThread implements Runnable{

	@Override
	public void run() {
    	try {
    		//UDP接收基站数据包
			new UdpServer(32500).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class HttpServerThread implements Runnable{

	@Override
	public void run() {
		try {
        	//HTTP接收请求
        	new HttpServer(8088).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}