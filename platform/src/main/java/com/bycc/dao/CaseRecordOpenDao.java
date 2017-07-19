package com.bycc.dao;

import com.bycc.entity.BdmPoliceStation;
import com.bycc.entity.CaseRecord;
import com.bycc.enumitem.CaseHandle;
import com.bycc.enumitem.CaseType;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghaidong on 2017/7/17.
 */
public interface CaseRecordOpenDao extends BaseJpaRepository<CaseRecord, Long> {

//  @Query("select c from CaseRecord as c where c.caseHandle =: caseHandle and c.masterUnit =: masterUnit and c.acceptDate between :acceptStart and :acceptEnd and c.closeDate between :closeStart and :closeEnd")
//  List<CaseRecord> findByCondition(@Param("caseHandle")CaseHandle caseHandle, @Param("masterUnit")BdmPoliceStation masterUnit, @Param("acceptStart")Date acceptStart, @Param("acceptEnd")Date acceptEnd,@Param("closeStart")Date closeStart,@Param("closeEnd")Date closeEnd);
    @Query("select c from CaseRecord as c where c.caseHandle = :caseHandle and c.masterUnit =:masterUnit and c.acceptDate between :acceptStart and :acceptEnd and c.closeDate between :closeStart and :closeEnd")
    List<CaseRecord> findCondition(@Param("caseHandle") CaseHandle caseHandle,@Param("masterUnit") BdmPoliceStation masterUnit,@Param("acceptStart")Date acceptStart, @Param("acceptEnd")Date acceptEnd,@Param("closeStart")Date closeStart,@Param("closeEnd")Date closeEnd);
}

