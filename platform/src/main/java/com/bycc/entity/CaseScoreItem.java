package com.bycc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 案件积分项
 */
@Entity
@Table(name = "case_score_item")
@TableGenerator(name = "com.bycc.entity.CaseScoreItem", // TableGenerator的名字，下面的“generator”使用
        table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
        pkColumnName = "KEY_ID_", // 列1的字段名
        valueColumnName = "GEN_VALUE_", // 列2的字段名
        pkColumnValue = "com.bycc.entity.CaseScoreItem", // 该表存在ID_GEN中列1的值
        initialValue = 1, // 初始值
        allocationSize = 1 // 增长率
)
public class CaseScoreItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.CaseScoreItem")
    private Integer id;

    /**
     * 评价项
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eval_id_")
    private BdmEvaluation eval;

    /**
     * 得分
     */
    @Column(name = "score_")
    private BigDecimal score;

    /**
     * 案件积分
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_score_id_")
    private CaseScore caseScore;

    /**
     * 备注
     */
    @Column(name = "note_")
    private String note;

    /**
     * 操作人
     */
    @Column(name = "operator_id_")
    private Integer operatorId;

    /**
     * 插入日期
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "insert_date_")
    private Date insertDate;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date_")
    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BdmEvaluation getEval() {
        return eval;
    }

    public void setEval(BdmEvaluation eval) {
        this.eval = eval;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public CaseScore getCaseScore() {
        return caseScore;
    }

    public void setCaseScore(CaseScore caseScore) {
        this.caseScore = caseScore;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
