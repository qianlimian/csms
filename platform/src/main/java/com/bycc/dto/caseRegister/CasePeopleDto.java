package com.bycc.dto.caseRegister;

import com.bycc.entity.CasePeople;
import com.bycc.enumitem.CertificateType;
import com.bycc.enumitem.EnterReason;
import com.bycc.enumitem.Gender;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Date;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-4-27 17:16
 */
@DtoClass
public class CasePeopleDto {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 案件ID
	 */
	private Integer caseId;

    /**
     * 办案记录ID
     */
    private Integer caseRecordId;

	/**
	 * 姓名
	 */
	@NotEmpty(message = "不能为空")
	private String name;


	/**
	 * 出生年月
	 */
    @NotEmpty(message = "不能为空")
	private String birthday;

	/**
	 * 性别
	 */
	@NotEmpty(message = "不能为空")
	private String gender;

	/**
	 * 联系方式
	 */
	private String telNum;

	/**
	 * 家庭住址
	 */
	private String address;

	/**
	 * 证件号码
	 */
	@NotEmpty(message = "不能为空")
	private String certificateNum;

	/**
	 * 身份证件种类
	 */
	@NotEmpty(message = "不能为空")
	private String certificateType;

	/**
	 * 进入办案区事由
	 */
	private String enterReason;

	/**
	 * 进入办案区其他事由
	 */
	private String otherEnterReason;

	/**
	 * 手环id
	 */
	private Integer strapId;

	/**
	 * 手环名称
	 */
	private String strapName;


	/**
	 * 离开原因
	 */
	private String leaveReason;

	/**
	 * 随身物品是否全部返还(全部返还：true，部分返还：false)
	 */
	private Boolean allBelongsReturn;

	/**
	 * 备注(未返还物品情况记载)
	 */
	private String note;


	//-----------------Convert------------------
	public CasePeople toEntity() throws ParseException {
		CasePeople entity = new CasePeople();
		entity.setInsertDate(new Date());
		entity.setEnterDate(new Date());
		return toEntity(entity);
	}

	public CasePeople toEntity(CasePeople entity) throws ParseException {
		entity.setId(this.id);
		entity.setName(this.name);
        entity.setBirthday(DateHelper.formatStringToDate(this.birthday, "yyyy-MM-dd"));
		entity.setTelNum(this.telNum);
		entity.setGender(Gender.getMatchByKey(this.gender));
		entity.setAddress(this.address);
		entity.setCertificateType(CertificateType.getMatchByKey(this.certificateType));
		entity.setCertificateNum(this.certificateNum);
		entity.setEnterReason(EnterReason.getMatchByKey(this.getEnterReason()));
		entity.setOtherEnterReason(this.getOtherEnterReason());
		entity.setLeaveReason(this.leaveReason);
		entity.setAllBelongsReturn(this.allBelongsReturn);
		entity.setNote(this.note);
		return entity;
	}

	public static CasePeopleDto toDto(CasePeople entity) {
		if (entity == null) {
			return null;
		}
		CasePeopleDto dto = new CasePeopleDto();
		dto.setId(entity.getId());
		if (entity.getBirthday() != null) {
			dto.setBirthday(DateHelper.formatDateToString(entity.getBirthday(), "yyyy-MM-dd"));
		}
		dto.setGender(entity.getGender() == null ? null : entity.getGender().key());
		dto.setName(entity.getName());
		dto.setTelNum(entity.getTelNum());
		dto.setAddress(entity.getAddress());
		dto.setCertificateType(entity.getCertificateType() != null ? entity.getCertificateType().key() : null);
		dto.setCertificateNum(entity.getCertificateNum());
		if (entity.getStrap() != null) {
			dto.setStrapId(entity.getStrap().getId());
			dto.setStrapName(entity.getStrap().getName());
		}
		dto.setEnterReason(entity.getEnterReason() == null ? null : entity.getEnterReason().key());
		dto.setOtherEnterReason(entity.getOtherEnterReason());
		dto.setNote(entity.getNote());
		dto.setAllBelongsReturn(entity.getAllBelongsReturn());
		dto.setLeaveReason(entity.getLeaveReason());
		return dto;
	}

	//-----------------GET&SET------------------

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public Integer getCaseRecordId() {
		return caseRecordId;
	}

	public void setCaseRecordId(Integer caseRecordId) {
		this.caseRecordId = caseRecordId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCertificateNum() {
		return certificateNum;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getEnterReason() {
		return enterReason;
	}

	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}

	public String getOtherEnterReason() {
		return otherEnterReason;
	}

	public void setOtherEnterReason(String otherEnterReason) {
		this.otherEnterReason = otherEnterReason;
	}

	public Integer getStrapId() {
		return strapId;
	}

	public void setStrapId(Integer strapId) {
		this.strapId = strapId;
	}

	public String getStrapName() {
		return strapName;
	}

	public void setStrapName(String strapName) {
		this.strapName = strapName;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public Boolean getAllBelongsReturn() {
		return allBelongsReturn;
	}

	public void setAllBelongsReturn(Boolean allBelongsReturn) {
		this.allBelongsReturn = allBelongsReturn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
