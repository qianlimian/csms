package com.bycc.service.overview;

import java.util.Map;

public interface OverviewService {

	/**
	 * 
	 * @description 根据登录用户查出派出所及其办案区和办案区名称
	 *              1.如果是市局用户，返回所有派出所和办案区名称
	 *              2.如果是派出所用户，返回该用户所属派出所和办案区名称
	 * @author liuxunhua
	 * @date 2017年5月3日 上午11:21:01
	 *
	 */
	String findPoliceStationList();

	/**
	 * 
	 * @description 
	 * @author liuxunhua
	 * @date 2017年5月3日 下午2:17:14
	 *
	 */
	Map<Integer, String> findCamerasByRoom(Integer roomId);

}
