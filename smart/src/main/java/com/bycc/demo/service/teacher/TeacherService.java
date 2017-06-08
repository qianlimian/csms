package com.bycc.demo.service.teacher;

import java.util.List;

import com.bycc.demo.dto.TeacherDto;
import org.smartframework.common.kendo.QueryBean;

public interface TeacherService {

	/**
	 * 按条件查询
	 */
	List<TeacherDto> query(QueryBean qb);

	/**
	 * 按id查询
	 */
	TeacherDto findById(Integer teacherId);

	/**
	 * 查询所有
	 */
	List<TeacherDto> findAll();

	/**
	 * 保存
	 */
	TeacherDto save(TeacherDto dto);

	/**
	 * 删除
	 */
	void delete(String ids);

}
