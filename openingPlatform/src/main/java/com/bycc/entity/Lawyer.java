package com.bycc.entity;

import com.bycc.enumitem.CertificateStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gaoningbo
 * @description 律师
 * @date 2017年7月5日
 */
@Entity
@Table(name = "lawyer")
@TableGenerator(name = "com.bycc.entity.Lawyer",
        table = "ID_Sequence",
        pkColumnName = "KEY_ID_",
        valueColumnName = "GEN_VALUE_",
        pkColumnValue = "com.bycc.entity.Lawyer",
        initialValue = 1,
        allocationSize = 1)
public class Lawyer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.Lawyer")
    private Integer id;

    /**
     * 姓名
     */
    @Column(name = "name_")
    private String name;

    /**
     * 领域
     */
    @Column(name = "domain_")
    private String domain;

    /**
     * 证件号
     */
    @Column(name = "registrationNum_")
    private String registrationNum;

    /**
     * 事务所
     */
    @Column(name = "lawyerOffice_")
    private String lawyerOffice;

    /**
     * 联系电话
     */
    @Column(name = "phone_")
    private String phone;

    /**
     * 电子邮件
     */
    @Column(name = "email_")
    private String email;

    /**
     * 简介
     */
    @Column(name = "info_")
    private String info;

    /**
     * 注册时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registerDate_")
    private Date registerDate;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private CertificateStatus status;

    /**
     * 新增时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insertDate_")
    private Date insertDate;
    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateDate_")
    private Date updateDate;

    /**
     * 操作人
     */
    @Column(name = "operatorId_")
    private Integer operatorId;

    /**
     * 案件
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "lawyers", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CaseRecord> caseRecords = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
    }

    public String getLawyerOffice() {
        return lawyerOffice;
    }

    public void setLawyerOffice(String lawyerOffice) {
        this.lawyerOffice = lawyerOffice;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public CertificateStatus getStatus() {
        return status;
    }

    public void setStatus(CertificateStatus status) {
        this.status = status;
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

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getPhone() {return phone;}

    public String getInfo() {return info;}

    public void setPhone(String phone) {this.phone = phone;}

    public void setInfo(String info) {this.info = info;}

    public List<CaseRecord> getCaseRecords() {
        return caseRecords;
    }

    public void setCaseRecords(List<CaseRecord> caseRecords) {
        this.caseRecords = caseRecords;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
