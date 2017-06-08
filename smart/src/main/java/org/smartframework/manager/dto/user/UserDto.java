package org.smartframework.manager.dto.user;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.manager.entity.User;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.MD5Utils;
import org.smartframework.utils.helper.DateHelper;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@DtoClass
public class UserDto {

	/**
     * 主键
     */
    private Integer id;

    /**
     * 姓名
     */
	@NotEmpty(message = "不能为空")
	@Size(max = 50, message = "姓名不能超过{max}个字符")
    private String name;

    /**
     * 登录名
     */
	@NotEmpty(message = "不能为空")
	@Size(max = 50, message = "不能超过{max}个字符")
    private String loginName;

    /**
     * 插入日期
     */
    private String insertDate;

    /**
     * 更新日期
     */
    private String updateDate;
    
    /**
     * 上次登录时间
     */
    private String lastLoginDate;

    /**
     * 密码
     */
	@NotEmpty(message = "不能为空")
	@Size(max = 50, message = "不能超过{max}个字符")
    private String password;

    /**
     * 操作人
     */
    private String operator;
    
    
    public User toEntity(){
    	User user = new User();
    	user.setInsertDate(new Date());
    	user.setPassword(MD5Utils.encode(password));
    	return toEntity(user);
    }
    
    public User toEntity(User user){
    	user.setUpdateDate(new Date());
		user.setLoginName(loginName);
		user.setName(name);
		return user;
    }
    
    public static UserDto toDto(User entity){
    	UserDto dto = new UserDto();
    	dto.setId(entity.getId());
    	dto.setLoginName(entity.getLoginName());
    	dto.setName(entity.getName());
    	dto.setPassword(entity.getPassword());
    	dto.setOperator(entity.getOperator());
		dto.setInsertDate(DateHelper.formatDateToString(entity.getInsertDate(), "yyyy-MM-dd HH:mm:ss"));
    	dto.setUpdateDate(DateHelper.formatDateToString(entity.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
    	dto.setLastLoginDate(DateHelper.formatDateToString(entity.getLastLoginDate(), "yyyy-MM-dd HH:mm:ss"));
    	return dto;
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

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}
