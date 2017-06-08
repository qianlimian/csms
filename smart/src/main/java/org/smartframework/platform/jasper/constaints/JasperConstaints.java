package org.smartframework.platform.jasper.constaints;

/**
 * Jasper报表-常量
 * @author 段春明
 */
public final class JasperConstaints {

	/*----↓导出类型↓----*/
	
	/**
	 * 导出类型-PDF
	 */
	public static final String EXPORT_TYPE_PDF = "PDF";
	
	/**
	 * 导出类型-WORD
	 */
	public static final String EXPORT_TYPE_WORD = "WORD";
	
	/**
	 * 导出类型-EXCEL
	 */
	public static final String EXPORT_TYPE_EXCEL = "EXCEL";
	
	/**
	 * 导出类型-预览
	 */
	public static final String EXPORT_TYPE_VIEW = "VIEW";
	
	/**
	 * 导出类型-打印
	 */
	public static final String EXPORT_TYPE_PRINT = "PRINT";
	
	/*----↑导出类型↑----*/

	/*----↓扩展名↓----*/
	
	/**
	 * 报表设计文件-JRXML
	 */
	public static final String FILE_EXT_NAME_JRXML = ".jrxml";
	
	/**
	 * 报表编译文件-JASPER
	 */
	public static final String FILE_EXT_NAME_JASPER = ".jasper";
	
	/**
	 * 文件扩展名-PDF
	 */
	public static final String FILE_EXT_NAME_PDF = ".pdf";
	
	/**
	 * 文件扩展名-WORD
	 */
	public static final String FILE_EXT_NAME_WORD = ".doc";
	
	/**
	 * 文件扩展名-EXCEL
	 */
	public static final String FILE_EXT_NAME_EXCEL = ".xls";
	
	/*----↑扩展名↑----*/
	
	/**
	 * 默认导出文件名
	 */
	public static final String DEFAULT_FILE_NAME = "report";
	
	/**
	 * 报表设计文件路径前缀。
	 * <br/>即.jrxml文件
	 * <br/>必要时更改此属性以实现报表特殊路径的需求
	 */
	public static String JRXML_PATH_PREFIX = "report/";
	
	/**
	 * 报表编译文件路径前缀。
	 * <br/>即.jasper文件
	 * <br/>必要时更改此属性以实现报表特殊路径的需求
	 */
	public static String JASPER_PATH_PREFIX = "report/";
	
	/**
	 * 编码方式
	 */
	public static String CHARSET = "UTF-8";
}
