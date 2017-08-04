package com.bycc.syncService.service;

import com.bycc.syncService.dao.CaseDao;
import com.bycc.syncService.entity.Case;
import com.bycc.syncService.util.Converter;
import com.bycc.syncService.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-6-15 9:44
 */
@org.springframework.stereotype.Service
public class StatusDetectionService implements Service {
	private static Logger logger = LoggerFactory.getLogger(StatusDetectionService.class);

	@Autowired
	private CaseDao caseDao;

	// 服务名称
	private static final String SERVICE_NAME = "案件状态检测服务";

	// 定时任务
	private static ScheduledFuture STATUS_DETECTION_TASK = null;

	// 线程池
	private static ScheduledExecutorService THREAD_POOL = null;

	private static Long ONE_SECOND = 1L;

	private static Long ONE_MINUTE = 60L;

	// 任务启动时延迟执行的秒数
	private static Long DELAY = 3 * ONE_SECOND;

	// 任务每多少秒执行一次
	private static Long PERIOD = 3 * ONE_SECOND;

	@Override
	public void start() {
		// 创建一个单线程的线程池
		THREAD_POOL = Executors.newSingleThreadScheduledExecutor();

		// 应用启动后3秒开始执行数据同步任务，任务每三分钟执行一次
		STATUS_DETECTION_TASK = THREAD_POOL.scheduleAtFixedRate(createDetectionTask(), DELAY, PERIOD, TimeUnit.SECONDS);
		logger.info("{}已启动", SERVICE_NAME);
	}

	// 定义状态检测逻辑
	@Transactional
	private Runnable createDetectionTask() {
		return () -> {
			List<Case> cases = caseDao.findUnCloseCase();
			if (!cases.isEmpty()) {
				//从闭环中查询未结案的警情信息
				for (Case entity : cases) {
					// TODO: 2017-06-15 接口待定
					String response = null;
					try {
						response = HttpUtil.get("" + entity.getAlarmCode());
						// 将数据转换为实体
						Case caze = Converter.jsonToEntity(response, Case.class);
						// 更新状态
						Case record = caseDao.findByAlarmCode(entity.getAlarmCode());
						record.setCaseStatus(caze.getCaseStatus());
						caseDao.save(record);
						logger.debug("更新案件状态: {{}}", entity);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	@Override
	public void stop() {
		//停止任务
		if (STATUS_DETECTION_TASK != null) {
			STATUS_DETECTION_TASK.cancel(true);
		}

		// 关闭线程池
		if (THREAD_POOL != null) {
			THREAD_POOL.shutdownNow();
		}
	}

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}
}
