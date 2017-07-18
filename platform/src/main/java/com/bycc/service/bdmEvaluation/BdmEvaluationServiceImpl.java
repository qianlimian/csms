package com.bycc.service.bdmEvaluation;

import com.bycc.dao.BdmEvaluationDao;
import com.bycc.dto.bdmEvaluation.BdmEvaluationDto;
import com.bycc.dto.bdmEvaluation.EvalTreeLeaf;
import com.bycc.entity.BdmEvaluation;
import com.bycc.enumitem.CaseScoreStandard;
import com.bycc.tools.NumberText;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BdmEvaluationServiceImpl implements BdmEvaluationService {
	@Autowired
	private BdmEvaluationDao dao;
	//转换序号(一、二....)
	private static NumberText nt = NumberText.getInstance(NumberText.Lang.ChineseSimplified);
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
	 * 查询ID下的所有评分细则
	 */	
	public List<BdmEvaluationDto> findAllById(Integer parent) {
		List<BdmEvaluationDto> dtos = new ArrayList<BdmEvaluationDto>();		
		BdmEvaluation root = dao.findOne(parent);
		if(root==null){				
			return dtos;
		}
		List<BdmEvaluation> leafs = dao.findByParent(parent,new Sort(new Order("displayOrder")));
		BdmEvaluationDto rootDto = BdmEvaluationDto.toDto(root);
		rootDto.setStandard(root.getStandard()+"（"+root.getScore()+"分）");
		List<EvalTreeLeaf> leafDtos = new ArrayList<EvalTreeLeaf>();
		int i=1;
		for (BdmEvaluation eval : leafs) {
			EvalTreeLeaf dto = EvalTreeLeaf.toDto(eval);
			dto.setStandard(i+"、 "+eval.getStandard()+"（"+eval.getScore()+"分）");
			List<EvalTreeLeaf> subDtos = findSubLeafs(eval.getId());
			if(subDtos.isEmpty()){
				dto.setHasChildren(false);					
			}
			dto.setTreeleafs(subDtos);
			leafDtos.add(dto);
			i++;
		}
		rootDto.setTreeleafs(leafDtos);
		dtos.add(rootDto);
		return dtos;
	}
	/**
	 * 递归遍历所有子节点	
	 */
	private List<EvalTreeLeaf> findSubLeafs(Integer parent) {
		int i=1;
		List<BdmEvaluation> subLeafs = dao.findByParent(parent,new Sort(new Order("displayOrder")));
		List<EvalTreeLeaf> leafDtos = new ArrayList<EvalTreeLeaf>();
		for (BdmEvaluation eval : subLeafs) {
			EvalTreeLeaf dto = EvalTreeLeaf.toDto(eval);
			dto.setStandard(i+") "+eval.getStandard()+"( "+eval.getScore()+" 分)");
			List<EvalTreeLeaf> subDtos = findSubLeafs(eval.getId());
			if(subDtos.isEmpty()){
				dto.setHasChildren(false);				
			}
			dto.setTreeleafs(subDtos);
			leafDtos.add(dto);
			i++;
		}
		return leafDtos;
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
	public void delete(Integer id) {
		//删除父项
		dao.delete(id);
		//删除子项
		dao.deleteByParent(id);					
	}

	/**
	 * 查询所有评价标准
	 */
	public List<BdmEvaluationDto> findAllScoreStandards() {
		List<BdmEvaluationDto> dtos = new ArrayList<BdmEvaluationDto>();
		List<BdmEvaluation> roots = dao.findByParent(null,new Sort(new Order("displayOrder")));	
		int i=1;
		for (BdmEvaluation root : roots) {			
			BdmEvaluationDto rootDto = BdmEvaluationDto.toDto(root);
			rootDto.setSeq(nt.getText(i));
			List<BdmEvaluation> leafs = dao.findByParent(root.getId(),new Sort(new Order("displayOrder")));
			List<EvalTreeLeaf> leafDtos = new ArrayList<EvalTreeLeaf>();
			int j=1;
			for (BdmEvaluation eval : leafs) {
				EvalTreeLeaf dto = EvalTreeLeaf.toDto(eval);
				dto.setSeq(j+"");
				List<EvalTreeLeaf> subDtos = findSubLeafs(eval.getId());
				if(subDtos.isEmpty()){
					dto.setHasChildren(false);					
				}
				dto.setTreeleafs(subDtos);
				leafDtos.add(dto);
				j++;
			}
			rootDto.setTreeleafs(leafDtos);			
			dtos.add(rootDto);	
			i++;
		}
		
		return dtos;
	}

	/**
	 * 查询所有评价大项
	 */
	public List<BdmEvaluationDto> findBigItems() {
		List<BdmEvaluationDto> dtos = new ArrayList<BdmEvaluationDto>();
		List<BdmEvaluation> bdmEvaluations = dao.findByParent(null,new Sort(new Order("displayOrder")));			
		for (int i=0;i<bdmEvaluations.size();i++) {
			BdmEvaluation entity=bdmEvaluations.get(i);
			BdmEvaluationDto dto=BdmEvaluationDto.toDto(entity);
			dto.setSeq(nt.getText(i+1));
			dtos.add(dto);
		}
		return dtos;
	}
}
