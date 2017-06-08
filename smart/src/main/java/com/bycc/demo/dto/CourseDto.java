package com.bycc.demo.dto;

import com.bycc.demo.entity.Course;
import com.bycc.demo.entity.Grade;
import com.bycc.demo.entity.Teacher;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@DtoClass
public class CourseDto {

    private Integer id;

    @NotEmpty(message = "不能为空")
    @Size(max = 50, message = "不能超过{max}个字符")
    private String courseName;

    @NotNull(message="不能为空")
    private Integer gradeId;
    private String gradeName; //显示值

    @NotNull(message="不能为空")
    private Integer teacherId;
    private String teacherName; //显示值

    /**
     * 课程新增列表
     */
    private List<ScoreDto> news = new ArrayList<ScoreDto>();
    /**
     * 课程编辑列表
     */
    private List<ScoreDto> updates = new ArrayList<ScoreDto>();
    /**
     * 课程删除列表
     */
    private List<Integer> deletes = new ArrayList<Integer>();


    public static CourseDto toDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setCourseName(course.getCourseName());
        dto.setGradeId(course.getGrade().getId());
        dto.setGradeName(course.getGrade().getGradeName());
        dto.setTeacherId(course.getTeacher().getId());
        dto.setTeacherName(course.getTeacher().getName());
        return dto;
    }

    public Course toEntity(Course course, Grade grade, Teacher teacher) {
        course.setCourseName(this.getCourseName());
        course.setGrade(grade);
        course.setTeacher(teacher);
        return course;
    }


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

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<ScoreDto> getNews() {
        return news;
    }

    public void setNews(List<ScoreDto> news) {
        this.news = news;
    }

    public List<ScoreDto> getUpdates() {
        return updates;
    }

    public void setUpdates(List<ScoreDto> updates) {
        this.updates = updates;
    }

    public List<Integer> getDeletes() {
        return deletes;
    }

    public void setDeletes(List<Integer> deletes) {
        this.deletes = deletes;
    }

}
