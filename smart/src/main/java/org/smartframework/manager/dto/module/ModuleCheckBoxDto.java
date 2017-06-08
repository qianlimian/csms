package org.smartframework.manager.dto.module;

import org.smartframework.manager.entity.Module;
import org.smartframework.manager.entity.Operate;

import java.util.ArrayList;
import java.util.List;

public class ModuleCheckBoxDto {

    private Integer id;

    /**
     * 名称
     */
    private String label;

    /**
     * 类型
     */
    private String type;

    /**
     * 是否有子菜单
     */
    private Boolean hasChildren;

    /**
     * 子菜单
     */
    private List<ModuleCheckBoxDto> items = new ArrayList<ModuleCheckBoxDto>();

    public static ModuleCheckBoxDto toDto(Module module) {
        ModuleCheckBoxDto dto = new ModuleCheckBoxDto();
        dto.setId(module.getId());
        dto.setLabel(module.getName());
        dto.setType("MODULE");
        return dto;
    }

    public static ModuleCheckBoxDto toDto(Operate operate) {
        ModuleCheckBoxDto dto = new ModuleCheckBoxDto();
        dto.setId(operate.getId());
        dto.setLabel(operate.getName());
        dto.setType("OPERATE");
        return dto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public List<ModuleCheckBoxDto> getItems() {
        return items;
    }

    public void setItems(List<ModuleCheckBoxDto> items) {
        this.items = items;
    }

}
