/**
 *
 */
package com.bycc.dto;

import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.bycc.entity.Case;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.manager.entity.User;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;
import com.bycc.entity.CaseRecord;
import com.bycc.enumitem.CaseHandle;
import com.bycc.enumitem.CaseType;

/**
 * @author gaoningbo
 * @description
 * @date 2017年4月14日
 */
@DtoClass
public class CaseRecordDto {

	private Integer id;
	// 案件ID
	private Integer caseId;
	// 警情编号
	private String alarmCode;
	// 案件编号
	private String caseCode;
	// 案件名称
	@NotEmpty(message = "不能为空")
	private String caseName;
	// 案件要情
	private String caseSummary;
	// 案件类型
	@NotEmpty(message = "不能为空")
	private String caseType;
	// 办理状态
	private String caseHandle;
	// 嫌疑人
	private String suspect;
	// 受理单位
	private Integer acceptUnitId;
	private String acceptUnit;// 显示值
	// 受理人
	private Integer acceptPoliceId;
	private String acceptPoliceName;// 显示值
	// 主办单位
    @NotNull(message = "不能为空")
	private Integer masterUnitId;
	private String masterUnit;// 显示值
	// 主办人
    @NotNull(message = "不能为空")
	private Integer masterPoliceId;
	private String masterPoliceName;// 显示值
	// 协办单位
    @NotNull(message = "不能为空")
	private Integer slaveUnitId;
	private String slaveUnit;// 显示值
	// 协办人
    @NotNull(message = "不能为空")
	private Integer slavePoliceId;
	private String slavePoliceName;// 显示值
	// 案发时间
	private String occurDate;
	// 接案时间
	private String acceptDate;
	// 立案时间
	private String registerDate;
	// 结案时间
	private String closeDate;
	// 办案时间
	private String startDate;
	// 备注
	private String note;

	public static CaseRecordDto toDto(CaseRecord cr) {

		CaseRecordDto dto = new CaseRecordDto();
		dto.setId(cr.getId());
		dto.setCaseId(cr.getCaze() != null ? cr.getCaze().getId() : null);
		dto.setCaseName(cr.getCaseName());
		dto.setAlarmCode(cr.getAlarmCode());
		dto.setCaseCode(cr.getCaseCode());
		dto.setCaseSummary(cr.getCaseSummary());
		dto.setCaseType(cr.getCaseType() != null ? cr.getCaseType().key() : null);
		dto.setCaseHandle(cr.getCaseHandle() != null ? cr.getCaseHandle().key() : null);
		dto.setSuspect(cr.getSuspect());
		if (cr.getAcceptUnit() != null) {
			dto.setAcceptUnitId(cr.getAcceptUnit().getId());
			dto.setAcceptUnit(cr.getAcceptUnit().getName());
		}
		if (cr.getMasterUnit() != null) {
			dto.setMasterUnitId(cr.getMasterUnit().getId());
			dto.setMasterUnit(cr.getMasterUnit().getName());
		}
		if (cr.getSlaveUnit() != null) {
			dto.setSlaveUnitId(cr.getSlaveUnit().getId());
			dto.setSlaveUnit(cr.getSlaveUnit().getName());
		}
		if (cr.getSlavePolice() != null) {
			dto.setSlavePoliceId(cr.getSlavePolice().getId());
			dto.setSlavePoliceName(cr.getSlavePolice().getUser() != null ? cr.getSlavePolice().getUser().getName() : null);
		}
		if (cr.getMasterPolice() != null) {
			dto.setMasterPoliceId(cr.getMasterPolice() != null ? cr.getMasterPolice().getId() : null);
			dto.setMasterPoliceName(cr.getMasterPolice() != null ? cr.getMasterPolice().getUser().getName() : null);
		}
		if (cr.getAcceptPolice() != null) {
			dto.setAcceptPoliceId(cr.getAcceptPolice() != null ? cr.getAcceptPolice().getId() : null);
			dto.setAcceptPoliceName(cr.getAcceptPolice() != null ? cr.getAcceptPolice().getUser().getName() : null);
		}
		dto.setOccurDate(DateHelper.formatDateToString(cr.getOccurDate(), "yyyy-MM-dd hh:mm"));
		dto.setAcceptDate(DateHelper.formatDateToString(cr.getAcceptDate(), "yyyy-MM-dd hh:mm"));
		dto.setStartDate(DateHelper.formatDateToString(cr.getStartDate(), "yyyy-MM-dd hh:mm"));
		dto.setRegisterDate(DateHelper.formatDateToString(cr.getRegisterDate(), "yyyy-MM-dd hh:mm"));
		dto.setCloseDate(DateHelper.formatDateToString(cr.getCloseDate(), "yyyy-MM-dd hh:mm"));
		return dto;
	}

	public CaseRecord toEntity() {
		CaseRecord caseRecord = new CaseRecord();
		caseRecord.setInsertDate(new Date());
        caseRecord.setOperatorId(User.getCurrentUser().getId());
		return toEntity(caseRecord);
	}

	public CaseRecord toEntity(CaseRecord entity) {
		entity.setAlarmCode(this.alarmCode);
		entity.setCaseCode(this.caseCode);
		entity.setCaseName(this.caseName);
		entity.setCaseSummary(this.caseSummary);
		entity.setCaseType(CaseType.getMatchByKey(this.caseType));
		entity.setCaseHandle(CaseHandle.getMatchByKey(this.caseHandle));
		entity.setSuspect(this.suspect);
		entity.setNote(this.note);
		try {
			entity.setAcceptDate(DateHelper.formatStringToDate(this.acceptDate, "yyyy-MM-dd hh:mm"));
			entity.setRegisterDate(DateHelper.formatStringToDate(this.registerDate, "yyyy-MM-dd hh:mm"));
			entity.setOccurDate(DateHelper.formatStringToDate(this.occurDate, "yyyy-MM-dd hh:mm"));
			entity.setStartDate(DateHelper.formatStringToDate(this.startDate, "yyyy-MM-dd hh:mm"));
			entity.setCloseDate(DateHelper.formatStringToDate(this.closeDate, "yyyy-MM-dd hh:mm"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		entity.setUpdateDate(new Date());
		return entity;
	}


	public static CaseRecord toEntity(Case caze) {
		CaseRecord caseRecord = new CaseRecord();
		caseRecord.setCaze(caze);
		caseRecord.setAlarmCode(caze.getAlarmCode());
		caseRecord.setCaseCode(caze.getCaseCode());
		caseRecord.setCaseName(caze.getCaseName());
		caseRecord.setCaseSummary(caze.getCaseSummary());
		caseRecord.setCaseType(caze.getCaseType());
		caseRecord.setSuspect(caze.getSuspect());
		caseRecord.setCaseHandle(CaseHandle.PENDING);

		caseRecord.setAcceptUnit(caze.getAcceptUnit());
		caseRecord.setAcceptPolice(caze.getAcceptPolice());
		caseRecord.setMasterUnit(caze.getMasterUnit());
		caseRecord.setMasterPolice(caze.getMasterPolice());
		caseRecord.setSlaveUnit(caze.getSlaveUnit());
		caseRecord.setSlavePolice(caze.getSlavePolice());

		caseRecord.setOccurDate(caze.getOccurDate());
		caseRecord.setAcceptDate(caze.getAcceptDate());
		caseRecord.setRegisterDate(caze.getRegisterDate());
		caseRecord.setStartDate(new Date());
		caseRecord.setInsertDate(new Date());
		caseRecord.setUpdateDate(new Date());
		caseRecord.setNote(caze.getNote());
        caseRecord.setOperatorId(User.getCurrentUser().getId());
		return caseRecord;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String toString() {
		return "CaseRecordDto [caseId=" + caseId + ", alarmCode=" + alarmCode + ", caseCode=" + caseCode + ", caseName="
				+ caseName + ", caseSummary=" + caseSummary + ", caseType=" + caseType + ", caseHandle=" + caseHandle
				+ ", suspect=" + suspect + ", acceptUnitId=" + acceptUnitId + ", acceptUnit=" + acceptUnit
				+ ", acceptPoliceId=" + acceptPoliceId + ", acceptPoliceName=" + acceptPoliceName + ", masterUnitId="
				+ masterUnitId + ", masterUnit=" + masterUnit + ", masterPoliceId=" + masterPoliceId
				+ ", masterPoliceName=" + masterPoliceName + ", slaveUnitId=" + slaveUnitId + ", slaveUnit=" + slaveUnit
				+ ", slavePoliceId=" + slavePoliceId + ", slavePoliceName=" + slavePoliceName + ", occurDate="
				+ occurDate + ", acceptDate=" + acceptDate + ", registerDate=" + registerDate + ", closeDate="
				+ closeDate + ", startDate=" + startDate + ", note=" + note + "]";
	}

}
