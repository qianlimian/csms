package org.smartframework.manager.service.user;

import java.util.*;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dao.user.UserDao;
import org.smartframework.manager.dao.userSetting.UserSettingDao;
import org.smartframework.manager.dto.user.UserSettingDto;
import org.smartframework.manager.entity.LoginUser;
import org.smartframework.manager.entity.User;
import org.smartframework.manager.dto.user.UserDto;
import org.smartframework.manager.entity.UserSetting;
import org.smartframework.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	 @Autowired
	 private UserDao dao;

    @Autowired
    private UserSettingDao usDao;

    /**
     * 查询用户列表
     */
    public List<UserDto> query(QueryBean qb) {
		//调用全局dao查询
		List<User> list = dao.findByQueryBean(qb);
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User user : list) {
		    UserDto dto = UserDto.toDto(user);
		    dtos.add(dto);
		}
		return dtos;
    }

	/**
	 * 查询用户列表
	 */
	@Override
	public List<UserDto> queryByGroupId(QueryBean qb, Integer groupId) {
		List<User> list = dao.findByGroupId(qb, groupId);
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User user : list) {
			UserDto dto = UserDto.toDto(user);
			dtos.add(dto);
		}
		return dtos;
	}

	/**
	 * 查询用户
	 */
	public UserDto findById(Integer id) {
		User user = dao.findOne(id);
		UserDto dto = UserDto.toDto(user);
		return dto;
	}

	/**
	 * 查询用户列表
	 */
	@Override
	public List<UserDto> findAll() {
		List<User> list = dao.findAll();
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User user : list) {
			UserDto dto = UserDto.toDto(user);
			dtos.add(dto);
		}
		return dtos;
	}

	/**
     * 保存用户
     */
    public UserDto save(UserDto dto) {
    	User user = null;
    	if(dto.getId() == null){
    		user = dto.toEntity();
    	}else{
    		user = dao.findOne(dto.getId());
    		if(user != null){
    			dto.toEntity(user);
    		}
    	}
    	user = dao.save(user);
    	dto = UserDto.toDto(user);
    	return dto;
    }

    /**
     * 删除用户
     */
    public void delete(String ids) {
		for (String id : ids.split(",")) {
		    User user = dao.findOne(Integer.parseInt(id));
		    if (user != null) {
		    	dao.delete(user);
		    }
		}
    }

    /**
     * 重置密码
     */
    public void resetPsw(String ids){
		for (String id : ids.split(",")) {
			User user = dao.findOne(Integer.parseInt(id));
			if (user != null) {
				//密码
				user.setPassword(MD5Utils.encode("123456"));
				dao.save(user);
			}
		}
    }
    
    /**
     * 根据登录名查询用户
     */
    @Override
	public User findByLoginName(String loginName) {
    	List<User> userList = dao.findByLoginName(loginName);
    	if (userList.isEmpty()) {
    		return null;
    	}
		return userList.get(0);
	}
    
    /**
     * 根据登录名查询用户角色
     */
    @Override
    public List<String> findRoleByLoginName(String loginName) {
    	List<String> roleList = dao.findRoleByLoginName(loginName);
    	return roleList;
    }

	/**
     * 更新登陆时间
     */
    @Override
    public void updateUserLoginTime(User user, Date loginDate) {
		user.setLastLoginDate(loginDate);
		dao.save(user);
    }

	/**
	 * 保存用户设置
	 */
	@Override
	public void saveSetting(UserSettingDto dto) {
		LoginUser loginUser = User.getCurrentUser();
		if (loginUser != null) {
			User user = dao.findOne(loginUser.getUserId());
			UserSetting setting = user.getUserSetting();
			if (setting == null) {
				setting = new UserSetting();
			}
			setting.setUser(user);
			setting.setPageWidth(dto.getPageWidth());
			setting.setMenuPosition(dto.getMenuPosition());
			usDao.save(setting);
		}
	}

}
