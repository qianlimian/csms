package com.bycc.dto.caseRegister;

import com.bycc.entity.CasePeopleInspect;
import com.bycc.enumitem.CollectItem;
import org.smartframework.manager.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-5-8 10:49
 */
public class CasePeopleInspectDto {
	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 人员ID
	 */
	private Integer casePeopleId;

	/**
	 * 自述情况
	 */
	private String statement;

	/**
	 * 检查情况
	 */
	private String inspection;

	/**
	 * 是否信息采集
	 */
	private Boolean collectOrNot;

	/**
	 * 是否信息入库
	 */
	private Boolean storeOrNot;

	/**
	 * 是否核对比查
	 */
	private Boolean verifyOrNot;

	/**
	 * 采集项目
	 */
	private List<String> collectItems;

	//----------------------CONVERTER----------------------
	public static CasePeopleInspectDto toDto(CasePeopleInspect entity) {
		CasePeopleInspectDto dto = new CasePeopleInspectDto();
		if (entity == null) {
			dto.setCollectItems(new ArrayList<>());
			return dto;
		}
		dto.setId(entity.getId());
		dto.setCasePeopleId(entity.getCasePeople().getId());
		dto.setInspection(entity.getInspection());
		dto.setStatement(entity.getStatement());
		dto.setStoreOrNot(entity.getStoreOrNot());
		dto.setCollectOrNot(entity.getCollectOrNot());
		dto.setVerifyOrNot(entity.getVerifyOrNot());
		List<String> items = new ArrayList<>();
		for (CollectItem item : entity.getCollectItems()) {
			items.add(item.key());
		}
		dto.setCollectItems(items);
		return dto;
	}


	public CasePeopleInspect toEntity(CasePeopleInspect entity) {
		entity.setId(this.getId());
		entity.setStatement(this.getStatement());
		entity.setInspection(this.getInspection());
		entity.setCollectOrNot(this.getCollectOrNot());
		entity.setStoreOrNot(this.getStoreOrNot());
		entity.setVerifyOrNot(this.getVerifyOrNot());
        entity.setUpdateDate(new Date());
        entity.setOperatorId(User.getCurrentUser().getId());
		List<CollectItem> items = new ArrayList<>();
		if (this.getCollectItems() != null) {
			for (String e : this.getCollectItems()) {
				items.add(CollectItem.getMatchByKey(e));
			}
			entity.setCollectItems(items);
		}
		return entity;
	}

	public CasePeopleInspect toEntity() {
		CasePeopleInspect entity = new CasePeopleInspect();
        entity.setInsertDate(new Date());
		return this.toEntity(entity);
	}

	//----------------------GET&SET----------------------


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCasePeopleId() {
        return casePeopleId;
    }

    public void setCasePeopleId(Integer casePeopleId) {
        this.casePeopleId = casePeopleId;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getInspection() {
        return inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public Boolean getCollectOrNot() {
        return collectOrNot;
    }

    public void setCollectOrNot(Boolean collectOrNot) {
        this.collectOrNot = collectOrNot;
    }

    public Boolean getStoreOrNot() {
        return storeOrNot;
    }

    public void setStoreOrNot(Boolean storeOrNot) {
        this.storeOrNot = storeOrNot;
    }

    public Boolean getVerifyOrNot() {
        return verifyOrNot;
    }

    public void setVerifyOrNot(Boolean verifyOrNot) {
        this.verifyOrNot = verifyOrNot;
    }

    public List<String> getCollectItems() {
        return collectItems;
    }

    public void setCollectItems(List<String> collectItems) {
        this.collectItems = collectItems;
    }
}
