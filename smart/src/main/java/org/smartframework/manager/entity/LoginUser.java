package org.smartframework.manager.entity;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {
    private static final long serialVersionUID = 1919464185097508773L;
    
    private Integer id;

    private Date loginTime;

    private String name;

    private Integer userId;

    private String loginName;

    public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired,
	    boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities)
		    throws IllegalArgumentException {
    		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * 重写hashcode和equals方法，目的是为了实现session管理
     */
    @Override
    public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		return result;
    }

    @Override
    public boolean equals(Object obj) {
		if (this == obj)
		    return true;
		if (!super.equals(obj))
		    return false;
		if (getClass() != obj.getClass())
		    return false;
		LoginUser other = (LoginUser) obj;
		if (loginName == null) {
		    if (other.loginName != null)
			return false;
		} else if (!loginName.equals(other.loginName))
		    return false;
		return true;
    }

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
