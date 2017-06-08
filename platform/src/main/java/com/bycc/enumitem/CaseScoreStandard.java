/**
 * 
 */
package com.bycc.enumitem;

/**
 * @description 案件积分评价标准
 * @author gaoningbo
 * @date 2017年5月31日
 * 
 */
public enum CaseScoreStandard {

	/*CIVIL_ADJUST(new String[]{"行政案件(调节)","加分","客观"}),
	CIVIL_DETENTION(new String[]{"行政案件(拘留)","加分","客观"}),
	CIVIL_PENALTY(new String[]{"行政案件(罚款)","加分","客观"}),
	CRIMINAL_CHARGE(new String[]{"刑事案件(起诉)","加分","客观"}),
	CRIMINAL_ARREST(new String[]{"刑事案件(逮捕)","加分","客观"}),
	CRIMINAL_DETENTION(new String[]{"刑事案件(拘留)","加分","客观"}),*/
	
	CIVIL(new String[]{"行政案件","加分","客观"}),
	CRIMINAL(new String[]{"刑事案件","加分","客观"}),
	DISPUTE(new String[]{"纠纷案件","加分","客观"}),
	
	CASETIMEOUT(new String[]{"案件超时","减分","主观"}),
	CASEQUALITY(new String[]{"案件质量","减分","主观"}),
	VIDEOQUALITY(new String[]{"视频质量","减分","主观"}),
	VIDEOLOST(new String[]{"视频缺失","减分","主观"}),
	
	CASERISK_LOW(new String[]{"案件风险(低)","加分","客观"}),
	CASERISK_MID(new String[]{"案件风险(中)","加分","客观"}),
	CASERISK_HIGH(new String[]{"案件风险(高)","加分","客观"});
	
	private String key;
	private String[] value;
	
	public String key() {
		return this.key();
	}

	public String[] value() {
		return this.value;
	}
	
	private CaseScoreStandard(String[] value){
		this.value=value;
	}
	
	/**
	 * 按key查找枚举
	 */
	public static CaseScoreStandard getMatchByKey(String key) {
		for (CaseScoreStandard e : CaseScoreStandard.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}
	
	
}
