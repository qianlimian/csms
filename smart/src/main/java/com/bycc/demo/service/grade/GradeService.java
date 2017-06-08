package com.bycc.demo.service.grade;

import com.bycc.demo.dto.GradeDto;
import org.smartframework.common.kendo.QueryBean;

import java.util.List;

public interface GradeService {

    List<GradeDto> query(QueryBean qb);

    GradeDto findById(Integer id);

    GradeDto save(GradeDto dto);

    void delete(String ids);

    List<GradeDto> findByQueryBean(QueryBean queryBean);
}
