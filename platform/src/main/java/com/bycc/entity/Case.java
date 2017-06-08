package com.bycc.entity;

import com.bycc.enumitem.CaseStatus;
import com.bycc.enumitem.CaseType;
import com.bycc.enumitem.RiskLevel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @description 案件信息
 * @author gaoningbo
 * @date 2017年4月11日
 * 
 */
@Entity
@Table(name = "caze")
@TableGenerator(name = "com.bycc.entity.Case",
		table = "ID_Sequence",
		pkColumnName = "KEY_ID_",
		valueColumnName = "GEN_VALUE_",
		pkColumnValue = "com.bycc.entity.Case",
		initialValue = 1,
		allocationSize = 1
)
public class Case implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.Case")
	private Integer id;

	/**
	 * 办案记录
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "caze")
	private CaseRecord caseRecord;
	
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
	 * 案件类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "case_type_")
	private CaseType caseType;
	
	/**
     * 风险等级
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level_")
    private RiskLevel riskLevel;
    
	/**
	 * 案件状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "case_status_")
	private CaseStatus caseStatus;

	/**
	 * 嫌疑人
	 */	
	@Column(name = "suspect_")
	private String suspect;

	/**
	 * 受理单位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accept_unit_id_")
	private BdmPoliceStation acceptUnit;

	/**
	 * 受理人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accept_police_id_")
	private BdmPolice acceptPolice;

	/**
	 * 主办单位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "master_unit_id_")
	private BdmPoliceStation masterUnit;

	/**
	 * 主办人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "master_police_id_")
	private BdmPolice masterPolice;

	/**
	 * 协办单位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "slave_unit_id_")
	private BdmPoliceStation slaveUnit;

	/**
	 * 协办人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "slave_police_id_")
	private BdmPolice slavePolice;

	/**
	 * 案发时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "occur_date_")
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
	 * 操作人
	 */
	@Column(name = "operator_id_")
	private Integer operatorId;

	/**
	 * 插入日期
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "insert_date_")
	private Date insertDate;

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date_")
	private Date updateDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CaseRecord getCaseRecord() {
		return caseRecord;
	}

	public void setCaseRecord(CaseRecord caseRecord) {
		this.caseRecord = caseRecord;
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

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public RiskLevel getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(RiskLevel riskLevel) {
		this.riskLevel = riskLevel;
	}

	public CaseStatus getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(CaseStatus caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getSuspect() {
		return suspect;
	}

	public void setSuspect(String suspect) {
		this.suspect = suspect;
	}

	public BdmPoliceStation getAcceptUnit() {
		return acceptUnit;
	}

	public void setAcceptUnit(BdmPoliceStation acceptUnit) {
		this.acceptUnit = acceptUnit;
	}

	public BdmPolice getAcceptPolice() {
		return acceptPolice;
	}

	public void setAcceptPolice(BdmPolice acceptPolice) {
		this.acceptPolice = acceptPolice;
	}

	public BdmPoliceStation getMasterUnit() {
		return masterUnit;
	}

	public void setMasterUnit(BdmPoliceStation masterUnit) {
		this.masterUnit = masterUnit;
	}

	public BdmPolice getMasterPolice() {
		return masterPolice;
	}

	public void setMasterPolice(BdmPolice masterPolice) {
		this.masterPolice = masterPolice;
	}

	public BdmPoliceStation getSlaveUnit() {
		return slaveUnit;
	}

	public void setSlaveUnit(BdmPoliceStation slaveUnit) {
		this.slaveUnit = slaveUnit;
	}

	public BdmPolice getSlavePolice() {
		return slavePolice;
	}

	public void setSlavePolice(BdmPolice slavePolice) {
		this.slavePolice = slavePolice;
	}

	public Date getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
