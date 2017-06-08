package org.smartframework.manager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "smart_operates")
@TableGenerator(name = "org.smartframework.entity.Operate", // TableGenerator的名字，下面的“generator”使用
        table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
        pkColumnName = "KEY_ID_", // 列1的字段名
        valueColumnName = "GEN_VALUE_", // 列2的字段名
        pkColumnValue = "org.smartframework.entity.Operate", // 该表存在ID_GEN中列1的值
        initialValue = 1, // 初始值
        allocationSize = 1 // 增长率
)
public class Operate implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "org.smartframework.entity.Operate")
    private Integer id;

    /**
     * 编码
     */
    @Column(name = "code_", length = 50)
    private String code;

    /**
     * 名称
     */
    @Column(name = "name_", length = 50)
    private String name;

    /**
     * 模块
     */
    @ManyToOne
    @JoinColumn(name = "module_id_")
    private Module module;

    /**
     * 插入日期
     */
    @Column(name = "insert_date_")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date insertDate;

    /**
     * 更新日期
     */
    @Column(name = "update_date_")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updateDate;

    /**
     * 操作人
     */
    @Column(name = "operator_", length = 50)
    private String operator;

    /**
     * 权限组（被动方）
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "operates", fetch = FetchType.LAZY)
    private Set<Group> groups = new HashSet<Group>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
