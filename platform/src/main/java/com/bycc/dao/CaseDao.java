/**
 * 
 */
package com.bycc.dao;

import com.bycc.entity.BdmPolice;
import com.bycc.entity.BdmPoliceStation;

import org.smartframework.platform.repository.jpa.BaseJpaRepository;

import com.bycc.entity.Case;

import java.util.List;


/**
 * @description
 * @author gaoningbo
 * @date 2017年4月18日
 * 
 */
public interface CaseDao extends BaseJpaRepository<Case, Integer>{

    List<Case> findByMasterPolice(BdmPolice police);

    List<Case> findBySlavePolice(BdmPolice police);

    List<Case> findByMasterUnitOrSlaveUnit(BdmPoliceStation masterUnit, BdmPoliceStation slaveUnit);

}
