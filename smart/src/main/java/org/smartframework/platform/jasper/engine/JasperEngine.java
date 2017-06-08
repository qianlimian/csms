package org.smartframework.platform.jasper.engine;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.smartframework.platform.context.SmartContext;
import org.smartframework.platform.exception.BusinessException;
import org.smartframework.platform.jasper.constaints.JasperConstaints;
import org.smartframework.platform.jasper.dto.JasperDto;
import org.smartframework.utils.helper.JasperHelper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <DL><DT><B>功能名：</B><DD>jasper报表</DL>
 * <DL><DT><B>类描述：</B><DD>引擎类</DL>
 * <DL><DT><B>说明：</B><DD>
 * 正常情况下直接调用{@linkplain JasperEngine#getResponse getResponse}方法，将文件输出到servlet回执。
 * <br/>此过程可分解为：
 * <br/>1.加载报表设计文件JasperDesign
 * <br/>2.编译文件JasperReport
 * <br/>3.填充文件JasperPrint
 * <br/>4.报表导出器JRXXXExporter
 * <br/>5.回执
 * </DL>
 *
 * @author 段春明
 */
public class JasperEngine {
	
	/*---------------------------类常量定义---------------------------*/

	/**
	 * 强制重新编译
	 * <br/>获取报表时，始终重新编译
	 * <br/>默认为否（编译文件存在则直接使用，不存在则生成待用）
	 * <br/>只对{@linkplain org.smartframework.platform.jasper.engine.JasperEngine#getResponse getResponse}方法有效
	 */
	public boolean FORCE_RECOMPILE = false;

	/**
	 * 自动重新计算宽度
	 * <br/>横向扩展报表中，宽度溢出将导致报表横向滚动条消失等问题
	 * <br/>将此属性设置为true，将自动根据填充后报表实际宽度重新设置报表整体宽度
	 */
	public boolean AUTO_FIT_WIDTH = false;
	
	/*-------------------------------类方法定义----------------------------------*/
	
	/*----------------接口方法----------------*/

	/*----↓总接口-获取回执↓----*/

	/**
	 * 输出到servlet回执（JSON数据源）
	 *
	 * @param jasperDto 报表参数：由前台传入
	 * @param data      数据
	 * @throws BusinessException
	 */
	public void getResponse(JasperDto jasperDto, String data) throws BusinessException {
		// 编译-填充-导出器-回执
		JasperReport jasperReport = FORCE_RECOMPILE ?
				getJasperReport(getJasperDesign(jasperDto.getReportName()))
				: getJasperReportAndGenNone(jasperDto.getReportName());
		JasperPrint jasperPrint = getJasperPrint(jasperReport, jasperDto.getExportType(), data, jasperDto.getJasperFillParams());
		JRAbstractExporter exporter = getExporter(jasperPrint, jasperDto.getExportType(), null);
		getResponse(exporter, jasperDto.getExportType(), jasperDto.getExportName());
	}

	/**
	 * 输出到servlet回执（JavaBean数据源）
	 *
	 * @param jasperDto 报表参数：由前台传入
	 * @param data      数据
	 * @throws BusinessException
	 */
	public void getResponse(JasperDto jasperDto, Collection<?> data) throws BusinessException {
		// 编译-填充-导出器-回执
		JasperReport jasperReport = FORCE_RECOMPILE ?
				getJasperReport(getJasperDesign(jasperDto.getReportName()))
				: getJasperReportAndGenNone(jasperDto.getReportName());
		JasperPrint jasperPrint = getJasperPrint(jasperReport, jasperDto.getExportType(), data, jasperDto.getJasperFillParams());
		JRAbstractExporter exporter = getExporter(jasperPrint, jasperDto.getExportType(), null);
		getResponse(exporter, jasperDto.getExportType(), jasperDto.getExportName());
	}

	/**
	 * 输出到servlet回执（JDBC数据源）
	 *
	 * @param jasperDto 报表参数：由前台传入
	 * @throws BusinessException
	 */
	public void getResponse(JasperDto jasperDto) throws BusinessException {
		// 编译-填充-导出器-回执
		JasperReport jasperReport = FORCE_RECOMPILE ?
				getJasperReport(getJasperDesign(jasperDto.getReportName()))
				: getJasperReportAndGenNone(jasperDto.getReportName());
		JasperPrint jasperPrint = getJasperPrint(jasperReport, jasperDto.getExportType(), jasperDto.getJasperFillParams());
		JRAbstractExporter exporter = getExporter(jasperPrint, jasperDto.getExportType(), null);
		getResponse(exporter, jasperDto.getExportType(), jasperDto.getExportName());
	}

	/**
	 * 输出到servlet回执（JSON数据源）
	 *
	 * @param jasperDto        报表参数：由前台传入
	 * @param data             数据
	 * @param jasperFillParams 报表输出文件扩展参数：允许为空。构建JasperPrint时的参数，若有重复、将覆盖默认参数；详见JasperFillManager.fillReport方法API
	 * @param exporterParams   报表导出文件扩展参数：允许为空。构建JasperXXXExporter时的参数，若有重复、将覆盖默认参数；详见jasperXXXExporter.setParameter方法API
	 * @throws BusinessException
	 */
	public void getResponse(JasperDto jasperDto, String data, Map<String, Object> jasperFillParams, Map<JRExporterParameter, Object> exporterParams) throws BusinessException {
		// 组装扩展参数
		Map<String, Object> jasperFillParamsAll = new HashMap<String, Object>();
		if (jasperDto.getJasperFillParams() != null) {
			jasperFillParamsAll.putAll(jasperDto.getJasperFillParams());
		}
		if (jasperFillParams != null) {
			jasperFillParamsAll.putAll(jasperFillParams);
		}
		// 编译-填充-导出器-回执
		JasperReport jasperReport = FORCE_RECOMPILE ?
				getJasperReport(getJasperDesign(jasperDto.getReportName()))
				: getJasperReportAndGenNone(jasperDto.getReportName());
		JasperPrint jasperPrint = getJasperPrint(jasperReport, jasperDto.getExportType(), data, jasperFillParamsAll);
		JRAbstractExporter exporter = getExporter(jasperPrint, jasperDto.getExportType(), exporterParams);
		getResponse(exporter, jasperDto.getExportType(), jasperDto.getExportName());
	}

	/**
	 * 输出到servlet回执（JavaBean数据源）
	 *
	 * @param jasperDto        报表参数：由前台传入
	 * @param data             数据
	 * @param jasperFillParams 报表输出文件扩展参数：允许为空。构建JasperPrint时的参数，若有重复、将覆盖默认参数；详见JasperFillManager.fillReport方法API
	 * @param exporterParams   报表导出文件扩展参数：允许为空。构建JasperXXXExporter时的参数，若有重复、将覆盖默认参数；详见jasperXXXExporter.setParameter方法API
	 * @throws BusinessException
	 */
	public void getResponse(JasperDto jasperDto, Collection<?> data, Map<String, Object> jasperFillParams, Map<JRExporterParameter, Object> exporterParams) throws BusinessException {
		// 组装扩展参数
		Map<String, Object> jasperFillParamsAll = new HashMap<String, Object>();
		if (jasperDto.getJasperFillParams() != null) {
			jasperFillParamsAll.putAll(jasperDto.getJasperFillParams());
		}
		if (jasperFillParams != null) {
			jasperFillParamsAll.putAll(jasperFillParams);
		}
		// 编译-填充-导出器-回执
		JasperReport jasperReport = FORCE_RECOMPILE ?
				getJasperReport(getJasperDesign(jasperDto.getReportName()))
				: getJasperReportAndGenNone(jasperDto.getReportName());
		JasperPrint jasperPrint = getJasperPrint(jasperReport, jasperDto.getExportType(), data, jasperFillParamsAll);
		JRAbstractExporter exporter = getExporter(jasperPrint, jasperDto.getExportType(), exporterParams);
		getResponse(exporter, jasperDto.getExportType(), jasperDto.getExportName());
	}

	/**
	 * 输出到servlet回执（JDBC数据源）
	 *
	 * @param jasperDto        报表参数：由前台传入
	 * @param jasperFillParams 报表输出文件扩展参数：允许为空。构建JasperPrint时的参数，若有重复、将覆盖默认参数；详见JasperFillManager.fillReport方法API
	 * @param exporterParams   报表导出文件扩展参数：允许为空。构建JasperXXXExporter时的参数，若有重复、将覆盖默认参数；详见jasperXXXExporter.setParameter方法API
	 * @throws BusinessException
	 */
	public void getResponse(JasperDto jasperDto, Map<String, Object> jasperFillParams, Map<JRExporterParameter, Object> exporterParams) throws BusinessException {
		// 组装扩展参数
		Map<String, Object> jasperFillParamsAll = new HashMap<String, Object>();
		if (jasperDto.getJasperFillParams() != null) {
			jasperFillParamsAll.putAll(jasperDto.getJasperFillParams());
		}
		if (jasperFillParams != null) {
			jasperFillParamsAll.putAll(jasperFillParams);
		}
		// 编译-填充-导出器-回执
		JasperReport jasperReport = FORCE_RECOMPILE ?
				getJasperReport(getJasperDesign(jasperDto.getReportName()))
				: getJasperReportAndGenNone(jasperDto.getReportName());
		JasperPrint jasperPrint = getJasperPrint(jasperReport, jasperDto.getExportType(), jasperFillParamsAll);
		JRAbstractExporter exporter = getExporter(jasperPrint, jasperDto.getExportType(), exporterParams);
		getResponse(exporter, jasperDto.getExportType(), jasperDto.getExportName());
	}

	/*----↑总接口-获取回执↑----*/

	/*----↓1.设计文件.jrxml↓----*/

	/**
	 * 获取报表设计文件
	 *
	 * @param reportName 报表文件名称
	 * @throws BusinessException
	 */
	public JasperDesign getJasperDesign(String reportName) throws BusinessException {
		// 报表路径
		String jrxmlPath = JasperHelper.getJrxmlRootPath() + reportName + JasperConstaints.FILE_EXT_NAME_JRXML;
		// 报表设计文件
		JasperDesign jasperDesign;
		try {
			jasperDesign = JRXmlLoader.load(new File(jrxmlPath));
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【获取报表设计文件】出错！<br/>报表路径：【" + jrxmlPath + "】<br/>异常信息：<br/>" + e.getMessage());
		}
		return jasperDesign;
	}

	/*----↑1.设计文件.jrxml↑----*/

	/*----↓2.编译文件.jasper↓----*/

	/**
	 * 获取报表编译文件（通过报表设计文件）
	 *
	 * @throws BusinessException
	 */
	public JasperReport getJasperReport(JasperDesign jasperDesign) throws BusinessException {
		JasperReport jasperReport;
		try {
			// 编译文件
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【获取报表编译文件】方法出错：<br/>" + e.getMessage());
		}
		return jasperReport;
	}

	/**
	 * 获取报表编译文件（通过报表设计文件，并输出编译文件到当前目录）
	 *
	 * @throws BusinessException
	 */
	public JasperReport getJasperReportToFile(JasperDesign jasperDesign) throws BusinessException {
		JasperReport jasperReport;
		try {
			// 编译文件到当前目录
			String jasperPath = JasperHelper.replaceFileExtName(jasperDesign.getName(), JasperConstaints.FILE_EXT_NAME_JASPER);
			JasperCompileManager.compileReportToFile(jasperDesign, jasperPath);
			// 获取编译文件
			jasperReport = (JasperReport) JRLoader.loadObject(new File(jasperPath));
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【获取报表编译文件】方法出错：<br/>" + e.getMessage());
		}
		return jasperReport;
	}

	/**
	 * 获取报表编译文件（通过报表设计文件，并输出编译文件到指定目录）
	 *
	 * @throws BusinessException
	 */
	public JasperReport getJasperReportToFile(JasperDesign jasperDesign, String targetFilePath) throws BusinessException {
		JasperReport jasperReport;
		try {
			// 编译文件到指定目录
			JasperCompileManager.compileReportToFile(jasperDesign, targetFilePath);
			// 获取编译文件
			jasperReport = (JasperReport) JRLoader.loadObject(new File(targetFilePath));
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【获取报表编译文件】方法出错：<br/>" + e.getMessage());
		}
		return jasperReport;
	}

	/**
	 * 获取报表编译文件（通过编译文件路径）
	 * <br/>编译文件存在、直接获取；编译文件不存在、重新编译
	 *
	 * @throws BusinessException
	 */
	public JasperReport getJasperReport(String reportName) throws BusinessException {
		// 报表路径
		String jasperPath = JasperHelper.getJasperRootPath() + reportName + JasperConstaints.FILE_EXT_NAME_JASPER;
		JasperReport jasperReport;
		try {
			File file = new File(jasperPath);
			// 编译文件存在：直接获取
			if (file.exists()) {
				jasperReport = (JasperReport) JRLoader.loadObject(file);
			}
			// 编译文件不存在：重新编译
			else {
				jasperReport = getJasperReport(getJasperDesign(reportName));
			}
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【获取报表编译文件】方法出错：<br/>" + e.getMessage());
		}
		return jasperReport;
	}

	/**
	 * 获取报表编译文件（通过编译文件路径获取，若不存在则生成）
	 * <br/>编译文件存在、直接获取；编译文件不存在、重新编译
	 *
	 * @throws BusinessException
	 */
	public JasperReport getJasperReportAndGenNone(String reportName) throws BusinessException {
		// 报表路径
		String jasperPath = JasperHelper.getJasperRootPath() + reportName + JasperConstaints.FILE_EXT_NAME_JASPER;
		JasperReport jasperReport;
		try {
			File file = new File(jasperPath);
			// 编译文件存在：直接获取
			if (file.exists()) {
				jasperReport = (JasperReport) JRLoader.loadObject(file);
			}
			// 编译文件不存在：重新编译
			else {
				String jrxmlPath = JasperHelper.getJasperRootPath() + reportName + JasperConstaints.FILE_EXT_NAME_JRXML;
				// 编译报表到当前目录
				JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
				// 获取报表编译文件
				jasperReport = (JasperReport) JRLoader.loadObject(new File(jasperPath));
			}
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【获取报表编译文件】方法出错：<br/>" + e.getMessage());
		}
		return jasperReport;
	}

	/**
	 * 获取报表编译文件（通过编译文件路径，并输出编译文件到当前目录）
	 * <br/>将覆盖原文件
	 *
	 * @throws BusinessException
	 */
	public JasperReport getJasperReportToFile(String reportName) throws BusinessException {
		// 报表路径
		String jasperPath = JasperHelper.getJasperRootPath() + reportName + JasperConstaints.FILE_EXT_NAME_JASPER;
		String jrxmlPath = JasperHelper.getJrxmlRootPath() + reportName + JasperConstaints.FILE_EXT_NAME_JRXML;
		JasperReport jasperReport;
		try {
			// 编译报表到当前目录
			JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
			// 获取报表编译文件
			jasperReport = (JasperReport) JRLoader.loadObject(new File(jasperPath));
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【获取报表编译文件】方法出错：<br/>" + e.getMessage());
		}
		return jasperReport;
	}

	/**
	 * 获取报表编译文件（通过编译文件路径，并输出编译文件到指定目录）
	 * <br/>将覆盖原文件
	 *
	 * @throws BusinessException
	 */
	public JasperReport getJasperReportToFile(String reportName, String targetFilePath) throws BusinessException {
		// 报表路径
		String jrxmlPath = JasperHelper.getJrxmlRootPath() + reportName + JasperConstaints.FILE_EXT_NAME_JRXML;
		JasperReport jasperReport;
		try {
			// 编译报表到指定目录
			JasperCompileManager.compileReportToFile(jrxmlPath, targetFilePath);
			// 获取报表编译文件
			jasperReport = (JasperReport) JRLoader.loadObject(new File(targetFilePath));
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【获取报表编译文件】方法出错：<br/>" + e.getMessage());
		}
		return jasperReport;
	}

	/*----↑2.编译文件.jasper↑----*/

	/*----↓3.填充报表↓----*/

	/**
	 * 填充报表（JSON数据源）
	 *
	 * @param jasperReport     报表编译文件
	 * @param exportType       导出类型
	 * @param data             数据
	 * @param jasperFillParams 填充参数
	 * @throws BusinessException
	 */
	public JasperPrint getJasperPrint(JasperReport jasperReport, String exportType, String data, Map<String, Object> jasperFillParams) throws BusinessException {
		// 1.报表参数：默认参数+传入参数
		Map<String, Object> jasperFillParamsAll = getDefaultFillParams(exportType);
		if (jasperFillParams != null) {
			jasperFillParamsAll.putAll(jasperFillParams);
		}
		// 2.将数据转化为输入流填入参数
		JasperPrint jasperPrint = null;
		InputStream inputStream;
		try {
			inputStream = new ByteArrayInputStream(data.getBytes(JasperConstaints.CHARSET));
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("调用【Jasper报表】-【填充报表】方法时，设置输入流编码格式失败。异常信息：<br/>" + e.getMessage());
		}
		jasperFillParamsAll.put("JSON_INPUT_STREAM", inputStream);
		try {
			inputStream.close();
		} catch (IOException e) {
			throw new BusinessException("调用【Jasper报表】-【填充报表】方法时，关闭输入流失败。异常信息：<br/>" + e.getMessage());
		}
		// 3.填充报表
		try {
			jasperPrint = JasperFillManager.fillReport(jasperReport, jasperFillParamsAll);
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【填充报表】方法时，获取报表输出文件失败！<br/>报表路径：【" + jasperReport.getName() + "】<br/>异常信息：<br/>" + e.getMessage());
		}
		// 自动重计算宽度
		if (AUTO_FIT_WIDTH) {
			JasperHelper.fitWidth(jasperPrint);
		}
		return jasperPrint;
	}

	/**
	 * 填充报表（JavaBean数据源）
	 *
	 * @param jasperReport     报表编译文件
	 * @param exportType       导出类型
	 * @param data             数据
	 * @param jasperFillParams 填充参数
	 * @throws BusinessException
	 */
	public JasperPrint getJasperPrint(JasperReport jasperReport, String exportType, Collection<?> data, Map<String, Object> jasperFillParams) throws BusinessException {
		// 1.报表参数：默认参数+传入参数
		Map<String, Object> jasperFillParamsAll = getDefaultFillParams(exportType);
		if (jasperFillParams != null) {
			jasperFillParamsAll.putAll(jasperFillParams);
		}
		// 2.填充报表
		JasperPrint jasperPrint = null;
		try {
			JRBeanCollectionDataSource javaBeanDataSource = new JRBeanCollectionDataSource(data);
			jasperPrint = JasperFillManager.fillReport(jasperReport, jasperFillParamsAll, javaBeanDataSource);
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【填充报表】方法时，获取报表输出文件失败！<br/>报表路径：【" + jasperReport.getName() + "】<br/>异常信息：<br/>" + e.getMessage());
		}
		// 自动重计算宽度
		if (AUTO_FIT_WIDTH) {
			JasperHelper.fitWidth(jasperPrint);
		}
		return jasperPrint;
	}

	/**
	 * 填充报表（JDBC数据源）
	 *
	 * @param jasperReport     报表编译文件
	 * @param exportType       导出类型
	 * @param jasperFillParams 填充参数
	 * @throws BusinessException
	 */
	public JasperPrint getJasperPrint(JasperReport jasperReport, String exportType, Map<String, Object> jasperFillParams) throws BusinessException {
		// 1.报表参数：默认参数+传入参数
		Map<String, Object> jasperFillParamsAll = getDefaultFillParams(exportType);
		if (jasperFillParams != null) {
			jasperFillParamsAll.putAll(jasperFillParams);
		}
		// 2.填充报表
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(jasperReport, jasperFillParamsAll, JasperHelper.getConnection());
		} catch (JRException e) {
			throw new BusinessException("调用【Jasper报表】-【填充报表】方法时，获取报表输出文件失败！<br/>报表路径：【" + jasperReport.getName() + "】<br/>异常信息：<br/>" + e.getMessage());
		}
		// 自动重计算宽度
		if (AUTO_FIT_WIDTH) {
			JasperHelper.fitWidth(jasperPrint);
		}
		return jasperPrint;
	}

	/*----↑3.填充报表↑----*/

	/*----↓4.导出器↓----*/

	/**
	 * 获取报表导出器（单文件）
	 *
	 * @param jasperPrint    报表填充文件
	 * @param exportType     导出类型
	 * @param exporterParams 报表导出文件扩展参数：允许为空。构建JasperXXXExporter时的参数，若有重复、将覆盖默认参数；详见jasperXXXExporter.setParameter方法API
	 * @throws BusinessException
	 */
	public JRAbstractExporter getExporter(JasperPrint jasperPrint, String exportType, Map<JRExporterParameter, Object> exporterParams) throws BusinessException {
		// 创建导出器
		JRAbstractExporter exporter = newExporter(exportType);
		// 默认参数
		setDefaultParams(exporter, exportType);
		// 参数：输出文件
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		// 其他参数
		if (exporterParams != null) {
			exporter.setParameters(exporterParams);
		}
		return exporter;
	}

	/**
	 * 获取报表导出器（多文件）
	 *
	 * @param jasperPrints   报表填充文件
	 * @param exportType     导出文件类型
	 * @param exporterParams 报表导出文件扩展参数：允许为空。构建JasperXXXExporter时的参数，若有重复、将覆盖默认参数；详见jasperXXXExporter.setParameter方法API
	 * @throws BusinessException
	 */
	public JRAbstractExporter getExporter(List<JasperPrint> jasperPrints, String exportType, Map<JRExporterParameter, Object> exporterParams) throws BusinessException {
		// 创建导出器
		JRAbstractExporter exporter = newExporter(exportType);
		// 默认参数
		setDefaultParams(exporter, exportType);
		// 参数：输出文件
		exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
		// 其他参数
		if (exporterParams != null) {
			exporter.setParameters(exporterParams);
		}
		return exporter;
	}

	/*----↑4.导出器↑----*/

	/*----↓5.回执↓----*/

	/**
	 * 将导出器输出到Servelet回执
	 *
	 * @param exporter   Jasper导出器
	 * @param exportType 导出类型
	 * @param exportName 导出文件名：当导出类型为{@linkplain JasperConstaints#EXPORT_TYPE_VIEW EXPORT_TYPE_VIEW}或{@linkplain JasperConstaints#EXPORT_TYPE_PRINT EXPORT_TYPE_PRINT}时无用
	 * @throws BusinessException
	 */
	public void getResponse(JRAbstractExporter exporter, String exportType, String exportName) throws BusinessException {
		ServletOutputStream outputStream = null;
		ByteArrayOutputStream os = null;
		// 文件名默认
		exportName = exportName == null || "".equals(exportName) ? JasperConstaints.DEFAULT_FILE_NAME : exportName;
		try {
			// 导出文件
			os = new ByteArrayOutputStream();
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
			exporter.exportReport();
			byte[] outbytes = os.toByteArray();
			// 组装Servlet回执
			HttpServletResponse response = SmartContext.getResponse();
			if (JasperConstaints.EXPORT_TYPE_VIEW.equals(exportType)) {
				response.setContentType("application/pdf");
			} else if (JasperConstaints.EXPORT_TYPE_PDF.equals(exportType)) {
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(new String(exportName.getBytes(JasperConstaints.CHARSET), JasperConstaints.CHARSET) + JasperConstaints.FILE_EXT_NAME_PDF, JasperConstaints.CHARSET));
			} else if (JasperConstaints.EXPORT_TYPE_EXCEL.equals(exportType)) {
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(new String(exportName.getBytes(JasperConstaints.CHARSET), JasperConstaints.CHARSET) + JasperConstaints.FILE_EXT_NAME_EXCEL, JasperConstaints.CHARSET));
				response.addHeader("Cache-Control", "no-cache");
			} else if (JasperConstaints.EXPORT_TYPE_WORD.equals(exportType)) {
				response.setContentType("application/msword");
				response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(new String(exportName.getBytes(JasperConstaints.CHARSET), JasperConstaints.CHARSET) + JasperConstaints.FILE_EXT_NAME_WORD, JasperConstaints.CHARSET));
				response.addHeader("Cache-Control", "no-cache");
			} else if (JasperConstaints.EXPORT_TYPE_PRINT.equals(exportType)) {
				// --↓给pdf加上脚本实现自动打印↓--
				PdfReader reader = new PdfReader(outbytes);
				ByteArrayOutputStream bos = new ByteArrayOutputStream(outbytes.length);
				StringBuffer script = new StringBuffer();
				script.append("this.print({bUI:false,bSilent:true,bShrinkToFit:false});").append("\r\nthis.closeDoc();");
				PdfStamper stamp = new PdfStamper(reader, bos);
				stamp.setViewerPreferences(PdfWriter.HideMenubar | PdfWriter.HideToolbar | PdfWriter.HideWindowUI);
				stamp.addJavaScript(script.toString());
				stamp.close();
				// --↑给pdf加上脚本实现自动打印↑--
				outbytes = bos.toByteArray();
				response.setContentType("application/pdf");
			}
			if (outbytes != null && outbytes.length > 0) {
				response.setContentLength(outbytes.length);
				outputStream = response.getOutputStream();
				outputStream.write(outbytes, 0, outbytes.length);
			}
		} catch (Exception e) {
			throw new BusinessException("调用【报表】-【获取导出数据】方法时，发生错误：【" + e.getMessage() + "】");
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (Exception e) {
				throw new BusinessException("调用【报表】-【获取导出数据】方法时，关闭输出流发生错误：【" + e.getMessage() + "】");
			}
		}
	}

	/*----↑5.回执↑----*/

	/*----↓5.打印↓----*/

	/**
	 * 打印
	 *
	 * @throws BusinessException
	 */
	public void print(JasperPrint jasperPrint) throws BusinessException {
		try {
			JasperPrintManager.printReport(jasperPrint, true);
		} catch (JRException e) {
			throw new BusinessException("调用【报表】-【打印】方法时，出错：【" + e.getMessage() + "】");
		}
	}

	/*----↑5.打印↑----*/
	
	/*----------------私有方法----------------*/

	/**
	 * 获取默认填充参数
	 */
	private Map<String, Object> getDefaultFillParams(String exportType) {
		Map<String, Object> result = new HashMap<String, Object>();
		// case1:EXCEL
		if (JasperConstaints.EXPORT_TYPE_EXCEL.equals(exportType)) {
			// 无分页
			result.put(JRParameter.IS_IGNORE_PAGINATION, true);
		}
		return result;
	}

	/**
	 * 默认导出器参数
	 */
	private void setDefaultParams(JRAbstractExporter exporter, String exportType) {
		if (JasperConstaints.EXPORT_TYPE_VIEW.equals(exportType)) {
			// nothing
		} else if (JasperConstaints.EXPORT_TYPE_PDF.equals(exportType)) {
			// nothing
		} else if (JasperConstaints.EXPORT_TYPE_EXCEL.equals(exportType)) {
			// 移除空行
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
			// 移除空列
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
			// 一个表单一页：否
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, false);
			// 白色背景：否
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
			// 检测单元格类型：是（将解决数字类型被识别为文本的问题）
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
		} else if (JasperConstaints.EXPORT_TYPE_WORD.equals(exportType)) {
			// nothing
		} else if (JasperConstaints.EXPORT_TYPE_PRINT.equals(exportType)) {
			// nothing
		} else {
			// nothing
		}
	}

	/**
	 * 获取导出器
	 *
	 * @throws BusinessException
	 */
	private JRAbstractExporter newExporter(String exportType) throws BusinessException {
		// 依类型创建不同导出器
		JRAbstractExporter exporter = new JRPdfExporter();
		if (JasperConstaints.EXPORT_TYPE_VIEW.equals(exportType)) {
			exporter = new JRPdfExporter();
		} else if (JasperConstaints.EXPORT_TYPE_PDF.equals(exportType)) {
			exporter = new JRPdfExporter();
		} else if (JasperConstaints.EXPORT_TYPE_EXCEL.equals(exportType)) {
			exporter = new JRXlsExporter();
		} else if (JasperConstaints.EXPORT_TYPE_WORD.equals(exportType)) {
			exporter = new JRDocxExporter();
		} else if (JasperConstaints.EXPORT_TYPE_PRINT.equals(exportType)) {
			exporter = new JRPdfExporter();
		} else {
			throw new BusinessException("调用【JSON格式的jasper报表】方法时，传入参数中【导出文件类型】不识别：【" + exportType + "】");
		}
		return exporter;
	}

}
