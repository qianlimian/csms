package com.bycc.dao;

import com.bycc.entity.BdmPolice;

import org.smartframework.manager.entity.User;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;

public interface BdmPoliceDao extends BaseJpaRepository<BdmPolice, Integer> {

	BdmPolice findByUser(User user);
	
	BdmPolice findByUserId(Integer userId);
}
