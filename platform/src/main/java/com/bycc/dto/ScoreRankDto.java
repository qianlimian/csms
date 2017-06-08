/**
 * 
 */
package com.bycc.dto;

import java.math.BigDecimal;

import com.bycc.entity.BdmPolice;

/**
 * @description 总积分
 * @author gaoningbo
 * @date 2017年4月27日
 * 
 */
public class ScoreRankDto {

	//警员ID
	private Integer policeId;
	//警员名称
	private String policeName;
	//地区
	private String areaType;
	//警局
	private String policeStation;
	//总得分
	private BigDecimal totalScore;
		
	public static ScoreRankDto toDto(BdmPolice police){
		ScoreRankDto scoreDto=new ScoreRankDto();
		scoreDto.setPoliceId(police.getId());
		scoreDto.setPoliceName(police.getUser()!=null?police.getUser().getName():null);
		if(police.getPoliceStation()!=null){
			scoreDto.setAreaType(police.getPoliceStation().getAreaType().key());
			scoreDto.setPoliceStation(police.getPoliceStation().getName());
		}
		return scoreDto;
	}

	public Integer getPoliceId() {
		return policeId;
	}

	public void setPoliceId(Integer policeId) {
		this.policeId = policeId;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getPoliceStation() {
		return policeStation;
	}

	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
	}

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}
}
