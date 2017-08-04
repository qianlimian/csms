package com.bycc.entity;

import com.bycc.enumitem.CertificateType;
import com.bycc.enumitem.Gender;
import com.bycc.enumitem.PeopleType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gaoningbo
 * @description 通知人（涉案人）
 * @date 2017年4月10日
 */
@Entity
@Table(name = "case_people")
@TableGenerator(name = "com.bycc.entity.CasePeople",
		table = "ID_Sequence",
		pkColumnName = "KEY_ID_",
		valueColumnName = "GEN_VALUE_",
		pkColumnValue = "com.bycc.entity.CasePeople",
		initialValue = 1,
		allocationSize = 1
)
public class CasePeople implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.CasePeople")
	private Integer id;

	/**
	 * 名称
	 */
	@Column(name = "name_")
	private String name;

	/**
	 * 出生年月
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday_")
	private Date birthday;

	/**
	 * 性别
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "gender_")
	private Gender gender;

	/**
	 * 联系方式
	 */
	@Column(name = "tel_num_")
	private String telNum;

	/**
	 * 证件号
	 */
	@Column(name = "certificate_num_")
	private String certificateNum;

	/**
	 * 证件类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "certificate_type_")
	private CertificateType certificateType;

	/**
	 * 家庭住址
	 */
	@Column(name = "address_")
	private String address;


	/**
	 * 涉案人员类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "people_type_")
	private PeopleType peopleType;

	/**
	 * 办案记录
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "case_record_id_")
	private CaseRecord caseRecord;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getCertificateNum() {
		return certificateNum;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}

	public CertificateType getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(CertificateType certificateType) {
		this.certificateType = certificateType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PeopleType getPeopleType() {
		return peopleType;
	}

	public void setPeopleType(PeopleType peopleType) {
		this.peopleType = peopleType;
	}

	public CaseRecord getCaseRecord() {
		return caseRecord;
	}

	public void setCaseRecord(CaseRecord caseRecord) {
		this.caseRecord = caseRecord;
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
