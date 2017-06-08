package com.bycc.demo.dto;

import com.bycc.demo.entity.Course;
import com.bycc.demo.entity.Score;
import com.bycc.demo.entity.Student;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.platform.validate.beans.GridDto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@DtoClass
public class ScoreDto extends GridDto {

    private Integer id;

    @NotNull(message="不能为空")
    private Integer studentId;
    private String studentName; //显示值

    @Digits(integer=3, fraction=1, message="数字精度有误")
    private BigDecimal mark;

    public static ScoreDto toDto(Score score) {
        ScoreDto dto = new ScoreDto();
        dto.setId(score.getId());
        dto.setStudentId(score.getStudent().getId());
        dto.setStudentName(score.getStudent().getName());
        dto.setMark(score.getMark());
        return dto;
    }

    public Score toEntity(Score score, Course course, Student student) {
        score.setCourse(course);
        score.setStudent(student);
        score.setMark(this.getMark());
        return score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public BigDecimal getMark() {
        return mark;
    }

    public void setMark(BigDecimal mark) {
        this.mark = mark;
    }

}
