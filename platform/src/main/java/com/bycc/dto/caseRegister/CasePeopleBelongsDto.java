package com.bycc.dto.caseRegister;

import com.bycc.entity.CasePeopleBelongs;
import com.bycc.enumitem.StoreType;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.manager.entity.User;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.platform.validate.beans.GridDto;
import org.smartframework.utils.helper.DateHelper;

import javax.validation.constraints.NotNull;
import java.util.Date;

@DtoClass
public class CasePeopleBelongsDto extends GridDto {

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 物品名称
	 */
	@NotEmpty(message = "不能为空")
	private String name;

	/**
	 * 物品特征
	 */
	private String description;

	/**
	 * 数量
	 */
	@NotNull(message = "不能为空")
	private Integer count;

	/**
	 * 单位
	 */
	@NotEmpty(message = "不能为空")
	private String unit;

	/**
	 * 保管措施
	 */
	@NotEmpty(message = "不能为空")
	private String storeType;

	/**
	 * 物品柜id
	 */
	@NotNull(message = "不能为空")
	private Integer cabinetId;

	/**
	 * 是否涉案
	 */
	private Boolean involvedOrNot;

	/**
	 * 是否归还
	 */
	private Boolean backOrNot;

	/**
	 * 归还时间
	 */
	private String backDate;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 涉案人员
	 */
	private Integer casePeople;
	//----------------CONVERTER----------------

	public static CasePeopleBelongsDto toDto(CasePeopleBelongs entity) {
		CasePeopleBelongsDto dto = new CasePeopleBelongsDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
        dto.setBackDate(DateHelper.formatDateToString(entity.getBackDate(), "yyyy-MM-dd"));
		dto.setBackOrNot(entity.getBackOrNot());
		if (entity.getCabinet() != null) {
			dto.setCabinetId(entity.getCabinet().getId());
		}
		dto.setCount(entity.getCount());
		dto.setDescription(entity.getDescription());
		dto.setNote(entity.getNote());
		dto.setUnit(entity.getUnit());
		dto.setInvolvedOrNot(entity.getInvolvedOrNot());
		dto.setStoreType(entity.getStoreType() != null ? entity.getStoreType().key() : null);
		return dto;
	}

	public CasePeopleBelongs toEntity() {
		CasePeopleBelongs entity = new CasePeopleBelongs();
		entity.setInsertDate(new Date());
		return toEntity(entity);
	}

	public CasePeopleBelongs toEntity(CasePeopleBelongs entity) {
		entity.setId(this.id);
		entity.setCount(this.count);
		entity.setName(this.name);
		entity.setDescription(this.description);
		entity.setUnit(this.unit);
		entity.setStoreType(StoreType.getMatchByKey(this.storeType));
		entity.setInvolvedOrNot(this.involvedOrNot);
		entity.setBackOrNot(this.backOrNot);
        entity.setOperatorId(User.getCurrentUser().getId());
        entity.setUpdateDate(new Date());
		return entity;
	}

	//----------------GET&SET----------------
	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public Boolean getBackOrNot() {
		return backOrNot;
	}

	public void setBackOrNot(Boolean backOrNot) {
		this.backOrNot = backOrNot;
	}

	public Integer getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(Integer cabinetId) {
		this.cabinetId = cabinetId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getInvolvedOrNot() {
		return involvedOrNot;
	}

	public void setInvolvedOrNot(Boolean involvedOrNot) {
		this.involvedOrNot = involvedOrNot;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getCasePeople() {
		return casePeople;
	}

	public void setCasePeople(Integer casePeople) {
		this.casePeople = casePeople;
	}


}
