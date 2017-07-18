package org.smartframework.manager.dto.plugin;

import java.io.Serializable;
import java.util.List;

import org.smartframework.manager.entity.Menu;

public class ModuleMenu implements Serializable {

	private static final long serialVersionUID = 5281606632371562328L;

	private Integer id;
	
	private String name;

	private String url;

	private List<GroupOrLeafMenu> groupLeafMenus;

	//构造函数
	public ModuleMenu() {
	}

	public ModuleMenu(Menu menu) {
		this.id = menu.getId();
		this.name = menu.getName();
		this.url = menu.getUrl();
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<GroupOrLeafMenu> getGroupLeafMenus() {
		return groupLeafMenus;
	}

	public void setGroupLeafMenus(List<GroupOrLeafMenu> groupLeafMenus) {
		this.groupLeafMenus = groupLeafMenus;
	}
}
