package com.bycc.syncService.service;

import com.bycc.syncService.dao.CaseDao;
import com.bycc.syncService.entity.Case;
import com.bycc.syncService.util.Converter;
import com.bycc.syncService.util.DataSource;
import com.bycc.syncService.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Description:数据同步服务
 * User: yumingzhe
 * Time: 2017-4-17 11:06
 */
@org.springframework.stereotype.Service
public class SyncService implements Service {
	private static Logger logger = LoggerFactory.getLogger(SyncService.class);

	@Autowired
	private CaseDao caseDao;

	// 服务名称
	private static final String SERVICE_NAME = "数据同步服务";

	// 定时任务
	private static ScheduledFuture TASK = null;

	// 线程池
	private static ScheduledExecutorService THREAD_POOL = null;

	private static Long ONE_SECOND = 1L;

	private static Long ONE_MINUTE = 60L;

	// 任务启动时延迟执行的秒数
	private static Long DELAY = 3 * ONE_SECOND;

	// 任务每多少秒执行一次
	private static Long PERIOD = 3 * ONE_SECOND;

	/**
	 * 启动服务
	 */
	@Override
	public void start() {
		// 初始化数据库连接池
		//DataSource.init();

		// 创建一个单线程的线程池
		THREAD_POOL = Executors.newSingleThreadScheduledExecutor();

		// 应用启动后3秒开始执行数据同步任务，任务每三分钟执行一次
		TASK = THREAD_POOL.scheduleAtFixedRate(createSyncTask(), DELAY, PERIOD, TimeUnit.SECONDS);
		logger.info("{}已启动", SERVICE_NAME);
	}

	/**
	 * 停止服务
	 */
	@Override
	public void stop() {
		//停止任务
		if (TASK != null) {
			TASK.cancel(true);
		}

		// 关闭线程池
		if (THREAD_POOL != null) {
			THREAD_POOL.shutdownNow();
		}

		// 销毁数据库连接池
		DataSource.destroy();
	}

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}

	// 定义数据同步业务逻辑
	@Transactional
	private Runnable createSyncTask() {
		return () -> {
			Connection connection = null;
			try {
				// 访问接口抓取数据
				// TODO: 2017-4-19 接口待定
				String response = HttpUtil.get("");

				// 将数据转换为实体
				List<Case> cases = Converter.jsonToEntity(response, List.class);

				for (Case entity : cases) {
					// 根据警情号查询是否已同步记录
					Case caze = caseDao.findByAlarmCode(entity.getAlarmCode());
					if (caze != null) {// 更新记录
						caze.setCaseName(entity.getCaseName());
						caze.setCaseSummary(entity.getCaseSummary());
						caze.setCaseType(entity.getCaseType());
						caze.setRiskLevel(entity.getRiskLevel());
						caze.setOccurDate(entity.getOccurDate());
						caze.setNote(entity.getNote());
						caseDao.save(caze);
						logger.debug("更新案件信息: {{}}", entity);
					} else {// 新增记录
						caseDao.save(entity);
						logger.debug("新增案件信息: {{}}", entity);
					}
				}
			} catch (IOException e) {
				logger.error("数据读取失败，原因:", e);
			}
		};
	}
}
