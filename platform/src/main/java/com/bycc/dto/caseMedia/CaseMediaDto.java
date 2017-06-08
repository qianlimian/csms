/**
 * 
 */
package com.bycc.dto.caseMedia;

import java.util.Date;

import org.smartframework.manager.entity.User;

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
	private Integer casePeopleId;
	private String note;

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

}
