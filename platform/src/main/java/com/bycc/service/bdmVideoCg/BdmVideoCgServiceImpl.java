package com.bycc.service.bdmVideoCg;

import com.bycc.dao.BdmVideoCgDao;
import com.bycc.dto.BdmVideoCgDto;
import com.bycc.entity.BdmVideoCategory;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BdmVideoCgServiceImpl implements BdmVideoCgService {

    @Autowired
    private BdmVideoCgDao dao;

    /**
     * 按条件查询
     */
    @Override
    public List<BdmVideoCgDto> query(QueryBean qb) {    	
        List<BdmVideoCategory> videoCgs = null;
        if(qb!=null){
        	videoCgs = dao.findByQueryBean(qb);
        }else{
        	videoCgs=dao.findAll();
        }
        List<BdmVideoCgDto> dtos = new ArrayList<BdmVideoCgDto>();
        for (BdmVideoCategory videoCg : videoCgs) {
            dtos.add(BdmVideoCgDto.toDto(videoCg));
        }
        return dtos;
    }

    /**
     * 按id查找
     */
    @Override
    public BdmVideoCgDto findById(Integer videoCgId) {
        BdmVideoCategory videoCg = dao.findOne(videoCgId);
        return BdmVideoCgDto.toDto(videoCg);
    }

    /**
     * 查询所有
     */
    @Override
    public List<BdmVideoCgDto> findAll() {
        List<BdmVideoCategory> videoCgs = dao.findAll();
        List<BdmVideoCgDto> dtos = new ArrayList<BdmVideoCgDto>();
        for (BdmVideoCategory videoCg : videoCgs) {
            dtos.add(BdmVideoCgDto.toDto(videoCg));
        }
        return dtos;
    }

    /**
     * 保存
     */
    @Override
    public BdmVideoCgDto save(BdmVideoCgDto dto){
        BdmVideoCategory videoCg = null;
        if (dto.getId() == null) {
            videoCg = dto.toEntity();
        } else {
            videoCg = dao.findOne(dto.getId());
            if (videoCg != null) {
                dto.toEntity(videoCg);
            }
        }
        dao.save(videoCg);
        return BdmVideoCgDto.toDto(videoCg);
    }

    /**
     * 删除
     */
    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            BdmVideoCategory videoCg = dao.findOne(Integer.valueOf(id));
            dao.delete(videoCg);
        }
    }
}
