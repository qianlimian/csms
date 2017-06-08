package org.smartframework.manager.dto.plugin;

import java.io.Serializable;

import org.smartframework.manager.entity.Menu;

public class LeafMenu implements Serializable {

	private static final long serialVersionUID = -5848436459087907083L;

	private Integer id;
	
	private String name;
	
	private String url;
	
	//构造函数
	public LeafMenu() {
	}

	public LeafMenu(Menu menu) {
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

}
