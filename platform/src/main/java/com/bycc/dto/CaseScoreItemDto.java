/**
 * 
 */
package com.bycc.dto;

import java.math.BigDecimal;
import java.util.Date;
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
	private BigDecimal score;

	// 评价标准
	private Integer evalId;
	private String evalType;
	private String standard;

	public static CaseScoreItemDto toDto(CaseScoreItem item) {
		CaseScoreItemDto dto = new CaseScoreItemDto();
		dto.setId(item.getId());
		dto.setCaseScoreId(item.getCaseScore() != null ? item.getCaseScore().getId() : null);
		dto.setScore(item.getScore());
		if (item.getEval() != null) {
			dto.setEvalId(item.getEval().getId());
            dto.setEvalType(item.getEval().getEvalType());
			dto.setStandard(item.getEval().getStandard());
		}
		return dto;
	}

	public static CaseScoreItemDto toDto(BdmEvaluation eval) {
		CaseScoreItemDto dto = new CaseScoreItemDto();
		if (eval != null) {
			dto.setEvalId(eval.getId());
			dto.setEvalType(eval.getEvalType());
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

    public String getEvalType() {
        return evalType;
    }

    public void setEvalType(String evalType) {
        this.evalType = evalType;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }
}
