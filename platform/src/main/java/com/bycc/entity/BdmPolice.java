package com.bycc.entity;

import com.bycc.enumitem.DutyType;
import org.smartframework.manager.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 民警
 */
@Entity
@Table(name = "bdm_police")
@TableGenerator(name = "com.bycc.entity.BdmPolice",
        table = "ID_Sequence",
        pkColumnName = "KEY_ID_",
        valueColumnName = "GEN_VALUE_",
        pkColumnValue = "com.bycc.entity.BdmPolice",
        initialValue = 1,
        allocationSize = 1
)
public class BdmPolice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.BdmPolice")
    private Integer id;

    /**
     * 用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_")
    private User user;


    /**
     * 职务
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "duty_type_")
    private DutyType dutyType;

    /**
     * 公安局（派出所）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "police_station_id_")
    private BdmPoliceStation policeStation;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DutyType getDutyType() {
        return dutyType;
    }

    public void setDutyType(DutyType dutyType) {
        this.dutyType = dutyType;
    }

    public BdmPoliceStation getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(BdmPoliceStation policeStation) {
        this.policeStation = policeStation;
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
