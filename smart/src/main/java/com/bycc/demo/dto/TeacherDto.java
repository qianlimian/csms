package com.bycc.demo.dto;

import com.bycc.demo.entity.Teacher;
import com.bycc.demo.enumitem.Gender;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;

import javax.validation.constraints.Size;

@DtoClass
public class TeacherDto {
	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 姓名
	 */
	@NotEmpty(message = "不能为空")
	@Size(max = 50, message = "不能超过{max}个字符")
	private String name;

	/**
	 * 性别
	 */
	private String gender;

	public static TeacherDto toDto(Teacher teacher) {
		TeacherDto dto = new TeacherDto();
		dto.setId(teacher.getId());
		dto.setName(teacher.getName());
		dto.setGender(null != teacher.getGender() ? teacher.getGender().key() : null);
		return dto;
	}

	public Teacher toEntity() {
		return toEntity(new Teacher());
	}

	public Teacher toEntity(Teacher teacher) {
		teacher.setName(this.getName());
		teacher.setGender(Gender.getMatchByKey(this.getGender()));
		return teacher;
	}

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
