package com.bycc.service.caseWarning;

import java.util.List;

import org.smartframework.common.kendo.QueryBean;

import com.bycc.dto.caseWarning.CaseWarningDto;

public interface CaseWarningService {
	public List<CaseWarningDto> query(QueryBean qb);
}
