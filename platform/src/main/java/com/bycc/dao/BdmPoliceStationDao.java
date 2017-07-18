/**
 *
 */
package com.bycc.dao;

import com.bycc.entity.BdmPoliceStation;
import com.bycc.enumitem.AreaType;
import com.bycc.enumitem.PoliceStationType;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;

import java.util.List;

/**
 * @author gaoningbo
 * @description
 * @date 2017年4月19日
 */
public interface BdmPoliceStationDao extends BaseJpaRepository<BdmPoliceStation, Integer> {
	public List<BdmPoliceStation> findByAreaType(AreaType areaType);

	List<BdmPoliceStation> findByAreaTypeAndPoliceStationType(AreaType areaType, PoliceStationType stationType);

	BdmPoliceStation findByName(String name);

}
