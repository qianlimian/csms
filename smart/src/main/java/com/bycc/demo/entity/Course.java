package com.bycc.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "demo_course")
@TableGenerator(name = "com.bycc.demo.entity.Course", // TableGenerator的名字，下面的“generator”使用
		table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
		pkColumnName = "KEY_ID_", // 列1的字段名
		valueColumnName = "GEN_VALUE_", // 列2的字段名
		pkColumnValue = "com.bycc.demo.entity.Course", // 该表存在ID_GEN中列1的值
		initialValue = 1, // 初始值
		allocationSize = 50 // 增长率
)
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	//	-------------------------字段-------------------------
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.demo.entity.Course")
	private Integer id;

	/**
	 * 课程名
	 */
	@Column(name = "course_name_", length = 50)
	private String courseName;

	/**
	 * 年级
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_id_")
	private Grade grade;

	/**
	 * 教师
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id_")
	private Teacher teacher;

	/**
	 * 课程-(1:n)-成绩
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
	private List<Score> scores = new ArrayList<Score>();


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}
}
