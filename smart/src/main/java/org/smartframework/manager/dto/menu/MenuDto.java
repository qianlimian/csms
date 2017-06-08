package org.smartframework.manager.dto.menu;

import javax.validation.constraints.Size;

import org.smartframework.manager.entity.Menu;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.platform.dto.annotation.DtoProperty;

@DtoClass(entities = { Menu.class })
public class MenuDto {

	@DtoProperty(entityClass = Menu.class)
	private Integer id;

	@Size(max = 150, min = 1, message = "菜单编码长度应在{min}到{max}个字符之间")
	@DtoProperty(entityClass = Menu.class)
	private String code;

	@Size(max = 50, min = 1, message = "菜单名称长度应在{min}到{max}个字符之间")
	@DtoProperty(entityClass = Menu.class)
	private String name;

	@DtoProperty(entityClass = Menu.class, entityProperty = "plugin.code")
	private String plugin;

	@Size(max = 150, message = "菜单URL长度应小于{max}个字符")
	@DtoProperty(entityClass = Menu.class)
	private String url;

	@DtoProperty(entityClass = Menu.class)
	private String type;

	@DtoProperty(entityClass = Menu.class, entityProperty = "parent.id")
	private Integer parentId;

	@DtoProperty(entityClass = Menu.class)
	private Integer displayOrder;

	@Size(max = 50, min = 1, message = "菜单描述长度应在{min}到{max}个字符之间")
	@DtoProperty(entityClass = Menu.class)
	private String desc;

	public static MenuDto toDto(Menu menu) {
		MenuDto dto = new MenuDto();
		dto.setId(menu.getId());
		dto.setCode(menu.getCode());
		dto.setName(menu.getName());
		dto.setPlugin(menu.getPlugin().getCode());
		dto.setUrl(menu.getUrl());
		dto.setType(menu.getType());
		dto.setParentId(menu.getParent() == null ? null: menu.getParent().getId());
		dto.setDesc(menu.getDesc());
		dto.setDisplayOrder(menu.getDisplayOrder());
		return dto;
	}


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

	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
