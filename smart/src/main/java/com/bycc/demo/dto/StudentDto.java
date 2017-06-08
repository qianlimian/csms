package com.bycc.demo.dto;

import java.text.ParseException;

import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;

import com.bycc.demo.entity.Student;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@DtoClass
public class StudentDto {

	private Integer id;

	@NotEmpty(message = "不能为空")
	@Size(max = 50, message = "不能超过{max}个字符")
	private String name;

	@Digits(integer=3, fraction=0, message="数字精度有误")
	private Integer age;

	private String birthday;

	@Size(max = 200, message = "不能超过{max}个字符")
	private String address;

	public static StudentDto toDto(Student student) {
		StudentDto dto = new StudentDto();
		dto.setId(student.getId());
		dto.setName(student.getName());
		dto.setAge(student.getAge());
		dto.setBirthday(student.getBirthday() != null ? DateHelper.formatDateToString(student.getBirthday(), "yyyy-MM-dd") : null);
		dto.setAddress(student.getAddress());
		return dto;
	}

	public Student toEntity() throws ParseException {
		Student student = new Student();
		return toEntity(student);
	}

	public Student toEntity(Student student) throws ParseException {
		student.setAddress(this.address);
		student.setAge(this.age);
		student.setName(this.name);
		student.setBirthday(DateHelper.formatStringToDate(this.birthday, "yyyy-MM-dd"));
		return student;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
