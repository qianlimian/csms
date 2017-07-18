/**
 * 
 */
package com.bycc.service.caseScore;

import java.util.List;

import org.smartframework.common.kendo.QueryBean;

import com.bycc.dto.caseScore.CaseScoreDto;
import com.bycc.dto.caseScore.CaseScoreItemDto;
import com.bycc.dto.scoreRank.CaseScoreRankDto;
import com.bycc.dto.scoreRank.PoliceScoreRankDto;
import com.bycc.dto.scoreRank.PoliceStationScoreRankDto;

/**
 * @description
 * @author gaoningbo
 * @date 2017年4月25日
 * 
 */
public interface CaseScoreService {

	CaseScoreDto save(CaseScoreDto dto);
	
	CaseScoreDto findById(Integer id);

	CaseScoreDto findByCaseId(Integer id);
	
	List<CaseScoreItemDto> findSubsById(Integer id);
	
	List<CaseScoreRankDto> query(QueryBean qb, Integer policeId);
	
	List<PoliceScoreRankDto> bdmPoliceQueryRank(QueryBean qb);

	List<PoliceStationScoreRankDto> bdmPoliceStationQueryRank(QueryBean qb);

	List<CaseScoreRankDto> bdmPoliceStationQuery(QueryBean qb, Integer policeStationId);

	List<CaseScoreRankDto> caseQueryRank(QueryBean qb);
}
