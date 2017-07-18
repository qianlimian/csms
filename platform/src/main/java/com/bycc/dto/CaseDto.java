/**
 * 
 */
package com.bycc.dto;

import org.smartframework.utils.helper.DateHelper;
import com.bycc.entity.Case;

/**
 * @description
 * @author gaoningbo
 * @date 2017年4月18日
 * 
 */
public class CaseDto {

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 办案记录
	 */
	private Integer caseRecordId;

	/**
	 * 警情编号
	 */
	private String alarmCode;

	/**
	 * 案件编号
	 */
	private String caseCode;

	/**
	 * 案件名称
	 */
	private String caseName;
	/**
	 * 简要案情
	 */
	private String caseSummary;

	/**
	 * 案件类型
	 */
	private String caseType;

	/**
	 * 风险等级
	 */
	private String riskLevel;

	/**
	 * 案件状态
	 */
	private String caseStatus;
    private String caseStatusName;

	/**
	 * 嫌疑人
	 */
	private String suspect;

	/**
	 * 受理单位
	 */
	private Integer acceptUnitId;
	private String acceptUnit;

	/**
	 * 受理人
	 */
	private Integer acceptPoliceId;
    private String acceptPoliceName;
	/**
	 * 主办单位
	 */
	private Integer masterUnitId;
	private String masterUnit;
	/**
	 * 主办人
	 */
	private Integer masterPoliceId;
	private String masterPoliceName;
	/**
	 * 协办单位
	 */
	private Integer slaveUnitId;
	private String slaveUnit;

	/**
	 * 协办人
	 */
	private Integer slavePoliceId;
	private String slavePoliceName;

	/**
	 * 案发时间
	 */
	private String occurDate;

	/**
	 * 受案时间
	 */
	private String acceptDate;

	/**
	 * 立案时间
	 */
	private String registerDate;
	
	/**
	 * 结案时间
	 */
	private String closeDate;
	
	/**
	 * 同步时间
	 */
	private String insertDate;
	
	/**
	 * 更新时间
	 */
	private String updateDate;
	
	/**
	 * 备注
	 */
	private String note;

	public static CaseDto toDto(Case c) {
		CaseDto dto = new CaseDto();
		dto.setId(c.getId());
		dto.setCaseRecordId(c.getCaseRecord() != null ? c.getCaseRecord().getId() : null);
		dto.setAlarmCode(c.getAlarmCode());
		dto.setCaseCode(c.getCaseCode());
		dto.setCaseName(c.getCaseName());
		dto.setCaseSummary(c.getCaseSummary());
		dto.setCaseType(c.getCaseType() != null ? c.getCaseType().value() : "");
		dto.setRiskLevel(c.getRiskLevel() != null ? c.getRiskLevel().value() : "");
		dto.setCaseStatus(c.getCaseStatus() != null ? c.getCaseStatus().key() : null);
        dto.setCaseStatusName(c.getCaseStatus() != null ? c.getCaseStatus().value() : "");
		dto.setSuspect(c.getSuspect());
        if (c.getAcceptUnit() != null) {
            dto.setAcceptUnitId(c.getAcceptUnit().getId());
            dto.setAcceptUnit(c.getAcceptUnit().getName());
        }
        if (c.getMasterUnit() != null) {
            dto.setMasterUnitId(c.getMasterUnit().getId());
            dto.setMasterUnit(c.getMasterUnit().getName());
        }
        if (c.getSlaveUnit() != null) {
            dto.setSlaveUnitId(c.getSlaveUnit().getId());
            dto.setSlaveUnit(c.getSlaveUnit().getName());
        }
        if (c.getSlavePolice() != null) {
            dto.setSlavePoliceId(c.getSlavePolice().getId());
            dto.setSlavePoliceName(c.getSlavePolice().getUser() != null ? c.getSlavePolice().getUser().getName() : null);
        }
        if (c.getMasterPolice() != null) {
            dto.setMasterPoliceId(c.getMasterPolice() != null ? c.getMasterPolice().getId() : null);
            dto.setMasterPoliceName(c.getMasterPolice() != null ? c.getMasterPolice().getUser().getName() : null);
        }
        if (c.getAcceptPolice() != null) {
            dto.setAcceptPoliceId(c.getAcceptPolice() != null ? c.getAcceptPolice().getId() : null);
            dto.setAcceptPoliceName(c.getAcceptPolice() != null ? c.getAcceptPolice().getUser().getName() : null);
        }
		dto.setOccurDate(c.getOccurDate() != null ? DateHelper.formatDateToString(c.getOccurDate(), "yyyy-MM-dd") : null);
		dto.setAcceptDate(c.getAcceptDate() != null ? DateHelper.formatDateToString(c.getAcceptDate(), "yyyy-MM-dd") : null);
		dto.setRegisterDate(c.getRegisterDate() != null ? DateHelper.formatDateToString(c.getRegisterDate(), "yyyy-MM-dd") : null);
		dto.setCloseDate(c.getCloseDate() != null ? DateHelper.formatDateToString(c.getCloseDate(), "yyyy-MM-dd") : null);
		dto.setInsertDate(c.getInsertDate() != null ? DateHelper.formatDateToString(c.getInsertDate(), "yyyy-MM-dd") : null);
		dto.setUpdateDate(c.getUpdateDate() != null ? DateHelper.formatDateToString(c.getUpdateDate(), "yyyy-MM-dd") : null);
		return dto;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseRecordId() {
        return caseRecordId;
    }

    public void setCaseRecordId(Integer caseRecordId) {
        this.caseRecordId = caseRecordId;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
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

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseStatusName() {
        return caseStatusName;
    }

    public void setCaseStatusName(String caseStatusName) {
        this.caseStatusName = caseStatusName;
    }

    public String getSuspect() {
        return suspect;
    }

    public void setSuspect(String suspect) {
        this.suspect = suspect;
    }

    public Integer getAcceptUnitId() {
        return acceptUnitId;
    }

    public void setAcceptUnitId(Integer acceptUnitId) {
        this.acceptUnitId = acceptUnitId;
    }

    public String getAcceptUnit() {
        return acceptUnit;
    }

    public void setAcceptUnit(String acceptUnit) {
        this.acceptUnit = acceptUnit;
    }

    public Integer getAcceptPoliceId() {
        return acceptPoliceId;
    }

    public void setAcceptPoliceId(Integer acceptPoliceId) {
        this.acceptPoliceId = acceptPoliceId;
    }

    public String getAcceptPoliceName() {
        return acceptPoliceName;
    }

    public void setAcceptPoliceName(String acceptPoliceName) {
        this.acceptPoliceName = acceptPoliceName;
    }

    public Integer getMasterUnitId() {
        return masterUnitId;
    }

    public void setMasterUnitId(Integer masterUnitId) {
        this.masterUnitId = masterUnitId;
    }

    public String getMasterUnit() {
        return masterUnit;
    }

    public void setMasterUnit(String masterUnit) {
        this.masterUnit = masterUnit;
    }

    public Integer getMasterPoliceId() {
        return masterPoliceId;
    }

    public void setMasterPoliceId(Integer masterPoliceId) {
        this.masterPoliceId = masterPoliceId;
    }

    public String getMasterPoliceName() {
        return masterPoliceName;
    }

    public void setMasterPoliceName(String masterPoliceName) {
        this.masterPoliceName = masterPoliceName;
    }

    public Integer getSlaveUnitId() {
        return slaveUnitId;
    }

    public void setSlaveUnitId(Integer slaveUnitId) {
        this.slaveUnitId = slaveUnitId;
    }

    public String getSlaveUnit() {
        return slaveUnit;
    }

    public void setSlaveUnit(String slaveUnit) {
        this.slaveUnit = slaveUnit;
    }

    public Integer getSlavePoliceId() {
        return slavePoliceId;
    }

    public void setSlavePoliceId(Integer slavePoliceId) {
        this.slavePoliceId = slavePoliceId;
    }

    public String getSlavePoliceName() {
        return slavePoliceName;
    }

    public void setSlavePoliceName(String slavePoliceName) {
        this.slavePoliceName = slavePoliceName;
    }

    public String getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(String occurDate) {
        this.occurDate = occurDate;
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

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
