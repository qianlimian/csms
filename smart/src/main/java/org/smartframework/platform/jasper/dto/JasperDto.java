package org.smartframework.platform.jasper.dto;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import net.sf.jasperreports.engine.JasperFillManager;
import org.smartframework.platform.jasper.constaints.JasperConstaints;
import org.smartframework.utils.helper.JsonHelper;

import java.io.IOException;
import java.util.Map;

/**
 * Jasper报表-数据传输对象
 *
 * @author 刘光银（2013年12月21日 上午9:48:46）
 * @author 段春明
 */
public class JasperDto {
	
	/*-----------------------类成员定义------------------------*/

	/**
	 * 报表名称
	 * <br/>必要。
	 * <br/>报表设计文件相对于{@linkplain JasperConstaints#JRXML_PATH_PREFIX JRXML_PATH_PREFIX}的路径名
	 * <br/>报表编译文件相对于{@linkplain JasperConstaints#JASPER_PATH_PREFIX JASPER_PATH_PREFIX}的路径名
	 * <br/>注意 不包括扩展名
	 */
	private String reportName;

	/**
	 * 导出类型
	 * <br/>必要。
	 * <br/>支持的类型：
	 * {@linkplain JasperConstaints#EXPORT_TYPE_VIEW EXPORT_TYPE_VIEW}、
	 * {@linkplain JasperConstaints#EXPORT_TYPE_PDF EXPORT_TYPE_PDF}、
	 * {@linkplain JasperConstaints#EXPORT_TYPE_WORD EXPORT_TYPE_WORD}、
	 * {@linkplain JasperConstaints#EXPORT_TYPE_EXCEL EXPORT_TYPE_EXCEL}、
	 * {@linkplain JasperConstaints#EXPORT_TYPE_PRINT EXPORT_TYPE_PRINT}
	 */
	private String exportType;

	/**
	 * 导出文件名
	 * <br/>非必要。
	 * <br/>导出文件名，不包含扩展名
	 */
	private String exportName;

	/**
	 * 报表参数
	 * <br/>填充报表方法{@linkplain JasperFillManager#fillReport}中使用
	 */
	private Map<String, Object> jasperFillParams;
	
	/*--------------------- 构造方法（@QueryParam注入需要） ------------------------*/

	public JasperDto() {
		// nothing
	}

	public JasperDto(String json) {
		// nothing
	}

	public static JasperDto valueOf(String json) throws JsonParseException, JsonMappingException, IOException {
		JasperDto condition = JsonHelper.getBean(json, JasperDto.class);
		return condition;
	}
	
	/*-----------------------类方法定义------------------------*/
	
	/*-----------------------GET&SET------------------------*/

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getExportName() {
		return exportName;
	}

	public void setExportName(String exportName) {
		this.exportName = exportName;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public Map<String, Object> getJasperFillParams() {
		return jasperFillParams;
	}

	public void setJasperFillParams(Map<String, Object> jasperFillParams) {
		this.jasperFillParams = jasperFillParams;
	}

}
