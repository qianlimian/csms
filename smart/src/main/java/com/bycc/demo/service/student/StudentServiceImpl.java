package com.bycc.demo.service.student;

import java.util.ArrayList;
import java.util.List;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bycc.demo.dao.StudentDao;
import com.bycc.demo.dto.StudentDto;
import com.bycc.demo.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao dao;

    /**
     * 按条件查询
     */
    @Override
    public List<StudentDto> query(QueryBean qb) {
        //调用查询
        List<Student> students = dao.findByQueryBean(qb);
        List<StudentDto> dtos = new ArrayList<StudentDto>();
        for (Student student : students) {
            dtos.add(StudentDto.toDto(student));
        }
        return dtos;
    }

    /**
     * 按id查找
     */
    @Override
    public StudentDto findById(Integer id) {
        Student domain = dao.findOne(id);
        return StudentDto.toDto(domain);
    }

    /**
     * 查询所有
     */
    @Override
    public List<StudentDto> findAll() {
        List<Student> students = dao.findAll();
        List<StudentDto> dtos = new ArrayList<StudentDto>();
        for (Student student : students) {
            dtos.add(StudentDto.toDto(student));
        }
        return dtos;
    }

    /**
     * 保存
     * @throws BusinessException 
     */
    @Override
    public StudentDto save(StudentDto dto) throws Exception {
        Student student = null;
        if (dto.getId() == null) {
            student = dto.toEntity();
        } else {
            student = dao.findOne(dto.getId());
            if (student != null) {
                dto.toEntity(student);
            }
        }
        dao.save(student);
        return StudentDto.toDto(student);
    }

    /**
     * 删除
     */
    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            Student student = dao.findOne(Integer.valueOf(id));
            dao.delete(student);
        }
    }
}
