package org.smartframework.manager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "smart_user_settings")
@TableGenerator(name = "org.smartframework.entity.UserSetting", // TableGenerator的名字，下面的“generator”使用
        table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
        pkColumnName = "KEY_ID_", // 列1的字段名
        valueColumnName = "GEN_VALUE_", // 列2的字段名
        pkColumnValue = "org.smartframework.entity.UserSetting", // 该表存在ID_GEN中列1的值
        initialValue = 1, // 初始值
        allocationSize = 1 // 增长率
)
public class UserSetting implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "org.smartframework.entity.UserSetting")
    private Integer id;

    /**
     * 用户id
     */
    @OneToOne
    @JoinColumn(name = "user_id_")
    private User user;

    /**
     * 菜单位置设置
     */
    @Column(name = "menu_Position_")
    private String menuPosition;

    /**
     * 页面宽度设置
     */
    @Column(name = "page_width_")
    private String pageWidth;

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

    public String getMenuPosition() {
        return menuPosition;
    }

    public void setMenuPosition(String menuPosition) {
        this.menuPosition = menuPosition;
    }

    public String getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(String pageWidth) {
        this.pageWidth = pageWidth;
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
