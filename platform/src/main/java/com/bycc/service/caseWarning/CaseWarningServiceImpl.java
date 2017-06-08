package com.bycc.service.caseWarning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dao.user.UserDao;
import org.smartframework.utils.helper.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bycc.dao.CaseWarningDao;
import com.bycc.dto.caseWarning.CaseWarningDto;
import com.bycc.entity.BdmPoliceStation;
import com.bycc.entity.Case;
import com.bycc.entity.CaseWarning;
import com.bycc.service.permission.PermissionService;

@Service
public class CaseWarningServiceImpl implements CaseWarningService{
	
	@Autowired
	private CaseWarningDao dao;
	

	
	@Autowired UserDao userDao;
	
	@Autowired
	PermissionService permissionService;

	@Override
	public List<CaseWarningDto> query(QueryBean qb) {
		List<CaseWarningDto> dtos = new ArrayList<CaseWarningDto>();
		
		BdmPoliceStation policeStation = permissionService.findPoliceStation();
		
        if (policeStation != null) {
        	String[] hql = new String[]{"caze.masterUnit.id = :policeStationId"};
            Map<String, Object> param = new HashMap<String, Object>();
        	param.put("policeStationId", policeStation.getId());
    		List<CaseWarning> caseWarnings = dao.findByQueryBeanCondition(hql, param, qb);
    		for (CaseWarning caseWarning : caseWarnings) {
    			CaseWarningDto dto = new CaseWarningDto();
    			dto.setId(caseWarning.getId());
    			dto.setNote(caseWarning.getNote());
    			dto.setUpdateTime(caseWarning.getUpdateDate() != null ? DateHelper.formatDateToString(caseWarning.getUpdateDate(), "yyyy-MM-dd") : null);
    			Case caze = caseWarning.getCaze();
    			if (caze != null) {
    				dto.setCaseCode(caze.getCaseCode());
    				dto.setCaseName(caze.getCaseName());
    				dto.setCaseStatus(caze.getCaseStatus() != null ? caze.getCaseStatus().key() : null);
    				dto.setCaseType(caze.getCaseType() != null ? caze.getCaseType().key() : null);
                    dto.setPoliceStationName(caze.getMasterUnit() != null ? caze.getMasterUnit().getName() : "");
                    if (caze.getMasterPolice() != null && caze.getMasterPolice().getUser() != null) {
                        dto.setPoliceName(caze.getMasterPolice().getUser().getName());
                    }
    			}
    			dtos.add(dto);
    		}
		}
		return dtos;
	}

}
