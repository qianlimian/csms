package com.bycc.syncService.service;

import com.bycc.syncService.entity.Case;
import com.bycc.syncService.util.Converter;
import com.bycc.syncService.util.DataSource;
import com.bycc.syncService.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Description:数据同步服务
 * User: yumingzhe
 * Time: 2017-4-17 11:06
 */
public class SyncService implements Service {
	private static Logger logger = LoggerFactory.getLogger(SyncService.class);

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
		DataSource.init();

		// 创建一个单线程的线程池
		THREAD_POOL = Executors.newSingleThreadScheduledExecutor();

		// 应用启动后3秒开始执行数据同步任务，任务每三分钟执行一次
		TASK = THREAD_POOL.scheduleAtFixedRate(createSyncTask(), DELAY, PERIOD, TimeUnit.SECONDS);
		logger.info("服务已启动");
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

	// 定义数据同步业务逻辑
	private Runnable createSyncTask() {
		return () -> {
			Connection connection = null;
			try {
				// 访问接口抓取数据
				// TODO: 2017-4-19 接口待定
				String response = HttpUtil.get("");

				// 将数据转换为实体
				Case entity = Converter.jsonToEntity(response, Case.class);

				connection = DataSource.getConnection();

				// 根据警情号查询是否已同步记录
				PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM caze WHERE alarm_code_ = ?");
				statement.setString(1, entity.getAlarmCode());
				boolean exist = statement.executeQuery().getInt(1) > 0;

				// TODO: 2017-4-19 批量更新
				if (exist) {// 更新记录
					statement = connection.prepareStatement("UPDATE caze SET case_name_ = ?, suspect_=?");
					statement.setString(1, entity.getCaseName());
					statement.setString(2, entity.getSuspect());
					statement.executeUpdate();
					connection.commit();
					logger.debug("更新案件信息: {{}}", entity);
				} else {// 新增记录

					// 获取新id，并更新id_sequence
					statement = connection.prepareStatement("SELECT gen_value_ FROM id_sequence WHERE key_id_ = 'com.bycc.entity.Case' FOR UPDATE ");
					int genValue = statement.executeQuery().getInt("gen_value_");
					int nextGenValue = genValue + 1;
					statement = connection.prepareStatement("UPDATE id_sequence SET gen_value_ = ? WHERE key_id_ = 'com.bycc.entity.Case'");
					statement.setInt(1, nextGenValue);
					statement.executeUpdate();
					connection.commit();
					logger.debug("更新id_sequence[key_id_ = 'com.bycc.entity.Case',gen_value_={}]", nextGenValue);

					// 保存
					statement = connection.prepareStatement("INSERT INTO caze(id_,alarm_code_,case_code_,case_name_,case_summary,insert_date_) VALUES (?,?,?,?,?,?)");
					statement.setInt(1, nextGenValue);
					statement.setString(2, entity.getAlarmCode());
					statement.setString(3, entity.getCaseCode());
					statement.setString(4, entity.getCaseName());
					statement.setString(5, entity.getCaseSummary());
					statement.setTimestamp(6, new Timestamp(new java.util.Date().getTime()));
					statement.executeUpdate();
					connection.commit();
					logger.debug("新增案件信息: {{}}", entity);
				}
			} catch (IOException e) {
				logger.error("数据读取失败，原因:", e);
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				logger.error("数据更新失败，原因:{}", e);
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						logger.error("数据库连接关闭异常，原因:{}", e);
					}
				}
			}
		};
	}
}
