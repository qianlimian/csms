/**
 * 
 */
package com.bycc.dto.caseMedia;

import java.util.Date;

import org.smartframework.manager.entity.User;
import org.smartframework.utils.helper.DateHelper;

import com.bycc.entity.BdmVideoCategory;
import com.bycc.entity.CaseMedia;
import com.bycc.entity.CasePeople;
import com.bycc.entity.CaseRecord;

/**
 * @description
 * @author gaoningbo
 * @date 2017年5月16日
 * 
 */
public class CaseMediaDto {

	private Integer id;
	/** 视频名称 **/
	private String title;
	/** 播放地址 **/
	private String url;
	/** 类别 **/
	private String category;
	/**涉案人ID**/
	private Integer casePeopleId;
	/**涉案人Name**/
	private String casePeopleName;
	/**备注**/
	private String note;
	/**案件名称**/
	private String caseName;
	/**更新时间**/
	private String updateDate;
	/**主办人**/
	private String masterPoliceName;
	/**协办人**/
	private String slavePoliceName;
	/**主办单位**/
	private String masterPoliceStation;
	/**协办单位**/
	private String slavePoliceStation;
	
	public static CaseMedia toEntity(CaseMediaDto dto) {
		return null;
	}

	public static CaseMediaDto toDto(CaseMedia entity) {
		CaseMediaDto dto = new CaseMediaDto();
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setNote(entity.getNote());
		dto.setCategory(entity.getCategory() != null ? entity.getCategory().getName() : null);
		dto.setCasePeopleId(entity.getCasePeople() != null ? entity.getCasePeople().getId() : null);
		dto.setCasePeopleName(entity.getCasePeople() != null ? entity.getCasePeople().getName() : null);

		dto.setUpdateDate(DateHelper.formatDateToString(entity.getUpdateDate(), "yyyy-MM-dd"));
		
		if(entity.getCaseRecord()!=null){
			CaseRecord cr=entity.getCaseRecord();
			dto.setCaseName(cr.getCaseName());
			dto.setMasterPoliceStation(cr.getMasterUnit()!=null?cr.getMasterUnit().getName():null);
			dto.setSlavePoliceStation(cr.getSlaveUnit()!=null?cr.getSlaveUnit().getName():null);
			dto.setMasterPoliceName(cr.getMasterPolice()!=null?cr.getMasterPolice().getUser().getName():null);
			dto.setSlavePoliceName(cr.getSlavePolice()!=null?cr.getSlavePolice().getUser().getName():null);			
		}
		return dto;
	}
	
	public static CaseMedia toEntity(Integer id, String title, String url, BdmVideoCategory cg, CasePeople suspect,
			CaseRecord cr, String note) {
		CaseMedia caseMedia = new CaseMedia();
		if(id!=null){
			caseMedia.setId(id);			
		}else{
			caseMedia.setInsertDate(new Date());	
		}
		caseMedia.setCasePeople(suspect);
		caseMedia.setUpdateDate(new Date());
		caseMedia.setUrl(url);
		caseMedia.setTitle(title);
		caseMedia.setNote(note);
		caseMedia.setOperator(User.getCurrentUser().getLoginName());
		caseMedia.setCategory(cg);
		caseMedia.setCaseRecord(cr);
		return caseMedia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public String getCasePeopleName() {
		return casePeopleName;
	}

	public void setCasePeopleName(String casePeopleName) {
		this.casePeopleName = casePeopleName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getCasePeopleId() {
		return casePeopleId;
	}

	public void setCasePeopleId(Integer casePeopleId) {
		this.casePeopleId = casePeopleId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getMasterPoliceName() {
		return masterPoliceName;
	}

	public void setMasterPoliceName(String masterPoliceName) {
		this.masterPoliceName = masterPoliceName;
	}

	public String getSlavePoliceName() {
		return slavePoliceName;
	}

	public void setSlavePoliceName(String slavePoliceName) {
		this.slavePoliceName = slavePoliceName;
	}

	public String getMasterPoliceStation() {
		return masterPoliceStation;
	}

	public void setMasterPoliceStation(String masterPoliceStation) {
		this.masterPoliceStation = masterPoliceStation;
	}

	public String getSlavePoliceStation() {
		return slavePoliceStation;
	}

	public void setSlavePoliceStation(String slavePoliceStation) {
		this.slavePoliceStation = slavePoliceStation;
	}

	
}
