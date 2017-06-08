package com.bycc.dao;

import com.bycc.entity.BdmHandlingArea;
import com.bycc.entity.BdmPoliceStation;

import java.util.List;

import org.smartframework.platform.repository.jpa.BaseJpaRepository;

public interface BdmHandlingAreaDao extends BaseJpaRepository<BdmHandlingArea, Integer> {

	List<BdmHandlingArea> findByPoliceStation(BdmPoliceStation policeStation);
}
