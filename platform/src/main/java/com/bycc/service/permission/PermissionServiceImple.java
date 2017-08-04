package com.bycc.service.permission;

import com.bycc.dao.BdmPoliceDao;
import com.bycc.dao.BdmPoliceStationDao;
import com.bycc.entity.BdmPolice;
import com.bycc.entity.BdmPoliceStation;
import com.bycc.enumitem.PoliceStationType;
import org.smartframework.manager.dao.user.UserDao;
import org.smartframework.manager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户权限过滤服务
 * User: yumingzhe
 * Time: 2017-5-23 10:05
 */
@Service
public class PermissionServiceImple implements PermissionService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private BdmPoliceDao policeDao;

	@Autowired
	private BdmPoliceStationDao stationDao;

	@Override
	public BdmPoliceStation findPoliceStation() {
		BdmPolice police = policeDao.findByUserId(User.getCurrentUser().getUserId());
		return police == null ? null : police.getPoliceStation();
	}

	//-------------------------------查询当前用户所在单位的下属单位----------------------------------------
	@Override
	public List<BdmPoliceStation> findSubPoliceStations() {
		List<BdmPoliceStation> stations = new ArrayList<>();

		BdmPoliceStation policeStation = findPoliceStation();

		// 如果policeStation为空或类型是'派出所'则终止查找
		if (policeStation == null || policeStation.getPoliceStationType() == PoliceStationType.PCS) {
			return stations;
		}

		// 如果policeStation类型是'分局'，则继续查找下属的'派出所'
		if (policeStation.getPoliceStationType() != null && policeStation.getPoliceStationType() == PoliceStationType.FJ) {
			List<BdmPoliceStation> subStations = stationDao.findByAreaTypeAndPoliceStationType(policeStation.getAreaType(), PoliceStationType.PCS);
			stations.addAll(subStations);
		}

		// 如果policeStation类型是'市局'，则查找所有下属的单位
		if (policeStation.getPoliceStationType() != null && policeStation.getPoliceStationType() == PoliceStationType.SJ) {
			List<BdmPoliceStation> subStations = stationDao.findAll();
			stations.addAll(subStations);
			stations.remove(policeStation);
		}
		return stations;
	}


	//----------------------------查询当前用户及下属用户----------------------------------

	@Override
	public User findUser() {
		return userDao.findOne(User.getCurrentUser().getUserId());
	}

	@Override
	public List<User> findSubUsers() {
		List<BdmPoliceStation> stations = findSubPoliceStations();
		List<User> users = new ArrayList<>();

		for (BdmPoliceStation station : stations) {
			for (BdmPolice police : station.getPolices()) {
				users.add(police.getUser());
			}
		}
		return users;
	}
}
