package org.smartframework.utils.helper;

import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperPrint;
import org.smartframework.platform.context.SmartContext;
import org.smartframework.platform.context.SpringContext;
import org.smartframework.platform.exception.BusinessException;
import org.smartframework.platform.jasper.constaints.JasperConstaints;
import org.springframework.beans.BeansException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <DL><DT><B>功能名：</B><DD>Jasper报表</DL>
 * <DL><DT><B>类描述：</B><DD>工具类</DL>
 *
 * @author 段春明
 */
public class JasperHelper {
	
	/*-------------------------类成员定义-------------------------*/

	/**
	 * 报表根目录。
	 */
	private static String ROOT_PATH = null;


	/**
	 * 数据源
	 */
	private static DataSource dataSource;
	
	/*-------------------------类方法定义-------------------------*/

	/**
	 * 获取数据库连接
	 *
	 * @throws BusinessException
	 */
	public static Connection getConnection() throws BusinessException {
		// 获取数据源
		String dataSourceBean = "dataSource";
		if (dataSource == null) {
			try {
				dataSource = ((DataSource) SpringContext.getBean(dataSourceBean));
			} catch (BeansException e) {
				throw new BusinessException("调用【Jasper报表】的【创建数据库连接】方法，获取数据源【" + dataSourceBean + "】失败：" + e.getMessage());
			}
		}
		// 获取数据库连接
		Connection connection;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new BusinessException("调用【Jasper报表】的【创建数据库连接】方法，连接数据库失败：" + e.getMessage());
		}
		return connection;
	}

	/**
	 * 获取报表设计文件跟目录
	 */
	public static String getJrxmlRootPath() {
		return getRootPath() + JasperConstaints.JRXML_PATH_PREFIX;
	}

	/**
	 * 获取报表编译文件跟目录
	 */
	public static String getJasperRootPath() {
		return getRootPath() + JasperConstaints.JASPER_PATH_PREFIX;
	}

	/**
	 * 获取根目录。
	 * <br/>默认从WakeContext中获取request请求的路径
	 * <br/>也可通过{@linkplain JasperHelper#setRootPath(String) setRootPath}方法修改
	 */
	public static String getRootPath() {
		if (ROOT_PATH != null) {
			return ROOT_PATH;
		} else {
			HttpServletRequest request = SmartContext.getRequest();
			return SmartContext.getRequest().getSession().getServletContext().getRealPath("") + "/";
		}
	}

	/**
	 * 设置根目录。
	 * <br/>必要时更改此属性以实现报表特殊路径的需求
	 */
	public static void setRootPath(String rootPath) {
		ROOT_PATH = rootPath;
	}

	/**
	 * 替换扩展名
	 */
	public static String replaceFileExtName(String originalFileName, String targetFileExtName) {
		String targetFileName;
		if (originalFileName == null || "".equals(originalFileName)) {
			return originalFileName;
		}
		int lastIndexOfPoint = originalFileName.lastIndexOf(".");
		if (lastIndexOfPoint == -1) {
			targetFileName = originalFileName + "." + targetFileExtName;
		} else {
			targetFileName = originalFileName.substring(0, lastIndexOfPoint) + "." + targetFileExtName;
		}
		return targetFileName;
	}

	/**
	 * 清除编译文件
	 */
	public static void clearJasperReportFiles() {
		List<File> files = getFilesByExtName(getRootPath(), JasperConstaints.FILE_EXT_NAME_JASPER);
		for (File file : files) {
			file.delete();
		}
	}

	/**
	 * 获取指定扩展名文件
	 */
	private static List<File> getFilesByExtName(String directory, String extName) {
		List<File> result = new ArrayList<File>();
		File rootFile = new File(directory);
		for (File file : rootFile.listFiles()) {
			// 文件夹：迭代
			if (file.isDirectory()) {
				result.addAll(getFilesByExtName(file.getAbsolutePath(), extName));
			}
			// 目标文件
			else if (file.getAbsolutePath().endsWith(extName)) {
				result.add(file);
			} else {
				// nothing
			}
		}
		return result;
	}

	/**
	 * 重新计算宽度
	 * <br/>页宽 = 最右组件右位置 + 右边距
	 * <br/>注意：此方法可能会裁剪宽度
	 */
	public static void fitWidth(JasperPrint jasperPrint) {
		// 获取所有元素
		List<JRPrintElement> jrpes = new ArrayList<JRPrintElement>();
		for (JRPrintPage jrPrintPage : jasperPrint.getPages()) {
			jrpes.addAll(jrPrintPage.getElements());
		}
		// 最右
		int right = 0;
		for (JRPrintElement jrpe : jrpes) {
			right = jrpe.getX() + jrpe.getWidth() > right ? jrpe.getX() + jrpe.getWidth() : right;
		}
		// 页宽=最右+右边距
		jasperPrint.setPageWidth(right + jasperPrint.getRightMargin());
	}
}
