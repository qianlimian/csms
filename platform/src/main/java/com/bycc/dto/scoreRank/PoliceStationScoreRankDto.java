package com.bycc.dto.scoreRank;

import java.math.BigDecimal;

public class PoliceStationScoreRankDto {
	private Integer policeStationId;
	private String policeStationName;
	//地区
	private String areaType;
	//总得分
	private BigDecimal totalScore;
	public Integer getPoliceStationId() {
		return policeStationId;
	}
	public void setPoliceStationId(Integer policeStationId) {
		this.policeStationId = policeStationId;
	}
	public String getPoliceStationName() {
		return policeStationName;
	}
	public void setPoliceStationName(String policeStationName) {
		this.policeStationName = policeStationName;
	}
	public BigDecimal getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	
}
