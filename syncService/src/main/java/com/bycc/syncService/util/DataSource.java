package com.bycc.syncService.util;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-4-17 13:50
 */
public final class DataSource {
	private static final Logger logger = LoggerFactory.getLogger(DataSource.class);

	// 连接池
	private static javax.sql.DataSource DATASOURCE = null;

	/**
	 * 获取数据库连接
	 *
	 * @return 数据库连接
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DATASOURCE.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 初始化连接池
	 */
	public static void init() {
		if (DATASOURCE != null) {
			return;
		}
		logger.info("初始化数据库连接池...");
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(Configuration.getProperty("db.jdbcUrl"));
		ds.setUsername(Configuration.getProperty("db.username"));
		ds.setPassword(Configuration.getProperty("db.password"));
		ds.setAutoCommit(false);
		ds.setMinimumIdle(Integer.parseInt(Configuration.getProperty("cp.minConnectionNum")));
		ds.setMaximumPoolSize(Integer.parseInt(Configuration.getProperty("cp.maxConnectionNum")));
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		DATASOURCE = ds;
		logger.info("数据库连接池初始化完成");
	}

	/**
	 * 关闭数据库连接池
	 */
	public static void destroy() {
		if (DATASOURCE != null) {
			try {
				DATASOURCE.unwrap(HikariDataSource.class).close();
				logger.info("关闭数据库连接池");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
