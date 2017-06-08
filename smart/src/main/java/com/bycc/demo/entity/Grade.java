package com.bycc.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "demo_grade")
@TableGenerator(name = "com.bycc.demo.entity.Grade", // TableGenerator的名字，下面的“generator”使用
		table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
		pkColumnName = "KEY_ID_", // 列1的字段名
		valueColumnName = "GEN_VALUE_", // 列2的字段名
		pkColumnValue = "com.bycc.demo.entity.Grade", // 该表存在ID_GEN中列1的值
		initialValue = 1, // 初始值
		allocationSize = 50 // 增长率
)
public class Grade implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.demo.entity.Grade")
	private Integer id;

	/**
	 * 班级名
	 */
	@Column(name = "grade_name_", length = 50)
	private String gradeName;

	/**
	 * 班主任
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id_")
	private Teacher teacher;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}
