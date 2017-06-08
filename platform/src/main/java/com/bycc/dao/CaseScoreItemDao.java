/**
 * 
 */
package com.bycc.dao;

import java.util.List;

import org.smartframework.platform.repository.jpa.BaseJpaRepository;

import com.bycc.entity.CaseScoreItem;


/**
 * @description
 * @author gaoningbo
 * @date 2017年4月26日
 * 
 */
public interface CaseScoreItemDao extends BaseJpaRepository<CaseScoreItem, Integer>{

	List<CaseScoreItem> findByCaseScoreId(Integer id);
}
