package com.bycc.service.bdmStrap;

import com.bycc.dao.BdmStrapDao;
import com.bycc.dto.bdmHandlingArea.BdmStrapDto;
import com.bycc.entity.BdmStrap;
import com.bycc.enumitem.UsageStatus;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-5-4 10:45
 */
@Service
public class BdmStrapServiceImpl implements BdmStrapService {
	@Autowired
	private BdmStrapDao bdmStrapDao;

	@Override
	public List<BdmStrapDto> findUnusedStraps(QueryBean qb) {
		String[] hql = new String[]{"status = :status"};

		Map<String, Object> param = new HashMap<String, Object>() {
			{
				put("status", UsageStatus.UNUSED);
			}
		};
		List<BdmStrap> straps = bdmStrapDao.findByQueryBeanCondition(hql, param, qb);
		return straps.stream().map(BdmStrapDto::toDto).collect(Collectors.toList());
	}

	@Override
	public List<BdmStrapDto> query(QueryBean qb) {
		List<BdmStrap> straps = bdmStrapDao.findByQueryBean(qb);
		List<BdmStrapDto> dtos = new ArrayList<>();
		for (BdmStrap strap : straps) {
			dtos.add(BdmStrapDto.toDto(strap));
		}
		return dtos;
	}
}
