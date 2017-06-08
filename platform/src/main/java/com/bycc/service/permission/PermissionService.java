package com.bycc.service.permission;

import com.bycc.entity.BdmPoliceStation;
import org.smartframework.manager.entity.User;

import java.util.List;

/**
 * 用户权限过滤服务
 * <p>
 * Created by yumingzhe on 2017-5-23.
 */
public interface PermissionService {

	/**
	 * 查找登录用户所在单位
	 *
	 * @return BdmPoliceStation
	 */
	BdmPoliceStation findPoliceStation();

	/**
	 * 查找登录用户所在单位的下属单位(不包括用户所在单位)
	 *
	 * @return BdmPoliceStation
	 */
	List<BdmPoliceStation> findSubPoliceStations();

	/**
	 * 查找登录用户
	 *
	 * @return User
	 */
	User findUser();

	/**
	 * 查找登录用户的下属用户(不包括当前用户)
	 *
	 * @return User
	 */
	List<User> findSubUsers();
}
