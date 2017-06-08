package org.smartframework.manager.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "smart_menus")
@TableGenerator(name = "org.smartframework.entity.Menu", // TableGenerator的名字，下面的“generator”使用
table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
pkColumnName = "KEY_ID_", // 列1的字段名
valueColumnName = "GEN_VALUE_", // 列2的字段名
pkColumnValue = "org.smartframework.entity.Menu", // 该表存在ID_GEN中列1的值
initialValue = 1, // 初始值
allocationSize = 50 // 增长率
)
public class Menu implements Serializable {
	
	private static final long serialVersionUID = -8169675502351520503L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_", nullable = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "org.smartframework.entity.Menu")
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
	 * 所属插件
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plugin_", referencedColumnName = "code_")
	private Plugin plugin;
	
	/**
	 * url
	 */
	@Column(name = "url_", nullable = true, length = 256)
	private String url;
	
	/**
	 * 类型
	 * MODULE|GROUP|MENU
	 */
	@Column(name = "type_", nullable = true, length = 50)
	private String type;

	/**
	 * 描述
	 */
	@Column(name = "desc_", nullable = true, length = 128)
	private String desc;
	
	/**
	 * 显示顺序
	 */
	@Column(name = "display_order_", nullable = true, length = 22)
	private Integer displayOrder;
	
	/**
	 * 权限组（被动方）
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "menus", fetch = FetchType.LAZY)
	private Set<Group> groups = new HashSet<Group>();
	
	/**
	 * 父菜单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id_")
	private Menu parent;
	
	/**
	 * 子菜单
	 */
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parent", fetch = FetchType.LAZY)
	private Set<Menu> children = new HashSet<Menu>();
	
	//当前菜单的ModuleMenu
	public Menu getModuleMenu() {
		if ("MODULE".equals(this.type)) {
			return this;
		} else if ("GROUP".equals(this.type)) {
			return this.parent;
		} else {
			Menu parent = this.parent;
			return parent.getModuleMenu();
		}
	}
	
	//当前菜单的GroupMenu
	public Menu getGroupMenu() {
		if ("MODULE".equals(this.type)) {
			return null;
		} else if ("GROUP".equals(this.type)) {
			return this;
		} else {
			Menu parent = this.parent;
			return parent.getGroupMenu();
		}
	}
	
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Plugin getPlugin() {
		return plugin;
	}
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Set<Group> getGroups() {
		return groups;
	}
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public Set<Menu> getChildren() {
		return children;
	}
	public void setChildren(Set<Menu> children) {
		this.children = children;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

