package com.bycc.dao;

import com.bycc.entity.BdmVideoCategory;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;

public interface BdmVideoCgDao extends BaseJpaRepository<BdmVideoCategory, Integer> {

	BdmVideoCategory findByCode(String code);

	BdmVideoCategory findByName(String name);

}
