package com.bycc.syncService.util;

import java.util.Properties;
import java.util.Set;

/**
 * Description: 系统配置
 * User: yumingzhe
 * Time: 2017-4-17 13:28
 */
public class Configuration {
	private static Properties CONFIG = null;

	// 根据key获取配置文件中对应的value
	public static String getProperty(String key) {
		if (CONFIG == null) {
			CONFIG = ConfigParser.parse();
		}
		return CONFIG.getProperty(key);
	}

	/**
	 * 获取配置文件中的key
	 *
	 * @return
	 */
	public static Set<String> getKeys() {
		if (CONFIG == null) {
			CONFIG = ConfigParser.parse();
		}
		return CONFIG.stringPropertyNames();
	}
}
