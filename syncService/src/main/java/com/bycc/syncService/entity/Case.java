package com.bycc.syncService.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gaoningbo
 * @description 案件信息
 * @date 2017年4月11日
 */
@Entity
@Table(name = "case_table")
@TableGenerator(name = "com.bycc.syncService.entity.Case",
table = "ID_Sequence",
pkColumnName = "KEY_ID_",
valueColumnName = "GEN_VALUE_",
pkColumnValue = "com.bycc.syncService.entity.Case",
initialValue = 1,
allocationSize = 1)
public class Case implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.syncService.entity.Case")
	private Integer id;

	/**
	 * 警情编号
	 */
	@Column(name = "alarm_code_")
	private String alarmCode;

	/**
	 * 案件编号
	 */
	@Column(name = "case_code_")
	private String caseCode;

	/**
	 * 案件名称
	 */
	@Column(name = "case_name_")
	private String caseName;

	/**
	 * 简要案情
	 */
	@Column(name = "case_summary_")
	private String caseSummary;

	/**
	 * 嫌疑人
	 */
	@Column(name = "suspect_")
	private String suspect;

	/**
	 * 受理人
	 */
	@Column(name = "accept_police_id_")
	private Integer acceptPoliceId;

	/**
	 * 主办人
	 */
	@Column(name="master_police_id_")
	private Integer masterPoliceId;

	/**
	 * 协办人
	 */
	@Column(name = "slave_police_id_")
	private Integer slavePoliceId;

	/**
	 * 案发时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="occur_date_")
	private Date occurDate;

	/**
	 * 受案时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "accept_date_")
	private Date acceptDate;

	/**
	 * 立案时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "register_date_")
	private Date registerDate;

	/**
	 * 结案时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "close_date_")
	private Date closeDate;

	/**
	 * 备注
	 */
	@Column(name = "note_")
	private String note;

	/**
	 * 插入日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_date_")
	private Date insertDate;

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date_")
	private Date updateDate;

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public Integer getAcceptPoliceId() {
		return acceptPoliceId;
	}

	public void setAcceptPoliceId(Integer acceptPoliceId) {
		this.acceptPoliceId = acceptPoliceId;
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

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Integer getMasterPoliceId() {
		return masterPoliceId;
	}

	public void setMasterPoliceId(Integer masterPoliceId) {
		this.masterPoliceId = masterPoliceId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getSlavePoliceId() {
		return slavePoliceId;
	}

	public void setSlavePoliceId(Integer slavePoliceId) {
		this.slavePoliceId = slavePoliceId;
	}

	public String getSuspect() {
		return suspect;
	}

	public void setSuspect(String suspect) {
		this.suspect = suspect;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Case{" +
				"id=" + id +
				", caseName='" + caseName + '\'' +
				", caseCode='" + caseCode + '\'' +
				", alarmCode='" + alarmCode + '\'' +
				", caseSummary='" + caseSummary + '\'' +
				", insertDate=" + insertDate +
				", acceptDate=" + acceptDate +
				", note='" + note + '\'' +
				'}';
	}
}
