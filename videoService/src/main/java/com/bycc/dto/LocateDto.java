/**
 * 
 */
package com.bycc.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 定位截取返回信息
 * @author gaoningbo
 * @date 2017年6月29日
 * 
 */
public class LocateDto {

	/**
	 * 办案记录ID
	 */
	private Integer caseRecoredId;
	/**
	 * 涉案人员ID
	 */
	private Integer casePeopleId;
	/**
	 * 房间类型
	 */
	private String roomType;
	/**
	 * 手环ID
	 */
	private Integer tagId;
	/**
	 * 摄像头IP
	 */
	private List<CameraDto> cameraDtos=new ArrayList<>();
	/**
	 * 人员轨迹ID
	 */
	private Integer traceId;
	
	public Integer getCaseRecoredId() {
		return caseRecoredId;
	}
	public void setCaseRecoredId(Integer caseRecoredId) {
		this.caseRecoredId = caseRecoredId;
	}
	public Integer getCasePeopleId() {
		return casePeopleId;
	}
	public void setCasePeopleId(Integer casePeopleId) {
		this.casePeopleId = casePeopleId;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public List<CameraDto> getCameraDtos() {
		return cameraDtos;
	}
	public void setCameraDtos(List<CameraDto> cameraDtos) {
		this.cameraDtos = cameraDtos;
	}
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	public Integer getTraceId() {
		return traceId;
	}
	public void setTraceId(Integer traceId) {
		this.traceId = traceId;
	}
}
