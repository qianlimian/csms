package com.bycc.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.bycc.demo.enumitem.Gender;

@Entity
@Table(name = "demo_teacher")
@TableGenerator(name = "com.bycc.demo.entity.Teacher", // TableGenerator的名字，下面的“generator”使用
		table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
		pkColumnName = "KEY_ID_", // 列1的字段名
		valueColumnName = "GEN_VALUE_", // 列2的字段名
		pkColumnValue = "com.bycc.demo.entity.Teacher", // 该表存在ID_GEN中列1的值
		initialValue = 1, // 初始值
		allocationSize = 50 // 增长率
)
//@NamedQuery(name = "com.bycc.demo.entity.Teacher.findAll", query = "select e from Teacher e")
public class Teacher implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.demo.entity.Teacher")
	private Integer id;

	/**
	 * 姓名
	 */
	@Column(name = "name_", length = 50)
	private String name;

	/**
	 * 性别
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "gender_")
	private Gender gender;

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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
