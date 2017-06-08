package com.bycc.entity;

import javax.persistence.*;

import com.bycc.enumitem.UsageStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * @description RFID手环
 * @author gaoningbo
 * @date 2017年4月11日
 * 
 */
@Entity
@Table(name = "bdm_strap")
@TableGenerator(name = "com.bycc.entity.BdmStrap",
		table = "ID_Sequence",
		pkColumnName = "KEY_ID_",
		valueColumnName = "GEN_VALUE_",
		pkColumnValue = "com.bycc.entity.BdmStrap",
		initialValue = 1,
		allocationSize = 1
)
public class BdmStrap implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID(手环编号)
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.BdmStrap")
	private Integer id;

	/**
	 * 编码
	 */
	@Column(name = "code_")
	private String code;

	/**
	 * 手环名称
	 */
	@Column(name="name_")
	private String name;

	/**
	 * 使用状态
	 */
	@Column(name = "status_")
	@Enumerated(EnumType.STRING)
	private UsageStatus status;

	/**
	 * 办案区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "handling_area_id_")
	private BdmHandlingArea handlingArea;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UsageStatus getStatus() {
		return status;
	}

	public void setStatus(UsageStatus status) {
		this.status = status;
	}

	public BdmHandlingArea getHandlingArea() {
		return handlingArea;
	}

	public void setHandlingArea(BdmHandlingArea handlingArea) {
		this.handlingArea = handlingArea;
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
}
