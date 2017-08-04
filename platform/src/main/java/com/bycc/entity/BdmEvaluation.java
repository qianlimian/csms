package com.bycc.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 积分评价标准
 */
@Entity
@Table(name = "bdm_evaluation")
@TableGenerator(name = "com.bycc.entity.BdmEvaluation", // TableGenerator的名字，下面的“generator”使用
        table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
        pkColumnName = "KEY_ID_", // 列1的字段名
        valueColumnName = "GEN_VALUE_", // 列2的字段名
        pkColumnValue = "com.bycc.entity.BdmEvaluation", // 该表存在ID_GEN中列1的值
        initialValue = 1, // 初始值
        allocationSize = 1 // 增长率
)
public class BdmEvaluation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.BdmEvaluation")
    private Integer id;

    /**
     * 评价标准
     */
    @Column(name = "standard_")
    private String standard;

    /**
     * 基础分值
     */
    @Column(name = "score_")
    private BigDecimal score;    

    /**
     * 备注
     */
    @Column(name = "note_")
    private String note;
    
    /**
     * 父ID
     */
    @Column(name = "parent_")
    private Integer parent;
  
    /**
     * 排序
     */
    @Column(name = "displayOrder_")
    private Integer displayOrder;
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

    public BdmEvaluation() {}
    //构造函数--构造测试数据用
    public BdmEvaluation(String standard, Integer parent, BigDecimal score) {
        this.standard = standard;
        this.parent = parent;
        this.score = score;
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

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
    
}
