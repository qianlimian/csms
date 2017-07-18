package com.bycc.dto.caseWarning;

/**
 * 
 * @description 受立案监督DTO
 * @author liuxunhua
 * @date 2017年7月13日 上午8:04:35
 *
 */
public class CaseWarningDto {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 告警信息
	 */
	private String note;
	/**
	 * 案件编号
	 */
	private String caseCode;
	/**
	 * 案件名称
	 */
	private String caseName;
	/**
	 * 案件类型
	 */
	private String caseType;
	/**
	 * 主办单位名称
	 */
	private String policeStationName;
	/**
	 * 主办人名称
	 */
	private String policeName;
	/**
	 * 案件状态
	 */
	private String caseStatus;
	
	/**
	 * 更新时间
	 */
	private String updateTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getPoliceStationName() {
		return policeStationName;
	}
	public void setPoliceStationName(String policeStationName) {
		this.policeStationName = policeStationName;
	}
	public String getPoliceName() {
		return policeName;
	}
	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}
	public String getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
