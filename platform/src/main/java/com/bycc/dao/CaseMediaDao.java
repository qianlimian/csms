/**
 * 
 */
package com.bycc.dao;

import java.util.List;

import org.smartframework.platform.repository.jpa.BaseJpaRepository;

import com.bycc.entity.CaseMedia;

/**
 * @description
 * @author gaoningbo
 * @date 2017年5月3日
 * 
 */
public interface CaseMediaDao extends BaseJpaRepository<CaseMedia, Integer>{

	List<CaseMedia> findByCasePeopleId(Integer id);
	
	CaseMedia findByCaseRecordIdAndTitle(Integer id,String title);
}
