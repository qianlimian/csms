package com.bycc.dao;

import com.bycc.entity.BdmHandlingArea;
import com.bycc.entity.BdmStrap;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/20.
 */
public interface BdmStrapDao extends BaseJpaRepository<BdmStrap, Integer> {
	List<BdmStrap> findBdmStrapByHandlingArea(BdmHandlingArea handlingArea);

	@Query("select strap from BdmStrap strap where strap.status = com.bycc.enumitem.UsageStatus.UNUSED")
	List<BdmStrap> findUnusedStrap();
	
	BdmStrap findByCode(Integer code);
}
