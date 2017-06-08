package com.bycc.service.bdmClassification;

import com.bycc.dto.BdmClassificationDto;

import org.smartframework.common.kendo.QueryBean;

import java.util.List;

/**
 * Created by PC on 2017/4/17.
 */
public interface BdmClassificationService {

    /**
     * 按条件查询
     */
    List<BdmClassificationDto> query(QueryBean qb);

    /**
     * 按id查询
     */
    BdmClassificationDto findById(Integer bdmClassificationId);

    /**
     * 查询所有
     */
    List<BdmClassificationDto> findAll();

    /**
     * 保存
     */
    BdmClassificationDto save(BdmClassificationDto dto);

    /**
     * 删除
     */
    void delete(String ids);
}
