package com.bycc.demo.service.grade;

import com.bycc.demo.dao.GradeDao;
import com.bycc.demo.dao.TeacherDao;
import com.bycc.demo.dto.GradeDto;
import com.bycc.demo.entity.Grade;
import com.bycc.demo.entity.Teacher;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService{

    @Autowired
    private GradeDao dao;

    @Autowired
    private TeacherDao teacherDao;

    /**
     * 按条件查询
     */
    @Override
    public List<GradeDto> query(QueryBean qb) {
        List<Grade> grades = dao.findByQueryBean(qb);
        List<GradeDto> dtos = new ArrayList<GradeDto>();
        for (Grade grade : grades) {
            dtos.add(GradeDto.toDto(grade));
        }
        return dtos;
    }

    /**
     * 按id查找
     */
    @Override
    public GradeDto findById(Integer id) {
        Grade domain = dao.findOne(id);
        return GradeDto.toDto(domain);
    }

    /**
     * 保存
     */
    @Override
    public GradeDto save(GradeDto dto) {

        Teacher teacher = null;
        if (dto.getTeacherId() != null) {
            teacher = teacherDao.findOne(dto.getTeacherId());
        }

        Grade grade = null;
        if (dto.getId() == null) {
            grade = dto.toEntity(teacher);
        } else {
            grade = dao.findOne(dto.getId());
            if (grade != null) {
                dto.toEntity(grade, teacher);
            }
        }
        dao.save(grade);
        return GradeDto.toDto(grade);
    }

    /**
     * 删除
     */
    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            Grade grade = dao.findOne(Integer.valueOf(id));
            dao.delete(grade);
        }
    }

    /**
     * 按过滤条件查询 --for searchBox
     */
    @Override
    public List<GradeDto> findByQueryBean(QueryBean queryBean) {
        List<GradeDto> dtos = new ArrayList<GradeDto>();
        List<Grade> list = dao.findByQueryBean(queryBean);
        for (Grade grade : list) {
            dtos.add(GradeDto.toDto(grade));
        }
        return dtos;
    }
}
