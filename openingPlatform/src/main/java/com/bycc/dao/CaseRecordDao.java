/**
 *
 */
package com.bycc.dao;


import com.bycc.entity.CaseRecord;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author gaoningbo
 * @description
 * @date 2017年4月14日
 */
public interface CaseRecordDao extends BaseJpaRepository<CaseRecord, Integer> {

	@Query("select cr from CaseRecord cr, CasePeople cp where cp.caseRecord = cr and cp.name = :name and cp.certificateNum = :certificateNum order by cr.insertDate desc")
	CaseRecord findByCasePeople(@Param("name") String name, @Param("certificateNum") String certificateNum);
}
