package com.bycc.dto;

import com.bycc.entity.BdmClassification;
import com.bycc.enumitem.CaseType;
import com.bycc.enumitem.RiskLevel;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;

import javax.validation.constraints.Size;
import java.util.Date;

@DtoClass
public class BdmClassificationDto {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 案件类型
     */
    @NotEmpty(message = "不能为空")
    @Size(max = 50, message = "不能超过{max}个字符")
    private String caseType;

    /**
     * 关键词
     */
    @NotEmpty(message = "不能为空")
    @Size(max = 50, message = "不能超过{max}个字符")
    private String keyWord;

    /**
     * 风险等级
     */
    @NotEmpty(message = "不能为空")
    @Size(max = 50, message = "不能超过{max}个字符")
    private String riskLevel;

    /**
     * 操作人
     */
    private Integer operatorId;

    /**
     * 备注
     */

    private String note;

    /**
     * 插入日期
     */
    private String insertDate;

    /**
     * 更新时间
     */
    private String updateDate;

    public static BdmClassificationDto toDto(BdmClassification entity) {
        BdmClassificationDto dto = new BdmClassificationDto();
        dto.setId(entity.getId());
        dto.setCaseType(null != entity.getCaseType() ? entity.getCaseType().key() : null);
        dto.setKeyWord(entity.getKeyWord());
        dto.setRiskLevel(null != entity.getRiskLevel() ? entity.getRiskLevel().key() : null);
        dto.setOperatorId(entity.getOperatorId());
        dto.setNote(entity.getNote());
        dto.setInsertDate(DateHelper.formatDateToString(entity.getInsertDate(),"yyyy-MM-dd HH:mm:ss"));
        dto.setUpdateDate(DateHelper.formatDateToString(entity.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
        return dto;
    }
    public BdmClassification toEntity() {
        BdmClassification entity = new BdmClassification();
        entity.setInsertDate(new Date());
        return toEntity(entity);
    }
    public BdmClassification toEntity(BdmClassification entity) {
        entity.setKeyWord(this.getKeyWord());
        entity.setCaseType(CaseType.getMatchByKey(this.getCaseType()));
        entity.setOperatorId(this.getOperatorId());
        entity.setRiskLevel(RiskLevel.getMatchByKey(this.getRiskLevel()));
        entity.setNote(this.getNote());
        entity.setUpdateDate(new Date());
        return entity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}

