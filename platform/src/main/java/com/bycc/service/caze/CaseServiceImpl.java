/**
 * 
 */
package com.bycc.service.caze;

import java.util.*;

import com.bycc.dao.CaseRecordDao;
import com.bycc.entity.CaseRecord;
import com.bycc.enumitem.CaseHandle;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.User;
import org.smartframework.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bycc.dao.CaseDao;
import com.bycc.dto.CaseDto;
import com.bycc.entity.Case;
import com.bycc.enumitem.CaseType;

/**
 * @description
 * @author gaoningbo
 * @date 2017年4月18日
 * 
 */
@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	CaseDao dao;

    @Autowired
    CaseRecordDao recordDao;

	@Override
	public List<CaseDto> query(String type, QueryBean qb) {
		String[] arr = new String[1];
		Map<String, Object> map = new HashMap<String, Object>();

		if (null != type && !"".equals(type)) {
			arr[0] = "caseType = :caseType";
			map.put("caseType", CaseType.getMatchByKey(type));
		} else {
            arr[0] = "caseRecord is null";
        }

		List<CaseDto> dtos = new ArrayList<CaseDto>();
		List<Case> cases = dao.findByQueryBeanCondition(arr, map, qb);
		for (Case c : cases) {
			dtos.add(CaseDto.toDto(c));
		}

		return dtos;
	}

	@Override
	public CaseDto findById(Integer id) {
		Case caze = dao.findOne(id);
		return CaseDto.toDto(caze);
	}

}
