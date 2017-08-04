package com.bycc.service.lawyer;


import com.bycc.dto.LawyerDto;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Created by mt.W on 2017/7/11.
 */
public interface LawyerService {
    /**
     * 删除律师
     */
    void deleteLawyerById(String ids);

    /**
     * 根据ID查找律师
     */
    LawyerDto  findById(Integer id);

    /**
     * 根据查询条件
     */
    List<LawyerDto> query(QueryBean qb);

    /**
     * 查询所有律师
     */
    List<LawyerDto> findAll();
    /**
     * 保存律师
     */
    LawyerDto save(LawyerDto dto)throws Exception;

    /**
     * 上传律师
     */
    public int importExcel(MultipartFile file)throws Exception;

    /**
     * 查找所有律师分页显示
     * @param qb
     * @return
     */
    List<LawyerDto> findAllPage(QueryBean qb);
}
