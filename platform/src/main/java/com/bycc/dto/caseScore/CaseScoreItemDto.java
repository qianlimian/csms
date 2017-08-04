/**
 * 
 */
package com.bycc.dto.caseScore;

import java.math.BigDecimal;
import java.util.Date;

import org.smartframework.manager.entity.User;

import com.bycc.entity.BdmEvaluation;
import com.bycc.entity.CaseScoreItem;

/**
 * @description
 * @author gaoningbo
 * @date 2017年4月25日
 * 
 */
public class CaseScoreItemDto {

	private Integer id;
	
    private Integer caseScoreId;   

	// 评价标准ID
	private Integer evalId;
	// 评价标准
	private String standard;
	//积分
	private BigDecimal score;
	//取消选中
	private boolean selected;
	
	
	public static CaseScoreItemDto toDto(CaseScoreItem item, BdmEvaluation parent, BdmEvaluation root) {
		CaseScoreItemDto dto = new CaseScoreItemDto();
		dto.setId(item.getId());
		dto.setEvalId(item.getEval()!=null?item.getEval().getId():null);
		dto.setCaseScoreId(item.getCaseScore() != null ? item.getCaseScore().getId() : null);
		dto.setScore(item.getScore());
		String standard = "";
		if (root != null) {
			standard = standard + root.getStandard() + "-";
		}
		if (parent != null) {
			standard = standard + parent.getStandard() + "-";
		}
		BdmEvaluation evaluation = item.getEval();
		if (evaluation != null) {
			standard = standard + evaluation.getStandard();
		}
		dto.setStandard(standard);
		return dto;
	}
	
	public static CaseScoreItemDto toDto(CaseScoreItem item) {
		CaseScoreItemDto dto = new CaseScoreItemDto();
		dto.setId(item.getId());
		dto.setEvalId(item.getEval()!=null?item.getEval().getId():null);
		dto.setCaseScoreId(item.getCaseScore() != null ? item.getCaseScore().getId() : null);
		dto.setScore(item.getScore());
		dto.setStandard(item.getEval()!=null?item.getEval().getStandard():null);
		return dto;
	}

	public static CaseScoreItemDto toDto(BdmEvaluation eval) {
		CaseScoreItemDto dto = new CaseScoreItemDto();
		if (eval != null) {
			dto.setEvalId(eval.getId());
            dto.setStandard(eval.getStandard());
			dto.setScore(eval.getScore());
		}
		return dto;
	}

	public CaseScoreItem toEntity() {
		CaseScoreItem item = new CaseScoreItem();
		item.setInsertDate(new Date());
		return toEntity(item);
	}

	public CaseScoreItem toEntity(CaseScoreItem item) {
		item.setUpdateDate(new Date());
		item.setScore(this.getScore() == null ? BigDecimal.ZERO : this.getScore());
        item.setOperatorId(User.getCurrentUser().getId());
		return item;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseScoreId() {
        return caseScoreId;
    }

    public void setCaseScoreId(Integer caseScoreId) {
        this.caseScoreId = caseScoreId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getEvalId() {
        return evalId;
    }

    public void setEvalId(Integer evalId) {
        this.evalId = evalId;
    }
    
    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
    
}
