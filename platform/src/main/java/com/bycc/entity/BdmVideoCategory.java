package com.bycc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 视频分类(基础数据)
 */
@Entity
@Table(name = "bdm_video_category")
@TableGenerator(name = "com.bycc.entity.BdmVideoCategory", // TableGenerator的名字，下面的“generator”使用
		table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
		pkColumnName = "KEY_ID_", // 列1的字段名
		valueColumnName = "GEN_VALUE_", // 列2的字段名
		pkColumnValue = "com.bycc.entity.BdmVideoCategory", // 该表存在ID_GEN中列1的值
		initialValue = 1, // 初始值
		allocationSize = 1 // 增长率
)
public class BdmVideoCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.BdmVideoCategory")
	private Integer id;

	/**
	 * 类别编号
	 */
	@Column(name = "code_")
	private String code;

	/**
	 * 类别名称
	 */
	@Column(name = "name_")
	private String name;

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

	public BdmVideoCategory(){}
	//构造函数--构造测试数据用
	public BdmVideoCategory(String code, String name) {
		this.code = code;
		this.name = name;
	}

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
