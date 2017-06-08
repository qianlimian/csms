package com.bycc.entity;

import com.bycc.enumitem.CollectItem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author gaoningbo
 * @description 人身检查
 * @date 2017年4月11日
 */
@Entity
@Table(name = "case_people_inspect")
@TableGenerator(name = "com.bycc.entity.CasePeopleInspect",
		table = "ID_Sequence",
		pkColumnName = "KEY_ID_",
		valueColumnName = "GEN_VALUE_",
		pkColumnValue = "com.bycc.entity.CasePeopleInspect",
		initialValue = 1,
		allocationSize = 1
)
public class CasePeopleInspect implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.CasePeopleInspect")
	private Integer id;

	/**
	 * 自述情况
	 */
	@Column(name = "statement_")
	private String statement;

	/**
	 * 检查情况
	 */
	@Column(name = "inspection_")
	private String inspection;

	/**
	 * 是否信息采集
	 */
	@Column(name = "collect_or_not_")
	private Boolean collectOrNot;

	/**
	 * 是否信息入库
	 */
	@Column(name = "store_or_not_")
	private Boolean storeOrNot;

	/**
	 * 是否核对比查
	 */
	@Column(name = "verify_or_not_")
	private Boolean verifyOrNot;

	/**
	 * 采集项目
	 */
	@ElementCollection(targetClass = CollectItem.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "collect_item_")
	@CollectionTable(name = "case_inspect_collect_item", joinColumns = {@JoinColumn(name = "inspect_id_")})
	private List<CollectItem> collectItems;

	/**
	 * 涉案人员
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "case_people_id_")
	private CasePeople casePeople;

	/**
	 * 备注
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

	public CasePeople getCasePeople() {
		return casePeople;
	}

	public void setCasePeople(CasePeople casePeople) {
		this.casePeople = casePeople;
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

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<CollectItem> getCollectItems() {
		return collectItems;
	}

	public void setCollectItems(List<CollectItem> collectItems) {
		this.collectItems = collectItems;
	}
}
