/**
 * 
 */
package com.bycc.dto.scoreRank;

import java.math.BigDecimal;

import com.bycc.entity.BdmPolice;

/**
 * @description 总积分
 * @author gaoningbo
 * @date 2017年4月27日
 * 
 */
public class PoliceScoreRankDto {

	//警员ID
	private Integer policeId;
	//警员名称
	private String policeName;
	//警局
	private String policeStation;
	//总得分
	private BigDecimal totalScore;
		
	public static PoliceScoreRankDto toDto(BdmPolice police){
		PoliceScoreRankDto scoreDto=new PoliceScoreRankDto();
		scoreDto.setPoliceId(police.getId());
		scoreDto.setPoliceName(police.getUser()!=null?police.getUser().getName():null);
		if(police.getPoliceStation()!=null){
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
