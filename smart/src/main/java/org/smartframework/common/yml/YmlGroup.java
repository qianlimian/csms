package org.smartframework.common.yml;

import java.util.List;

public class YmlGroup {

	private String group;
	
	private List<YmlMenu> menus;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<YmlMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<YmlMenu> menus) {
		this.menus = menus;
	}
	
}
