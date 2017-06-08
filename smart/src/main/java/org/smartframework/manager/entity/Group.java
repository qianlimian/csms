package org.smartframework.manager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "smart_groups")
@TableGenerator(name = "org.smartframework.entity.Group", // TableGenerator的名字，下面的“generator”使用
table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
pkColumnName = "KEY_ID_", // 列1的字段名
valueColumnName = "GEN_VALUE_", // 列2的字段名
pkColumnValue = "org.smartframework.entity.Group", // 该表存在ID_GEN中列1的值
initialValue = 1, // 初始值
allocationSize = 50 // 增长率
)
public class Group implements Serializable {

	private static final long serialVersionUID = -3692915583184445205L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "org.smartframework.entity.Group")
	private Integer id;
	
	/**
	 * 组名
	 */
	@Column(name = "name_", length = 50, unique = true)
	private String name;
	
	/**
	 * 描述
	 */
	@Column(name = "desc_", length = 128)
	private String description;
	
	/**
	 * 用户（被动方）
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "groups", fetch = FetchType.LAZY)
	private List<User> users = new ArrayList<User>();
	
	/**
	 * 角色（主动方）
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "smart_group_role", joinColumns = @JoinColumn(name = "group_id_") , inverseJoinColumns = @JoinColumn(name = "role_id_") )
	private List<Role> roles = new ArrayList<Role>();
	
	/**
	 * 菜单（主动方）
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "smart_group_menu", joinColumns = { @JoinColumn(name = "group_id_") }, inverseJoinColumns = { @JoinColumn(name = "menu_id_") })
	private Set<Menu> menus = new HashSet<Menu>();

	/**
	 * 操作（主动方）
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "smart_group_operate", joinColumns = @JoinColumn(name = "group_id_") , inverseJoinColumns = @JoinColumn(name = "operate_id_") )
	private Set<Operate> operates = new HashSet<Operate>();

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
	 * 操作人
	 */
	@Column(name = "operator_", length = 50)
	private String operator;

	/*--------------------------------------------------------------------------*/
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public Set<Operate> getOperates() {
		return operates;
	}

	public void setOperates(Set<Operate> operates) {
		this.operates = operates;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}


}
