package com.bycc.dto.bdmEvaluation;

import com.bycc.entity.BdmEvaluation;
import com.bycc.enumitem.CaseScoreStandard;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19 0019.
 */
public class BdmEvaluationDto {
	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 评价标准
	 */
	@NotEmpty(message = "不能为空")
	@Size(max = 50, message = "不能超过{max}个字符")
	private String standard;

	/**
	 * 基础分值
	 */
	private BigDecimal score;

	/**
	 * 父ID
	 */
	private Integer parent;

	/**
	 * 备注
	 */
	private String note;
	/**
	 * 序号
	 */
	private String seq;
	/**
	 * 排序
	 */
	private Integer displayOrder;
	/**
	 * 子节点	
	 */
	private List<EvalTreeLeaf> treeleafs=new ArrayList<>();
	
	
	public static BdmEvaluationDto toDto(BdmEvaluation bdmEvaluation) {
		BdmEvaluationDto dto = new BdmEvaluationDto();
		dto.setId(bdmEvaluation.getId());
		dto.setStandard(bdmEvaluation.getStandard());
		dto.setScore(bdmEvaluation.getScore());
		dto.setNote(bdmEvaluation.getNote());
		dto.setParent(bdmEvaluation.getParent());
		dto.setDisplayOrder(bdmEvaluation.getDisplayOrder());
		return dto;
	}

	public BdmEvaluation toEntity() {
		BdmEvaluation entity = new BdmEvaluation();
		entity.setInsertDate(new Date());
		return toEntity(entity);
	}

	public BdmEvaluation toEntity(BdmEvaluation bdmEvaluation) {
		bdmEvaluation.setStandard(this.getStandard());
		bdmEvaluation.setScore(this.getScore());
		bdmEvaluation.setNote(this.getNote());
		bdmEvaluation.setUpdateDate(new Date());
		bdmEvaluation.setParent(this.parent);
		bdmEvaluation.setDisplayOrder(this.displayOrder);
		return bdmEvaluation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<EvalTreeLeaf> getTreeleafs() {
		return treeleafs;
	}

	public void setTreeleafs(List<EvalTreeLeaf> treeleafs) {
		this.treeleafs = treeleafs;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}	
	
}
