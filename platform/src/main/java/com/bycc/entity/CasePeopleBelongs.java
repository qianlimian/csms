package com.bycc.entity;

import com.bycc.enumitem.StoreType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gaoningbo
 * @description 涉案财物
 * @date 2017年4月10日
 */
@Entity
@Table(name = "case_people_belongs")
@TableGenerator(
        name = "com.bycc.entity.CasePeopleBelongs",
        table = "ID_Sequence",
        pkColumnName = "KEY_ID_",
        valueColumnName = "GEN_VALUE_",
        pkColumnValue = "com.bycc.entity.CasePeopleBelongs",
        initialValue = 1,
        allocationSize = 1
)
public class CasePeopleBelongs implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.CasePeopleBelongs")
    private Integer id;

    /**
     * 财物名称
     */
    @Column(name = "name_")
    private String name;

    /**
     * 物品特征
     */
    @Column(name = "description_")
    private String description;

    /**
     * 数量
     */
    @Column(name = "count_")
    private Integer count;

    /**
     * 单位
     */
    @Column(name = "unit_")
    private String unit;

    /**
     * 保管措施
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "store_type_")
    private StoreType storeType;

    /**
     * 物品柜
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cabinet_id_")
    private BdmCabinet cabinet;

    /**
     * 是否涉案
     */
    @Column(name = "involved_or_not_")
    private Boolean involvedOrNot;

    /**
     * 是否归还
     */
    @Column(name = "back_or_not_")
    private Boolean backOrNot;

    /**
     * 归还时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "back_date_")
    private Date backDate;

    /**
     * 涉案人员
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_people_id_")
    private CasePeople casePeople;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    public BdmCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(BdmCabinet cabinet) {
        this.cabinet = cabinet;
    }

    public Boolean getInvolvedOrNot() {
        return involvedOrNot;
    }

    public void setInvolvedOrNot(Boolean involvedOrNot) {
        this.involvedOrNot = involvedOrNot;
    }

    public Boolean getBackOrNot() {
        return backOrNot;
    }

    public void setBackOrNot(Boolean backOrNot) {
        this.backOrNot = backOrNot;
    }

    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    public CasePeople getCasePeople() {
        return casePeople;
    }

    public void setCasePeople(CasePeople casePeople) {
        this.casePeople = casePeople;
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
