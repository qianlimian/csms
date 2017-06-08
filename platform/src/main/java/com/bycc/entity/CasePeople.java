package com.bycc.entity;

import com.bycc.enumitem.CertificateType;
import com.bycc.enumitem.EnterReason;
import com.bycc.enumitem.Gender;
import com.bycc.enumitem.PeopleType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gaoningbo
 * @description 涉案人员
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
	 * 涉案人员名称
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
	 * 手环
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "strap_id_")
	private BdmStrap strap;

	/**
	 * 涉案人员类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "people_type_")
	private PeopleType peopleType;

	/**
	 * 进入办案区的事由
	 */
	@Column(name = "enter_reason_")
	@Enumerated(EnumType.STRING)
	private EnterReason enterReason;

	/**
	 * 进入办案区的其他事由
	 */
	@Column(name = "other_enter_reason_")
	private String otherEnterReason;

	/**
	 * 进入办案区时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "enter_date_")
	private Date enterDate;

	/**
	 * 离开原因
	 */
	@Column(name = "leave_reason_")
	private String leaveReason;

	/**
	 * 离开时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "leave_date_")
	private Date leaveDate;

	/**
	 * 办案记录
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "case_record_id_")
	private CaseRecord caseRecord;

	/**
	 * （涉案人员）人身检查
	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "casePeople", fetch = FetchType.LAZY)
	private CasePeopleInspect inpect;

	/**
	 * （涉案人员）随身物品
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "casePeople", fetch = FetchType.LAZY)
	private List<CasePeopleBelongs> casePeopleBelongs = new ArrayList<CasePeopleBelongs>();

	/**
	 * 随身物品是否全部返还(全部返还：true，部分返还：false)
	 */
	@Column(name = "all_belongs_return_")
	private Boolean allBelongsReturn;

	/**
	 * 备注(未返还物品情况记载)
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

	public BdmStrap getStrap() {
		return strap;
	}

	public void setStrap(BdmStrap strap) {
		this.strap = strap;
	}

	public PeopleType getPeopleType() {
		return peopleType;
	}

	public void setPeopleType(PeopleType peopleType) {
		this.peopleType = peopleType;
	}

	public EnterReason getEnterReason() {
		return enterReason;
	}

	public void setEnterReason(EnterReason enterReason) {
		this.enterReason = enterReason;
	}

	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public CaseRecord getCaseRecord() {
		return caseRecord;
	}

	public void setCaseRecord(CaseRecord caseRecord) {
		this.caseRecord = caseRecord;
	}

	public CasePeopleInspect getInpect() {
		return inpect;
	}

	public void setInpect(CasePeopleInspect inpect) {
		this.inpect = inpect;
	}

	public List<CasePeopleBelongs> getCasePeopleBelongs() {
		return casePeopleBelongs;
	}

	public void setCasePeopleBelongs(List<CasePeopleBelongs> casePeopleBelongs) {
		this.casePeopleBelongs = casePeopleBelongs;
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

	public String getOtherEnterReason() {
		return otherEnterReason;
	}

	public void setOtherEnterReason(String otherEnterReason) {
		this.otherEnterReason = otherEnterReason;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Boolean getAllBelongsReturn() {
		return allBelongsReturn;
	}

	public void setAllBelongsReturn(Boolean allBelongsReturn) {
		this.allBelongsReturn = allBelongsReturn;
	}
}
