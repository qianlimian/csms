/**
 * 
 */
package com.bycc.service.caseScore;

import java.util.List;

import org.smartframework.common.kendo.QueryBean;

import com.bycc.dto.CaseScoreDto;
import com.bycc.dto.CaseScoreItemDto;
import com.bycc.dto.ScoreRankDto;

/**
 * @description
 * @author gaoningbo
 * @date 2017年4月25日
 * 
 */
public interface CaseScoreService {

	CaseScoreDto save(CaseScoreDto dto);
	
	CaseScoreDto findById(Integer id);

	CaseScoreDto findByCaseRecordId(Integer id);
	
	List<CaseScoreItemDto> findSubsById(Integer id);
	
	List<CaseScoreDto> query(QueryBean qb, Integer policeId);
	
	List<ScoreRankDto> queryRank(QueryBean qb);
}
