package com.bycc.entity;

import com.bycc.enumitem.CaseHandle;
import com.bycc.enumitem.CaseStatus;
import com.bycc.enumitem.CaseType;
import com.bycc.enumitem.OpenType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gaoningbo
 * @description 案件
 * @date 2017年4月10日
 */
@Entity
@Table(name = "case_record")
@TableGenerator(name = "com.bycc.entity.CaseRecord",
        table = "ID_Sequence",
        pkColumnName = "KEY_ID_",
        valueColumnName = "GEN_VALUE_",
        pkColumnValue = "com.bycc.entity.CaseRecord",
        initialValue = 1,
        allocationSize = 1)
public class CaseRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.CaseRecord")
    private Integer id;

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
     * 办理状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "case_handle_")
    private CaseHandle caseHandle;

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
     * 案件是否开放
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "open_")
    private OpenType open;
    /**
     * 法律法规
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "case_record_law", joinColumns = @JoinColumn(name = "case_record_id_"), inverseJoinColumns = @JoinColumn(name = "law_id_"))
    private List<Law> laws = new ArrayList<>();

    /**
     * 律师
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "case_record_lawyer", joinColumns = @JoinColumn(name = "case_record_id_"), inverseJoinColumns = @JoinColumn(name = "lawyer_id_"))
    private List<Lawyer> lawyers = new ArrayList<>();

    /**
     * 主办单位
     */
    @Column(name = "master_unit_name_")
    private String masterUnitName;

    /**
     * 主办人
     */
    @Column(name = "master_police_name_")
    private String masterPoliceName;

    /**
     * 协办单位
     */
    @Column(name = "slave_unit_name_")
    private String slaveUnitName;

    /**
     * 协办人
     */
    @Column(name = "slave_police_name_")
    private String slavePoliceName;

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
     * 办案时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date_")
    private Date startDate;

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

    public CaseHandle getCaseHandle() {
        return caseHandle;
    }

    public void setCaseHandle(CaseHandle caseHandle) {
        this.caseHandle = caseHandle;
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

    public String getMasterUnitName() {
        return masterUnitName;
    }

    public void setMasterUnitName(String masterUnitName) {
        this.masterUnitName = masterUnitName;
    }

    public String getMasterPoliceName() {
        return masterPoliceName;
    }

    public void setMasterPoliceName(String masterPoliceName) {
        this.masterPoliceName = masterPoliceName;
    }

    public String getSlaveUnitName() {
        return slaveUnitName;
    }

    public void setSlaveUnitName(String slaveUnitName) {
        this.slaveUnitName = slaveUnitName;
    }

    public String getSlavePoliceName() {
        return slavePoliceName;
    }

    public void setSlavePoliceName(String slavePoliceName) {
        this.slavePoliceName = slavePoliceName;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

	public OpenType getOpen() {
		return open;
	}

	public void setOpen(OpenType open) {
		this.open = open;
	}
    
    
}
