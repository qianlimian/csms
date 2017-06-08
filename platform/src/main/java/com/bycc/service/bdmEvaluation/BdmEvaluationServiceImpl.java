package com.bycc.service.bdmEvaluation;

import com.bycc.dao.BdmEvaluationDao;
import com.bycc.dto.BdmEvaluationDto;
import com.bycc.entity.BdmEvaluation;
import com.bycc.enumitem.CaseScoreStandard;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BdmEvaluationServiceImpl implements BdmEvaluationService{
    @Autowired
    private BdmEvaluationDao dao;

    /**
     * 按条件查询
     */
    @Override
    public List<BdmEvaluationDto> query(QueryBean qb) {
        List<BdmEvaluation> bdmEvaluations = dao.findByQueryBean(qb);
        List<BdmEvaluationDto> dtos = new ArrayList<BdmEvaluationDto>();
        for (BdmEvaluation bdmEvaluation : bdmEvaluations) {
            dtos.add(BdmEvaluationDto.toDto(bdmEvaluation));
        }
        return dtos;
    }

    /**
     * 按id查找
     */
    @Override
    public BdmEvaluationDto findById(Integer bdmEvaluationId) {
        BdmEvaluation bdmEvaluation = dao.findOne(bdmEvaluationId);
        return BdmEvaluationDto.toDto(bdmEvaluation);
    }

    /**
     * 查询所有
     */
    @Override
    public List<BdmEvaluationDto> findAll() {
        List<BdmEvaluation> bdmEvaluations = dao.findAll();
        List<BdmEvaluationDto> dtos = new ArrayList<BdmEvaluationDto>();
        for (BdmEvaluation bdmEvaluation : bdmEvaluations) {
            dtos.add(BdmEvaluationDto.toDto(bdmEvaluation));
        }
        return dtos;
    }

    /**
     * 保存
     */
    @Override
    public BdmEvaluationDto save(BdmEvaluationDto dto) {
        BdmEvaluation bdmEvaluation = null;
        if (dto.getId() == null) {
            bdmEvaluation = dto.toEntity();
        } else {
            bdmEvaluation = dao.findOne(dto.getId());
            if (bdmEvaluation != null) {
                dto.toEntity(bdmEvaluation);
            }
        }
        bdmEvaluation.setOperatorId(User.getCurrentUser().getId());
        dao.save(bdmEvaluation);
        return BdmEvaluationDto.toDto(bdmEvaluation);
    }

    /**
     * 删除
     */
    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            BdmEvaluation bdmEvaluation = dao.findOne(Integer.valueOf(id));
            dao.delete(bdmEvaluation);
        }
    }

	
    /**
     * 查询所有评价标准
     */
	public List<BdmEvaluationDto> findAllScoreStandards() {
		List<BdmEvaluationDto> dtos=new ArrayList<>();
		for (CaseScoreStandard e : CaseScoreStandard.values()) {
			BdmEvaluationDto dto=BdmEvaluationDto.toDto(e);
			dtos.add(dto);
		}
		return dtos;
	}
}
