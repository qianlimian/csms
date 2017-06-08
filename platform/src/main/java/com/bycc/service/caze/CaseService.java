/**
 * 
 */
package com.bycc.service.caze;

import java.util.List;
import org.smartframework.common.kendo.QueryBean;
import com.bycc.dto.CaseDto;

/**
 * @description
 * @author gaoningbo
 * @date 2017年4月18日
 * 
 */
public interface CaseService {

	List<CaseDto> query(String type, QueryBean queryBean);

	CaseDto findById(Integer id);

}
