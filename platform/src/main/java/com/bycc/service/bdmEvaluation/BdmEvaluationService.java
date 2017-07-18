package com.bycc.service.bdmEvaluation;

import org.smartframework.common.kendo.QueryBean;

import com.bycc.dto.bdmEvaluation.BdmEvaluationDto;

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
     * 查询所有评分细则
     */
    List<BdmEvaluationDto> findAllById(Integer parent);
    /**
     * 查询所有大项    
     */
    List<BdmEvaluationDto> findBigItems();
    /**
     * 保存
     */
    BdmEvaluationDto save(BdmEvaluationDto dto);
    /**
     * 删除
     */
    void delete(Integer id);
    /**
     * 查询所有评价标准   
     */
    List<BdmEvaluationDto> findAllScoreStandards();
}
