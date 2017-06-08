package com.bycc.service.bdmStrap;

import com.bycc.dto.bdmHandlingArea.BdmStrapDto;
import com.bycc.entity.BdmStrap;
import org.smartframework.common.kendo.QueryBean;

import java.util.List;

public interface BdmStrapService {

	List<BdmStrapDto> findUnusedStraps(QueryBean qb);

	List<BdmStrapDto> query(QueryBean qb);
}
