package com.bycc.syncService.dao;

import com.bycc.syncService.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wanghaidong on 2017/5/26.
 */
@Repository
public interface CaseDao extends JpaRepository<Case, Integer> {

	Case findByAlarmCode(String alarmCode);

	@Query("select c from Case c where c.caseStatus != CaseStatus.CLOSED")
	List<Case> findUnCloseCase();
}
