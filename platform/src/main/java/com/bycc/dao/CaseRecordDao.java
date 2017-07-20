/**
 * 
 */
package com.bycc.dao;

import java.util.List;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import com.bycc.entity.CaseRecord;

/**
 * @description
 * @author gaoningbo
 * @date 2017年4月14日
 * 
 */
public interface CaseRecordDao extends BaseJpaRepository<CaseRecord, Integer>{

	List<CaseRecord> findByMasterUnitId(Integer id);
	
}
