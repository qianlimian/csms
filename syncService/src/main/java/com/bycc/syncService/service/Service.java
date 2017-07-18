package com.bycc.syncService.service;

/**
 * 数据同步服务接口
 */
public interface Service {
	void start();

	void stop();

	String getServiceName();

}
