package com.bycc.dto;

import com.bycc.entity.BdmEvaluation;
import com.bycc.enumitem.CaseScoreStandard;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

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
     * 加减分项:加分，减分
     */
    private String scoreType;

    /**
     * 基础分值
     */
    private BigDecimal score;

    /**
     * 类别：主观，客观
     */
    private String evalType;

    /**
     * 备注
     */
    private String note;

    public static BdmEvaluationDto toDto(BdmEvaluation bdmEvaluation) {
        BdmEvaluationDto dto = new BdmEvaluationDto();
        dto.setId(bdmEvaluation.getId());
        dto.setStandard(bdmEvaluation.getStandard());
        dto.setScoreType(bdmEvaluation.getScoreType());
        dto.setScore(bdmEvaluation.getScore());
        dto.setEvalType(bdmEvaluation.getEvalType());
        dto.setNote(bdmEvaluation.getNote());
        return dto;
    }
    
    /**
     * 转换枚举    
     */
    public static BdmEvaluationDto toDto(CaseScoreStandard e){
        BdmEvaluationDto dto = new BdmEvaluationDto();
        String[] items= e.value();
        dto.setStandard(items[0]);
        dto.setScoreType(items[1]);
        dto.setEvalType(items[2]);        
        return dto;
    }
    
    
    public BdmEvaluation toEntity() {
    	BdmEvaluation entity =new BdmEvaluation();
    	entity.setInsertDate(new Date());
        return toEntity(entity);
    }

    public BdmEvaluation toEntity(BdmEvaluation bdmEvaluation) {
        bdmEvaluation.setStandard(this.getStandard());
        bdmEvaluation.setScoreType(this.getScoreType());
        bdmEvaluation.setScore(this.getScore());
        bdmEvaluation.setEvalType(this.getEvalType());
        bdmEvaluation.setNote(this.getNote());
        bdmEvaluation.setUpdateDate(new Date());
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

    public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public String getEvalType() {
		return evalType;
	}

	public void setEvalType(String evalType) {
		this.evalType = evalType;
	}

	public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
