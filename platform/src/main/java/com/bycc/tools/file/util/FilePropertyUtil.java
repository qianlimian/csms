/**
 * 
 */
package com.bycc.tools.file.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * @description 读取文件配置文件
 * @author gaoningbo
 * @date 2017年5月16日
 * 
 */
public class FilePropertyUtil {

	public static String getValueByKey(String key) {

		String path = FilePropertyUtil.class.getClassLoader().getResource("").getPath();
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(path + "//properties//file.properties"));
			prop.load(in); /// 加载属性列表
			return prop.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// 测试Dest路径
	public static String getTestDestPath() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String destDir = servletContext.getRealPath("video");
		return destDir;
	}

	// 测试Src路径
	public static String getTestSrcPath() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String destDir = servletContext.getRealPath("videoSrc");
		return destDir;
	}
}
