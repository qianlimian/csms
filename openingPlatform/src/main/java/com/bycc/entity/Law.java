package com.bycc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gaoningbo
 * @description 法律法规
 * @date 2017年7月5日
 */
@Entity
@Table(name = "law")
@TableGenerator(name = "com.bycc.entity.Law",
		table = "ID_Sequence",
		pkColumnName = "KEY_ID_",
		valueColumnName = "GEN_VALUE_",
		pkColumnValue = "com.bycc.entity.Law",
		initialValue = 1,
		allocationSize = 1)
public class Law implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.Law")
	private Integer id;

	/**
	 * 标题
	 */
	@Column(name = "title_")
	private String title;

	/**
	 * 内容
	 */
	@Column(name = "content_", columnDefinition = "text")
	private String content;

	/**
	 * 新增时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insertDate_")
	private Date insertDate;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate_")
	private Date updateDate;

	/**
	 * 案件
	 */
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "laws", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<CaseRecord> caseRecords = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public List<CaseRecord> getCaseRecords() {
		return caseRecords;
	}

	public void setCaseRecords(List<CaseRecord> caseRecords) {
		this.caseRecords = caseRecords;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
