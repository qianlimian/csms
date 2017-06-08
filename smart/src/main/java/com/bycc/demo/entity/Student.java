package com.bycc.demo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "demo_student")
@TableGenerator(name = "com.bycc.demo.entity.Student", // TableGenerator的名字，下面的“generator”使用
		table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
		pkColumnName = "KEY_ID_", // 列1的字段名
		valueColumnName = "GEN_VALUE_", // 列2的字段名
		pkColumnValue = "com.bycc.demo.entity.Student", // 该表存在ID_GEN中列1的值
		initialValue = 1, // 初始值
		allocationSize = 50 // 增长率
)
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.demo.entity.Student")
	private Integer id;

	/**
	 * 姓名
	 */
	@Column(name = "name_", length = 50)
	private String name;

	/**
	 * 年龄
	 */
	@Column(name = "age_")
	private Integer age;

	/**
	 * 生日
	 */
	@Column(name = "birthday_")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date birthday;

	/**
	 * 住址
	 */
	@Column(name = "address_", length = 200)
	private String address;


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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
