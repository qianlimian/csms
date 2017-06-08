package com.bycc.demo.service.course;

import com.bycc.demo.dao.CourseDao;
import com.bycc.demo.dao.GradeDao;
import com.bycc.demo.dao.ScoreDao;
import com.bycc.demo.dao.StudentDao;
import com.bycc.demo.dao.TeacherDao;
import com.bycc.demo.dto.CourseDto;
import com.bycc.demo.dto.ScoreDto;
import com.bycc.demo.entity.*;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.utils.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private ScoreDao scoreDao;

	@Autowired
	private GradeDao gradeDao;

	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private StudentDao studentDao;

	/**
	 * 按条件查询课程列表
	 */
	@Override
	public List<CourseDto> query(QueryBean qb) {
		List<Course> courses = courseDao.findByQueryBean(qb);
		List<CourseDto> dtos = new ArrayList<CourseDto>();
		for (Course course : courses) {
			dtos.add(CourseDto.toDto(course));
		}
		return dtos;
	}

	/**
	 * 按id查询课程
	 */
	@Override
	public CourseDto findById(Integer id) {
		Course course = courseDao.findOne(id);
		return CourseDto.toDto(course);
	}

	/**
	 * 按id查询成绩
	 */
	@Override
	public List<ScoreDto> findSubsById(Integer id) {
		List<ScoreDto> dtos = new ArrayList<ScoreDto>();
		Course course = courseDao.findOne(id);
		for (Score score : course.getScores()) {
			dtos.add(ScoreDto.toDto(score));
		}
		return dtos;
	}

	/**
	 * 保存课程
	 */
	@Override
	public CourseDto save(CourseDto dto) {
		System.out.println(JsonHelper.getJson(dto));

		//references
		Grade grade = gradeDao.findOne(dto.getGradeId());
		Teacher teacher = teacherDao.findOne(dto.getTeacherId());

		// 保存主表
		Course course = null;
		if (dto.getId() == null) {
			course = dto.toEntity(new Course(), grade, teacher);
		} else {
			course = courseDao.findOne(dto.getId());
			if (course != null) {
				dto.toEntity(course, grade, teacher);
			}
		}

		// 保存子表
		// 删除
		for (Integer scoreId : dto.getDeletes()) {
			scoreDao.delete(scoreId);
		}
		// 更新
		for (ScoreDto scoreDto : dto.getUpdates()) {
			//reference
			Student student = studentDao.findOne(scoreDto.getStudentId());

			Score score = scoreDao.findOne(scoreDto.getId());
			scoreDto.toEntity(score, course, student);
			scoreDao.save(score);
		}
		// 新增
		for (ScoreDto scoreDto : dto.getNews()) {
			//reference
			Student student = studentDao.findOne(scoreDto.getStudentId());

			Score score = new Score();
			scoreDto.toEntity(score, course, student);
			scoreDao.save(score);
		}

		courseDao.save(course);
		return CourseDto.toDto(course);
	}

	/**
	 * 删除课程
	 */
	@Override
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			Course course = courseDao.findOne(Integer.valueOf(id));
			courseDao.delete(course);
		}
	}
	
}
