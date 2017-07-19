/**
 * 
 */
package com.bycc.dto.bdmEvaluation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.bycc.entity.BdmEvaluation;

/**
 * @description
 * @author gaoningbo
 * @date 2017年6月20日
 * 
 */
public class EvalTreeLeaf {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 评价标准
	 */
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
	 * 序列
	 */
	private String seq;
	/**
	 * 是否有子节点
	 */
	private boolean hasChildren=true;
	/**
	 * 默认展开显示
	 */
	private boolean expanded= true;
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 子节点	
	 */
	private List<EvalTreeLeaf> treeleafs=new ArrayList<>();
	
	public static EvalTreeLeaf toDto(BdmEvaluation entity){		
		EvalTreeLeaf dto = new EvalTreeLeaf();
		dto.setId(entity.getId());
		dto.setStandard(entity.getStandard());
		dto.setScore(entity.getScore());
		dto.setNote(entity.getNote());
		dto.setParent(entity.getParent());		
		return dto;
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

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
}
