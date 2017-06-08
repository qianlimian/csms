/**
 * 
 */
package com.bycc.dto.caseMedia;

import java.io.File;
import java.util.Date;

/**
 * @description 视频目录列表
 * @author gaoningbo
 * @date 2017年5月3日
 * 
 */
public class MediaTreeListDto {

	private Integer caseRecordId;
	/** 文件名称 */
	private String label;
	/** 是否有子菜单 */
	private Boolean hasChildren;
	/*** 映射路径 */
	private String mappingPath;
	/** 流媒体服务器路径 **/
	private String serverPath;
	/** 视频分类 */
	private String mediaCategory;
	/** 房间类别 */
	private String roomCategory;
	/** 备注 */
	private String note;
	/**视频开始时间**/
	private String startDate;
	/**视频结束时间**/
	private String endDate;
	
	public static MediaTreeListDto toDto(File file) {
		MediaTreeListDto dto = new MediaTreeListDto();
		dto.setHasChildren(true);
		dto.setLabel(file.getName());
		dto.setMappingPath(file.getAbsolutePath());
		if (file.isDirectory()) {
			dto.setHasChildren(true);
		} else {			
			dto.setHasChildren(false);
		}		
		return dto;
	}

	public Integer getCaseRecordId() {
		return caseRecordId;
	}

	public void setCaseRecordId(Integer caseRecordId) {
		this.caseRecordId = caseRecordId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMappingPath() {
		return mappingPath;
	}

	public void setMappingPath(String mappingPath) {
		this.mappingPath = mappingPath;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	
	public String getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(String mediaCategory) {
		this.mediaCategory = mediaCategory;
	}

	public String getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
