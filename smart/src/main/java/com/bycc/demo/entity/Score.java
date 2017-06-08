package com.bycc.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "demo_score")
@TableGenerator(name = "com.bycc.demo.entity.Score", // TableGenerator的名字，下面的“generator”使用
		table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
		pkColumnName = "KEY_ID_", // 列1的字段名
		valueColumnName = "GEN_VALUE_", // 列2的字段名
		pkColumnValue = "com.bycc.demo.entity.Score", // 该表存在ID_GEN中列1的值
		initialValue = 1, // 初始值
		allocationSize = 50 // 增长率
)
public class Score implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.demo.entity.Score")
	private Integer id;

	/**
	 * 课程-(1:n)-成绩
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id_")
	private Course course;

	/**
	 * 学生
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id_")
	private Student student;

	/**
	 * 成绩
	 */
	@Column(name = "mark_", precision = 4, scale = 1)
	private BigDecimal mark;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public BigDecimal getMark() {
		return mark;
	}

	public void setMark(BigDecimal mark) {
		this.mark = mark;
	}
}
