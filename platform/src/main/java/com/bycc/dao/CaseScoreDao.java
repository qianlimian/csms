/**
 * 
 */
package com.bycc.dao;

import org.smartframework.platform.repository.jpa.BaseJpaRepository;

import com.bycc.entity.CaseScore;


/**
 * @description
 * @author gaoningbo
 * @date 2017年4月25日
 * 
 */
public interface CaseScoreDao extends BaseJpaRepository<CaseScore, Integer>{
	
	CaseScore findByCaseRecordId(Integer id);	
	
}
