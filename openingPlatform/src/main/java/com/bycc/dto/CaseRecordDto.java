/**
 *
 */
package com.bycc.dto;


import com.bycc.common.ExcelColumn;
import com.bycc.entity.CaseRecord;
import com.bycc.entity.Law;
import com.bycc.entity.Lawyer;
import com.bycc.enumitem.CaseHandle;
import com.bycc.enumitem.CaseStatus;
import com.bycc.enumitem.CaseType;
import org.smartframework.utils.helper.DateHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author gaoningbo
 * @description
 * @date 2017年4月14日
 */
public class CaseRecordDto {

	@ExcelColumn(allowBlank = true, title = "ID")
	private Integer id;
	// 案件编号
	@ExcelColumn(allowBlank = true, title = "案件编号")
	private String caseCode;
	// 案件名称
	@ExcelColumn(allowBlank = true, title = "案件名称")
	private String caseName;
	// 简要案情
	@ExcelColumn(allowBlank = true, title = "简要案情")
	private String caseSummary;
	// 案件类型
	@ExcelColumn(allowBlank = true, title = "案件类型")
	private String caseType;
	// 办理状态
	@ExcelColumn(allowBlank = true, title = "办理状态")
	private String caseHandle;
	//案件状态
	@ExcelColumn(allowBlank = true, title = "案件状态")
	private String caseStatus;
	// 嫌疑人
	@ExcelColumn(allowBlank = true, title = "嫌疑人")
	private String suspect;
	// 主办单位
	@ExcelColumn(allowBlank = true, title = "主办单位")
	private String masterUnit;// 显示值
	// 主办人
	@ExcelColumn(allowBlank = true, title = "主办人")
	private String masterPoliceName;// 显示值
	// 协办单位
	@ExcelColumn(allowBlank = true, title = "协办单位")
	private String slaveUnit;// 显示值
	// 协办人
	@ExcelColumn(allowBlank = true, title = "协办人")
	private String slavePoliceName;// 显示值	
	// 接案时间
	@ExcelColumn(allowBlank = true, title = "接案时间", required = false)
	private String acceptDate;
	// 立案时间
	@ExcelColumn(allowBlank = true, title = "立案时间")
	private String registerDate;
	// 结案时间
	@ExcelColumn(allowBlank = true, title = "结案时间")
	private String closeDate;
	// 办案时间
	@ExcelColumn(allowBlank = true, title = "办案时间")
	private String startDate;
	// 备注
	@ExcelColumn(allowBlank = true, title = "备注")
	private String note;
	//是否公开
	private String open = "NO";
	//法律
	private List<Law> laws = new ArrayList<>();
	//律师
	private List<Lawyer> lawyers = new ArrayList<>();
	// 是否聘请律师
	private boolean hasLawyers;

	public static CaseRecordDto toDto(CaseRecord cr) {
		CaseRecordDto dto = new CaseRecordDto();
		dto.setId(cr.getId());
		dto.setCaseName(cr.getCaseName());
		dto.setCaseCode(cr.getCaseCode());
		dto.setCaseSummary(cr.getCaseSummary());
		dto.setCaseType(cr.getCaseType() != null ? cr.getCaseType().key() : null);
		dto.setCaseHandle(cr.getCaseHandle() != null ? cr.getCaseHandle().key() : null);
		dto.setSuspect(cr.getSuspect());
		dto.setCaseStatus(cr.getCaseStatus() != null ? cr.getCaseStatus().key() : null);
		dto.setAcceptDate(DateHelper.formatDateToString(cr.getAcceptDate(), "yyyy-MM-dd"));
		dto.setRegisterDate(DateHelper.formatDateToString(cr.getRegisterDate(), "yyyy-MM-dd"));
		dto.setStartDate(DateHelper.formatDateToString(cr.getStartDate(), "yyyy-MM-dd"));
		dto.setCloseDate(DateHelper.formatDateToString(cr.getCloseDate(), "yyyy-MM-dd"));

		dto.setMasterUnit(cr.getMasterUnitName());
		dto.setMasterPoliceName(cr.getMasterPoliceName());
		dto.setSlaveUnit(cr.getSlaveUnitName());
		dto.setSlavePoliceName(cr.getSlavePoliceName());

		dto.setOpen(cr.getOpen() != null ? cr.getOpen().key() : null);
		if (!cr.getLawyers().isEmpty()) {
			dto.setHasLawyers(true);
		}
		dto.setLaws(cr.getLaws());

		return dto;
	}

	/**
	 * 转换为前台查询使用的DTO
	 *
	 * @param cr 案件记录
	 */
	public static CaseRecordDto toDto4Index(CaseRecord cr) {
		CaseRecordDto dto = new CaseRecordDto();

		dto.setId(cr.getId());
		dto.setCaseName(cr.getCaseName());
		dto.setCaseCode(cr.getCaseCode());
		dto.setCaseSummary(cr.getCaseSummary());
		dto.setCaseType(cr.getCaseType() != null ? cr.getCaseType().value() : null);
		dto.setCaseHandle(cr.getCaseHandle() != null ? cr.getCaseHandle().value() : null);
		dto.setAcceptDate(DateHelper.formatDateToString(cr.getAcceptDate(), "yyyy-MM-dd"));
		dto.setRegisterDate(DateHelper.formatDateToString(cr.getRegisterDate(), "yyyy-MM-dd"));
		dto.setStartDate(DateHelper.formatDateToString(cr.getStartDate(), "yyyy-MM-dd"));
		dto.setMasterUnit(cr.getMasterUnitName());
		dto.setSlaveUnit(cr.getSlaveUnitName());
		dto.setMasterPoliceName(cr.getMasterPoliceName());
		if (!cr.getLawyers().isEmpty()) {
			dto.setHasLawyers(true);
		}
		dto.setLaws(cr.getLaws());
		return dto;
	}

	public CaseRecord toEntity() {
		CaseRecord entity = new CaseRecord();
		entity.setId(this.id);
		entity.setCaseCode(this.caseCode);
		entity.setCaseName(this.caseName);
		entity.setCaseSummary(this.caseSummary);
		entity.setCaseType(CaseType.getMatchByKey(this.caseType));
		entity.setCaseHandle(CaseHandle.getMatchByKey(this.caseHandle));
		entity.setCaseStatus(CaseStatus.getMatchByKey(this.caseStatus));
		entity.setSuspect(this.suspect);

		entity.setMasterPoliceName(this.masterPoliceName);
		entity.setMasterUnitName(this.masterUnit);
		entity.setSlavePoliceName(this.slavePoliceName);
		entity.setSlaveUnitName(this.slaveUnit);
		entity.setNote(this.note);
		try {
			entity.setAcceptDate(DateHelper.formatStringToDate(this.acceptDate, "yyyy-MM-dd"));
			entity.setRegisterDate(DateHelper.formatStringToDate(this.registerDate, "yyyy-MM-dd"));
			entity.setStartDate(DateHelper.formatStringToDate(this.startDate, "yyyy-MM-dd"));
			entity.setCloseDate(DateHelper.formatStringToDate(this.closeDate, "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return entity;
	}

	public boolean isHasLawyers() {
		return hasLawyers;
	}

	public void setHasLawyers(boolean hasLawyers) {
		this.hasLawyers = hasLawyers;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseSummary() {
		return caseSummary;
	}

	public void setCaseSummary(String caseSummary) {
		this.caseSummary = caseSummary;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseHandle() {
		return caseHandle;
	}

	public void setCaseHandle(String caseHandle) {
		this.caseHandle = caseHandle;
	}

	public String getSuspect() {
		return suspect;
	}

	public void setSuspect(String suspect) {
		this.suspect = suspect;
	}

	public String getMasterUnit() {
		return masterUnit;
	}

	public void setMasterUnit(String masterUnit) {
		this.masterUnit = masterUnit;
	}


	public String getMasterPoliceName() {
		return masterPoliceName;
	}

	public void setMasterPoliceName(String masterPoliceName) {
		this.masterPoliceName = masterPoliceName;
	}


	public String getSlaveUnit() {
		return slaveUnit;
	}

	public void setSlaveUnit(String slaveUnit) {
		this.slaveUnit = slaveUnit;
	}

	public String getSlavePoliceName() {
		return slavePoliceName;
	}

	public void setSlavePoliceName(String slavePoliceName) {
		this.slavePoliceName = slavePoliceName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public List<Law> getLaws() {
		return laws;
	}

	public void setLaws(List<Law> laws) {
		this.laws = laws;
	}

	public List<Lawyer> getLawyers() {
		return lawyers;
	}

	public void setLawyers(List<Lawyer> lawyers) {
		this.lawyers = lawyers;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

}
