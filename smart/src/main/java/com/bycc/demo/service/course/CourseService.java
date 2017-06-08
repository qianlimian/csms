package com.bycc.demo.service.course;

import com.bycc.demo.dto.CourseDto;
import com.bycc.demo.dto.ScoreDto;

import org.smartframework.common.kendo.QueryBean;

import java.util.List;

public interface CourseService {

	/**
	 * 按条件查询课程列表
	 */
	List<CourseDto> query(QueryBean qb);

	/**
	 * 按id查询课程
	 */
	CourseDto findById(Integer id);

	/**
	 * 按id查询成绩
	 */
	List<ScoreDto> findSubsById(Integer id);

	/**
	 * 保存课程
	 */
	CourseDto save(CourseDto dto);
	
	/**
	 * 删除课程
	 */
	void delete(String ids);

}
