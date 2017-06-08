package org.smartframework.manager.dto.group;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.manager.entity.Group;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;

@DtoClass
public class GroupDto {

    private Integer id;

    @NotEmpty(message = "不能为空")
    @Size(max = 50, message = "不能超过{max}个字符")
    private String name;

    @NotEmpty(message = "不能为空")
    @Size(max = 50, message = "不能超过{max}个字符")
    private String desc;

    private String updateDate;

    private String insertDate;

    private String operator;

    public Group toEntity() {
        Group group = new Group();
        return toEntity(group);
    }

    public Group toEntity(Group group) {
        group.setId(this.getId());
        group.setName(this.getName());
        group.setDescription(this.getDesc());
        return group;
    }

    public static GroupDto toDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDesc(group.getDescription());
        dto.setUpdateDate(DateHelper.formatDateToString(group.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
        dto.setInsertDate(DateHelper.formatDateToString(group.getInsertDate(), "yyyy-MM-dd HH:mm:ss"));
        dto.setOperator(group.getOperator());
        return dto;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
