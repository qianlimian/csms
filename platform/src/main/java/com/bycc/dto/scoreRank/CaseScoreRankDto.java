package com.bycc.dto.scoreRank;

import java.math.BigDecimal;

import com.bycc.entity.Case;
import com.bycc.entity.CaseScore;

public class CaseScoreRankDto {
	private Integer id;
	private Integer caseId;
	private String caseName;
	private String masterUnitName;
	private String slaveUnitName;
	private String masterPoliceName;
	private String slavePoliceName;
	private BigDecimal totalScore;
	
	public static CaseScoreRankDto toDto(CaseScore caseScore) {
		if (caseScore != null && caseScore.getCaze() != null) {
			Case caze = caseScore.getCaze();
			CaseScoreRankDto dto = new CaseScoreRankDto();
			dto.setId(caseScore.getId());
			dto.setCaseId(caze.getId());
			dto.setCaseName(caze.getCaseName());
			dto.setMasterPoliceName(caze.getMasterPolice() != null ? caze.getMasterPolice().getUser().getName() : "");
			dto.setMasterUnitName(caze.getMasterUnit() != null ? caze.getMasterUnit().getName() : "");
			dto.setSlavePoliceName(caze.getSlavePolice() != null ? caze.getSlavePolice().getUser().getName() : "");
			dto.setSlaveUnitName(caze.getSlaveUnit() != null ? caze.getSlaveUnit().getName() : "");
			dto.setTotalScore(caseScore.getTotalScore());
			return dto;
		}
		return null;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCaseId() {
		return caseId;
	}
	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getMasterUnitName() {
		return masterUnitName;
	}
	public void setMasterUnitName(String masterUnitName) {
		this.masterUnitName = masterUnitName;
	}
	public String getSlaveUnitName() {
		return slaveUnitName;
	}
	public void setSlaveUnitName(String slaveUnitName) {
		this.slaveUnitName = slaveUnitName;
	}
	public String getMasterPoliceName() {
		return masterPoliceName;
	}
	public void setMasterPoliceName(String masterPoliceName) {
		this.masterPoliceName = masterPoliceName;
	}
	public String getSlavePoliceName() {
		return slavePoliceName;
	}
	public void setSlavePoliceName(String slavePoliceName) {
		this.slavePoliceName = slavePoliceName;
	}
	public BigDecimal getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}
}
