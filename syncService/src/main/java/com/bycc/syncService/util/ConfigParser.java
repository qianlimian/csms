package com.bycc.syncService.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件解析工具
 * User: yumingzhe
 * Time: 2017-4-17 13:12
 */
public class ConfigParser {
	private static final Logger logger = LoggerFactory.getLogger(ConfigParser.class);

	/**
	 * 解析配置文件
	 *
	 * @return 配置对象
	 */
	public static Properties parse() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			// 加载配置文件
			input = ConfigParser.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(input);
			if (logger.isDebugEnabled()) {
				logger.debug("加载配置文件：");
				for (String key : prop.stringPropertyNames()) {
					logger.debug("{}:{}", key, prop.getProperty(key));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return prop;
	}
}
