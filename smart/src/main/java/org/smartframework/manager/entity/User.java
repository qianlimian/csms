package org.smartframework.manager.entity;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name = "smart_users")
@TableGenerator(name = "org.smartframework.entity.User", // TableGenerator的名字，下面的“generator”使用
table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
pkColumnName = "KEY_ID_", // 列1的字段名
valueColumnName = "GEN_VALUE_", // 列2的字段名
pkColumnValue = "org.smartframework.entity.User", // 该表存在ID_GEN中列1的值
initialValue = 1, // 初始值
allocationSize = 50 // 增长率
)
public class User implements Serializable {

	private static final long serialVersionUID = 733304426338271123L;

	public User() {
	}

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "org.smartframework.entity.User")
	private Integer id;

	/**
	 * 姓名
	 */
	@Column(name = "name_", length = 50)
	private String name;

	/**
	 * 登录名
	 */
	@Column(name = "login_name_", length = 50, unique = true)
	private String loginName;
	
	/**
	 * 密码
	 */
	@Column(name = "password_", length = 50)
	private String password;

	/**
	 * 权限组（主动方）
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "smart_user_group", joinColumns = @JoinColumn(name = "user_id_") , inverseJoinColumns = @JoinColumn(name = "group_id_") )
	private List<Group> groups = new ArrayList<Group>();

	/**
	 * 上次登录日期
	 */
	@Column(name = "last_login_date_")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastLoginDate;
	
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

	/**
	 * 用户设置
	 */
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserSetting userSetting;

	// 查询当前登录用户
	public static LoginUser getCurrentUser() {
		LoginUser userDetails = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if ("anonymousUser".equals(principal)) {
				return null;
			}
			userDetails = (LoginUser) principal;
		}
		return userDetails;
	}

	// 取用户（组）设置的menuIds
	public List<Integer> getMenuIds() {
		List<Integer> menuIds = new ArrayList<Integer>();
		for (Group group : this.groups) {
			for (Menu menu : group.getMenus()) {
				menuIds.add(menu.getId());
			}
		}
		return menuIds;
	}


	// 取用户（组）设置的roles
	public List<String> getRoles() {
		List<String> roles = new ArrayList<String>();
		for (Group group : this.groups) {
			for (Role role : group.getRoles()) {
				roles.add(role.getName());
			}
		}
		return roles;
	}

	// 取用户（组）设置的operates
	public Map<String, List<String>> getOperates() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (Group group : this.groups) {
			for (Operate operate : group.getOperates()) {
				Module module = operate.getModule();
				if (module != null && !"".equals(module.getCode())) {
					if (map.get(module.getCode()) == null) {
						map.put(module.getCode(), new ArrayList<String>());
					}
					map.get(module.getCode()).add(operate.getCode());
				}
			}
		}
		return map;
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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public UserSetting getUserSetting() {
		return userSetting;
	}

	public void setUserSetting(UserSetting userSetting) {
		this.userSetting = userSetting;
	}
}
