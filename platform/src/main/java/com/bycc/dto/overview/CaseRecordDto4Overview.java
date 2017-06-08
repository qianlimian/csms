package com.bycc.dto.overview;

import java.util.List;

import com.bycc.dto.caseRegister.CasePeopleDto;

public class CaseRecordDto4Overview {
	private Integer id;
	private String name;
	private List<CasePeopleDto> peopleDtos;
	
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
	public List<CasePeopleDto> getPeopleDtos() {
		return peopleDtos;
	}
	public void setPeopleDtos(List<CasePeopleDto> peopleDtos) {
		this.peopleDtos = peopleDtos;
	}
	
}
