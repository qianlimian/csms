package org.smartframework.manager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "smart_plugins")
@TableGenerator(name = "org.smartframework.entity.Plugin", // TableGenerator的名字，下面的“generator”使用
table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
pkColumnName = "KEY_ID_", // 列1的字段名
valueColumnName = "GEN_VALUE_", // 列2的字段名
pkColumnValue = "org.smartframework.entity.Plugin", // 该表存在ID_GEN中列1的值
initialValue = 1, // 初始值
allocationSize = 50 // 增长率
)
public class Plugin implements Serializable {

	private static final long serialVersionUID = 8362121473527657153L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_", nullable = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "org.smartframework.entity.Plugin")
	private Integer id;
	
	/**
	 * 编码
	 */
	@Column(name = "code_", nullable = true, unique = true, length = 127)
	private String code;
	
	/**
	 * 名称
	 */
	@Column(name = "name_", nullable = true, length = 50)
	private String name;
	
	/**
	 * 描述
	 */
	@Column(name = "desc_", length = 128)
	private String desc;

	/**
	 * 显示|隐藏
	 */
	@Column(name = "display_or_not_")
	private Boolean displayOrNot;

	/**
	 * 插入日期
	 */
	@Column(name = "insert_date_")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date insertDate;

	/**
	 * 更新日期
	 */
	@Column(name = "update_date_")
	@Temporal(value = TemporalType.TIMESTAMP)
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getDisplayOrNot() {
		return displayOrNot;
	}

	public void setDisplayOrNot(Boolean displayOrNot) {
		this.displayOrNot = displayOrNot;
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
