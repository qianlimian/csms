/**
 * 
 */
package com.bycc.dao;

import java.util.List;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import com.bycc.entity.CaseRecord;
import org.springframework.data.jpa.repository.Query;

/**
 * @description
 * @author gaoningbo
 * @date 2017年4月14日
 * 
 */
public interface CaseRecordDao extends BaseJpaRepository<CaseRecord, Integer>{

	List<CaseRecord> findByMasterUnitId(Integer id);

	@Query("select e.caze.id from CaseRecord e")
	List<Integer> getCaseIds();
	
}
