package org.smartframework.platform.security.login;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.smartframework.manager.entity.LoginUser;
import org.smartframework.manager.entity.User;
import org.smartframework.manager.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	/**
	 * @Title: loadUserByUsername
	 * @Description:根据登陆用户名读取用户实现
	 * @param username
	 * @return UserDetails
	 */
	public UserDetails loadUserByUsername(String loginName) {
		if (loginName == null || loginName.trim().length() == 0) {
			throw new UsernameNotFoundException("用户名不能为空");
		}

		// 2.开始检测项目是否需要自行处理登录用户的信息和角色装填
		LoginUser userDetails = null;

		User user = userService.findByLoginName(loginName);
		if (user == null) {
			throw new UsernameNotFoundException("用户" + loginName + " 不存在!");
		}

		List<String> roleList = userService.findRoleByLoginName(loginName);
		Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(roleList);

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		userDetails = new LoginUser(user.getLoginName(), user.getPassword(), 
				enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
		userDetails.setId(user.getId());
		// 加入登录时间信息和用户角色
		userDetails.setLoginTime(new Date());
		userDetails.setUserId(user.getId());
		userDetails.setName(user.getName());
		userDetails.setLoginName(user.getLoginName());
		userService.updateUserLoginTime(user, new Date());
		return userDetails;
	}

	/**
	 * @Title: obtainGrantedAuthorities
	 * @Description: 将字符串转换为权限对象
	 * @author nidongsheng 2013-6-8
	 * @param roleList
	 * @return Set<GrantedAuthority>
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(List<String> roleList) {
		Set<GrantedAuthority> authSet = Sets.newHashSet();
		if (roleList != null) {
			for (int i = 0; i < roleList.size(); i++) {
				String roleName = (String) roleList.get(i);
				authSet.add(new SimpleGrantedAuthority(roleName));
			}
		}
		return authSet;
	}

}
