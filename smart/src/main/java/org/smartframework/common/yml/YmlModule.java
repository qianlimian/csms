package org.smartframework.common.yml;

import java.util.List;

public class YmlModule {

	private String module;
	
	private List<YmlGroup> groups;
	
	private List<YmlMenu> menus;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<YmlGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<YmlGroup> groups) {
		this.groups = groups;
	}

	public List<YmlMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<YmlMenu> menus) {
		this.menus = menus;
	}
	
}
