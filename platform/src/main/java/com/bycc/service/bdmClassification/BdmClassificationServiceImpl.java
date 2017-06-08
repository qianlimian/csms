package com.bycc.service.bdmClassification;

import com.bycc.dao.BdmClassificationDao;



import com.bycc.entity.BdmClassification;

import com.bycc.dto.BdmClassificationDto;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dao.user.UserDao;
import org.smartframework.manager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2017/4/17.
 */
@Service
public class BdmClassificationServiceImpl implements BdmClassificationService {

    @Autowired
    private BdmClassificationDao dao;

    @Autowired
    private UserDao userDao;

    /**
     * 按条件查询
     */
    @Override
    public List<BdmClassificationDto> query(QueryBean qb) {
        List<BdmClassification> bdmClassifications = dao.findByQueryBean(qb);
        List<BdmClassificationDto> dtos = new ArrayList<BdmClassificationDto>();
        for (BdmClassification bdmClassification : bdmClassifications) {
            dtos.add(BdmClassificationDto.toDto(bdmClassification));
        }
        return dtos;
    }

    /**
     * 按id查找
     */
    @Override
    public BdmClassificationDto findById(Integer bdmClassificationId) {
        BdmClassification bdmClassification = dao.findOne(bdmClassificationId);
        return BdmClassificationDto.toDto(bdmClassification);
    }

    /**
     * 查询所有
     */
    @Override
    public List<BdmClassificationDto> findAll() {
        List<BdmClassification> bdmClassifications = dao.findAll();
        List<BdmClassificationDto> dtos = new ArrayList<BdmClassificationDto>();
        for (BdmClassification bdmClassification : bdmClassifications) {
            dtos.add(BdmClassificationDto.toDto(bdmClassification));
        }
        return dtos;
    }

    /**
     * 保存
     */
    @Override
    public BdmClassificationDto save(BdmClassificationDto dto){
        BdmClassification bdmClassification = null;
        if (dto.getId() == null) {
            bdmClassification = dto.toEntity();
        } else {
            bdmClassification = dao.findOne(dto.getId());
            if (bdmClassification != null) {
                dto.toEntity(bdmClassification);
            }
        }
        bdmClassification.setOperatorId(User.getCurrentUser().getId());
        dao.save(bdmClassification);
        return BdmClassificationDto.toDto(bdmClassification);
    }

    /**
     * 删除
     */
    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            BdmClassification bdmClassification = dao.findOne(Integer.valueOf(id));
            dao.delete(bdmClassification);
        }
    }
}
