package com.bycc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 案件积分
 */
@Entity
@Table(name = "case_score")
@TableGenerator(name = "com.bycc.entity.CaseScore", // TableGenerator的名字，下面的“generator”使用
        table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
        pkColumnName = "KEY_ID_", // 列1的字段名
        valueColumnName = "GEN_VALUE_", // 列2的字段名
        pkColumnValue = "com.bycc.entity.CaseScore", // 该表存在ID_GEN中列1的值
        initialValue = 1, // 初始值
        allocationSize = 1 // 增长率
)
public class CaseScore implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.CaseScore")
    private Integer id;

    /**
     * 得分
     */
    @Column(name = "total_score_")
    private BigDecimal totalScore;

    /**
     * 办案记录
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id_")
    private Case caze;

    /**
     * 积分项
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caseScore", fetch = FetchType.LAZY)
    private List<CaseScoreItem> caseScoreItems = new ArrayList<CaseScoreItem>();

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

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public Case getCaze() {
        return caze;
    }

    public void setCaze(Case caze) {
        this.caze = caze;
    }

    public List<CaseScoreItem> getCaseScoreItems() {
        return caseScoreItems;
    }

    public void setCaseScoreItems(List<CaseScoreItem> caseScoreItems) {
        this.caseScoreItems = caseScoreItems;
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
