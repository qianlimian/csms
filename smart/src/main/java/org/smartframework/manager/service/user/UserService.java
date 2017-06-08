package org.smartframework.manager.service.user;

import java.util.Date;
import java.util.List;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dto.user.UserSettingDto;
import org.smartframework.manager.entity.User;
import org.smartframework.manager.dto.user.UserDto;

public interface UserService {
	
	 /**
     * 查询用户列表
     */
    List<UserDto> query(QueryBean qb);

    /**
     * 查找用户
     */
    UserDto findById(Integer id);

    /**
     * 查询用户列表
     */
    List<UserDto> findAll();

    /**
     * 查询用户列表
     */
    List<UserDto> queryByGroupId(QueryBean qb, Integer groupId);

    /**
     * 保存用户
     */
    UserDto save(UserDto dto);

    /**
     * 删除用户
     */
    void delete(String ids);

    /**
     * 重置密码
     */
    void resetPsw(String ids);
    
    /**
     * 根据登录名查询用户
     */
    User findByLoginName(String loginName);

    /**
     * 根据登录名查询角色
     */
	List<String> findRoleByLoginName(String loginName);

    /**
     * 更新登陆时间
     */
	void updateUserLoginTime(User user, Date loginDate);

    /**
     * 保存用户设置
     */
    void saveSetting(UserSettingDto dto);
}
