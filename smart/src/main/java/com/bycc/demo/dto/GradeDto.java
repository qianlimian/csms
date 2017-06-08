package com.bycc.demo.dto;

import com.bycc.demo.entity.Grade;
import com.bycc.demo.entity.Teacher;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@DtoClass
public class GradeDto {

    private Integer id;

    @NotEmpty(message = "不能为空")
    @Size(max = 50, message = "不能超过{max}个字符")
    private String gradeName;

    @NotNull(message = "不能为空")
    private Integer teacherId;
    private String teacherName;

    public static GradeDto toDto(Grade grade) {
        GradeDto dto = new GradeDto();
        dto.setId(grade.getId());
        dto.setGradeName(grade.getGradeName());
        dto.setTeacherId(null != grade.getTeacher() ? grade.getTeacher().getId() : null);
        dto.setTeacherName(null != grade.getTeacher() ? grade.getTeacher().getName() : "");
        return dto;
    }

    public Grade toEntity(Teacher teacher) {
        Grade grade = new Grade();
        return toEntity(grade, teacher);
    }

    public Grade toEntity(Grade grade, Teacher teacher) {
        grade.setGradeName(this.getGradeName());
        grade.setTeacher(teacher);
        return grade;
    }

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

}
