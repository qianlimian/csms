package org.smartframework.manager.dto.menu;

import java.util.ArrayList;
import java.util.List;
import org.smartframework.manager.entity.Menu;
import org.smartframework.manager.entity.Plugin;

public class MenuCheckBoxDto {

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
	private List<MenuCheckBoxDto> items = new ArrayList<MenuCheckBoxDto>();


	public static MenuCheckBoxDto toDto(Plugin plugin) {
		MenuCheckBoxDto dto = new MenuCheckBoxDto();
		dto.setId(plugin.getId());
		dto.setLabel(plugin.getName());
		dto.setType("PLUGIN");
		return dto;
	}

	public static MenuCheckBoxDto toDto(Menu menu) {
		MenuCheckBoxDto dto = new MenuCheckBoxDto();
		dto.setId(menu.getId());
		dto.setLabel(menu.getName());
		dto.setType(menu.getType());
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

	public List<MenuCheckBoxDto> getItems() {
		return items;
	}

	public void setItems(List<MenuCheckBoxDto> items) {
		this.items = items;
	}
}
