package com.bycc.service.caseRegister;

import com.bycc.dto.caseRegister.CasePeopleBelongsDto;
import com.bycc.dto.caseRegister.CasePeopleBelongsSaveDto;
import com.bycc.dto.caseRegister.CasePeopleDto;
import com.bycc.dto.caseRegister.CasePeopleInspectDto;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.platform.exception.BusinessException;

import java.util.List;

public interface CaseRegisterService {

	List<CasePeopleDto> query(Integer caseRecordId, Integer caseId, QueryBean qb);

	CasePeopleDto findById(Integer id);

	CasePeopleDto save(CasePeopleDto dto) throws Exception;

	void delete(String ids);

	List<CasePeopleBelongsDto> findPeopleBelongs(Integer peopleId, QueryBean qb);

	List<CasePeopleBelongsDto> saveOrUpdatePeopleBelongs(CasePeopleBelongsSaveDto dto) throws Exception;

    void returnPeopleBelongs(List<Integer> belongIds);

	void bindStrap(Integer peopleId, Integer strapId) throws BusinessException;

	void unBindStrap(CasePeopleDto dto);

	CasePeopleInspectDto findPeopleInspect(Integer peopleId);

	CasePeopleInspectDto savePeopleInspect(CasePeopleInspectDto dto);

	void print(Integer peopleId, String reportName) throws BusinessException;

}
