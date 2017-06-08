package com.bycc.demo.service.student;

import java.util.List;
import org.smartframework.common.kendo.QueryBean;

import com.bycc.demo.dto.StudentDto;

public interface StudentService {

	/**
	 * 按条件查询
	 */
	List<StudentDto> query(QueryBean qb);

	/**
	 * 按id查找
	 */
	StudentDto findById(Integer id);

	/**
	 * 查询所有
	 */
	List<StudentDto> findAll();

	/**
	 * 保存
	 */
	StudentDto save(StudentDto dto) throws Exception;

	/**
	 * 删除
	 */
	void delete(String ids);
}
