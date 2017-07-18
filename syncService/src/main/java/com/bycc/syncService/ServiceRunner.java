package com.bycc.syncService;

import com.bycc.syncService.service.Service;
import com.bycc.syncService.service.StatusDetectionService;
import com.bycc.syncService.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-4-18 15:36
 */
public class ServiceRunner {
	private static Logger logger = LoggerFactory.getLogger(ServiceRunner.class);

	public static void main(String[] args) throws InterruptedException {
		// 启动同步服务
		Service service = new SyncService();
		service.start();
		registerShutdownHook(service);

		// 启动案件状态检测服务
		Service statusDetectionService = new StatusDetectionService();
		service.start();
		registerShutdownHook(statusDetectionService);
	}

	/**
	 * 注册服务关闭时处理函数
	 *
	 * @param service 数据抓取服务
	 */
	private static void registerShutdownHook(Service service) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// 应用停止时要停止服务并释放资源
				service.stop();
				logger.info("{}已停止", service.getServiceName());
			}
		});
	}
}
