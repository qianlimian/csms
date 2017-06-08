package com.bycc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 案件音视频
 */
@Entity
@Table(name = "case_media")
@TableGenerator(name = "com.bycc.entity.CaseMedia", // TableGenerator的名字，下面的“generator”使用
		table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
		pkColumnName = "KEY_ID_", // 列1的字段名
		valueColumnName = "GEN_VALUE_", // 列2的字段名
		pkColumnValue = "com.bycc.entity.CaseMedia", // 该表存在ID_GEN中列1的值
		initialValue = 1, // 初始值
		allocationSize = 1 // 增长率
)
public class CaseMedia implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.CaseMedia")
	private Integer id;

	/**
	 * 标题
	 */
	@Column(name = "title_")
	private String title;

	/**
	 * 视频存储位置
	 */
	@Column(name = "url_")
	private String url;

	/**
	 * 分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id_")
	private BdmVideoCategory category;

	/**
	 * 涉案人员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "case_people_id_")
	private CasePeople casePeople;
	
	/**
	 * 办案记录
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "case_record_id_")
	private CaseRecord caseRecord;
	
	/**
	 * 备注
	 */
	@Column(name = "note_")
	private String note;

	/**
	 * 操作人
	 */
	@Column(name = "operator_")
	private String operator;

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

	public BdmVideoCategory getCategory() {
		return category;
	}

	public void setCategory(BdmVideoCategory category) {
		this.category = category;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public CaseRecord getCaseRecord() {
		return caseRecord;
	}

	public void setCaseRecord(CaseRecord caseRecord) {
		this.caseRecord = caseRecord;
	}
	
}
