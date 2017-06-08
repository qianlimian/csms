package com.bycc.demo.service.teacher;

import java.util.ArrayList;
import java.util.List;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bycc.demo.dao.TeacherDao;
import com.bycc.demo.dto.TeacherDto;
import com.bycc.demo.entity.Teacher;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao dao;

    /**
     * 按条件查询
     */
    @Override
    public List<TeacherDto> query(QueryBean qb) {
        List<Teacher> teachers = dao.findByQueryBean(qb);
        List<TeacherDto> dtos = new ArrayList<TeacherDto>();
        for (Teacher teacher : teachers) {
            dtos.add(TeacherDto.toDto(teacher));
        }
        return dtos;
    }

    /**
     * 按id查找
     */
    @Override
    public TeacherDto findById(Integer teacherId) {
        Teacher teacher = dao.findOne(teacherId);
        return TeacherDto.toDto(teacher);
    }

    /**
     * 查询所有
     */
    @Override
    public List<TeacherDto> findAll() {
        List<Teacher> teachers = dao.findAll();
        List<TeacherDto> dtos = new ArrayList<TeacherDto>();
        for (Teacher teacher : teachers) {
            dtos.add(TeacherDto.toDto(teacher));
        }
        return dtos;
    }

    /**
     * 保存
     */
    @Override
    public TeacherDto save(TeacherDto dto) {
        Teacher teacher = null;
        if (dto.getId() == null) {
            teacher = dto.toEntity(new Teacher());
        } else {
            teacher = dao.findOne(dto.getId());
            if (teacher != null) {
                dto.toEntity(teacher);
            }
        }
        dao.save(teacher);
        return TeacherDto.toDto(teacher);
    }

    /**
     * 删除
     */
    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            Teacher teacher = dao.findOne(Integer.valueOf(id));
            dao.delete(teacher);
        }
    }

}
