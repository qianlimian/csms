/**
 * 
 */
package com.bycc.dao;

import java.util.List;

import org.smartframework.platform.repository.jpa.BaseJpaRepository;

import com.bycc.entity.BdmStrap;
import com.bycc.entity.CasePeople;


/**
 * @description
 * @author gaoningbo
 * @date 2017年5月3日
 * 
 */
public interface CasePeopleDao extends BaseJpaRepository<CasePeople, Integer>{

	List<CasePeople> findByCaseRecordId(Integer id);
	CasePeople findByStrap(BdmStrap bdmStrap);
	CasePeople findByStrapCode(Integer code);
}
