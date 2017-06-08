package com.bycc.service.bdmVideoCg;

import com.bycc.dto.BdmVideoCgDto;
import org.smartframework.common.kendo.QueryBean;

import java.util.List;

public interface BdmVideoCgService {

    /**
     * 按条件查询
     */
    List<BdmVideoCgDto> query(QueryBean qb);

    /**
     * 按id查询
     */
    BdmVideoCgDto findById(Integer bdmClassificationId);

    /**
     * 查询所有
     */
    List<BdmVideoCgDto> findAll();

    /**
     * 保存
     */
    BdmVideoCgDto save(BdmVideoCgDto dto);

    /**
     * 删除
     */
    void delete(String ids);
}
