package com.bycc.dto.overview;

import java.util.List;

public class PoliceStationDto4Overview {
	//办案区ID
	private Integer id;
	//派出所名称
	private String name;
	//办案记录列表
	private List<CaseRecordDto4Overview> recordDtos;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CaseRecordDto4Overview> getRecordDtos() {
		return recordDtos;
	}
	public void setRecordDtos(List<CaseRecordDto4Overview> recordDtos) {
		this.recordDtos = recordDtos;
	}
}
