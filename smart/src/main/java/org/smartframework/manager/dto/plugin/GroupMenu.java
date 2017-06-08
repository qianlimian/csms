package org.smartframework.manager.dto.plugin;

import java.io.Serializable;
import java.util.List;

import org.smartframework.manager.entity.Menu;

public class GroupMenu implements Serializable {

	private static final long serialVersionUID = 3107495866167802330L;

	private Integer id;
	
	private String name;
	
	private List<LeafMenu> leafMenus;

	//构造函数
	public GroupMenu() {
	}

	public GroupMenu(Menu menu) {
		this.id = menu.getId();
		this.name = menu.getName();
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

	public List<LeafMenu> getLeafMenus() {
		return leafMenus;
	}

	public void setLeafMenus(List<LeafMenu> leafMenus) {
		this.leafMenus = leafMenus;
	}
}
