package org.smartframework.manager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "smart_roles")
@TableGenerator(name = "org.smartframework.entity.Role", // TableGenerator的名字，下面的“generator”使用
table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
pkColumnName = "KEY_ID_", // 列1的字段名
valueColumnName = "GEN_VALUE_", // 列2的字段名
pkColumnValue = "org.smartframework.entity.Role", // 该表存在ID_GEN中列1的值
initialValue = 1, // 初始值
allocationSize = 50 // 增长率
)

public class Role implements Serializable {
	
	private static final long serialVersionUID = -2994298168429832424L;
	
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "org.smartframework.entity.Role")
	private Integer id;
	
	/**
	 * 角色名称
	 */
	@Column(name = "name_", length = 50, unique = true)
	private String name;

	/**
	 * 描述
	 */
	@Column(name = "desc_", length = 128)
	private String description;

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

	/**
	 * 权限组（被动方）
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "roles", fetch = FetchType.LAZY)
	private List<Group> groups = new ArrayList<Group>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
