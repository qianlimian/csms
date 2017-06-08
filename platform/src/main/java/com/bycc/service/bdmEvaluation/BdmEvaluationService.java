package com.bycc.service.bdmEvaluation;

import com.bycc.dto.BdmEvaluationDto;
import org.smartframework.common.kendo.QueryBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
public interface BdmEvaluationService {

    /**
     * 按条件查询
     */
    List<BdmEvaluationDto> query(QueryBean qd);
    /**
     * 按id查询
     */
    BdmEvaluationDto findById(Integer id);
    /**
     * 查询所有
     */
    List<BdmEvaluationDto> findAll();
    /**
     * 保存
     */
    BdmEvaluationDto save(BdmEvaluationDto dto);
    /**
     * 删除
     */
    void delete(String ids);
    /**
     * 查询所有评价标准   
     */
    List<BdmEvaluationDto> findAllScoreStandards();
}
