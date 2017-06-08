package org.smartframework.utils.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: LogHelper
 * @Description: 日志工具类
 */
public class LogHelper {

	public static int DEBUG = 1;
	public static int INFO = 2;
	public static int WARNING = 3;
	public static int ERROR = 4;

	/**
	 * @Title: log
	 * @Description: 日志函数
	 * @param clazz 类名
	 * @param level 日志等级
	 * @param msg 日志消息
	 * @return void
	 * @throws
	 */
	public static void log(Class<?> clazz, int level, String msg) {
		Logger log = LoggerFactory.getLogger(clazz);
		switch (level) {
		case 1:
			log.debug(msg);
			break;
		case 2:
			log.info(msg);
			break;
		case 3:
			log.warn(msg);
			break;
		case 4:
			log.error(msg);
			break;
		default:
			log.debug(msg);
			break;
		}
	}
	
	/**
	 * @Title: log
	 * @Description: 日志函数
	 * @param clazz 类名
	 * @param level 日志等级
	 * @param msg 日志消息
	 * @return void
	 * @throws
	 */
	public static void log(Class<?> clazz, int level, String msg, Exception e) {
		Logger log = LoggerFactory.getLogger(clazz);
		switch (level) {
		case 1:
			log.debug(msg, e);
			break;
		case 2:
			log.info(msg, e);
			break;
		case 3:
			log.warn(msg, e);
			break;
		case 4:
			log.error(msg, e);
			break;
		default:
			log.debug(msg, e);
			break;
		}
	}

	public static void debug(Class<?> clazz, String msg) {
		log(clazz, LogHelper.DEBUG, msg);
	}
	
	public static void error(Class<?> clazz, String msg) {
		log(clazz, LogHelper.ERROR, msg);
	}
	
	public static void info(Class<?> clazz, String msg) {
		log(clazz, LogHelper.INFO, msg);
	}
	public static void warn(Class<?> clazz, String msg) {
		log(clazz, LogHelper.WARNING, msg);
	}
	
	public static void debug(Class<?> clazz, String msg, Exception e) {
		log(clazz, LogHelper.DEBUG, msg, e);
	}
	
	public static void error(Class<?> clazz, String msg, Exception e) {
		log(clazz, LogHelper.ERROR, msg, e);
	}
	
	public static void info(Class<?> clazz, String msg, Exception e) {
		log(clazz, LogHelper.INFO, msg, e);
	}
	public static void warn(Class<?> clazz, String msg, Exception e) {
		log(clazz, LogHelper.WARNING, msg, e);
	}

}
