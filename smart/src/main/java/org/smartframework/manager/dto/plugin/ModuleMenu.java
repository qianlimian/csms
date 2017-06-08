package org.smartframework.manager.dto.plugin;

import java.io.Serializable;
import java.util.List;

import org.smartframework.manager.entity.Menu;

public class ModuleMenu implements Serializable {

	private static final long serialVersionUID = 5281606632371562328L;

	private Integer id;
	
	private String name;

	private String url;

    private List<GroupMenu> groupMenus;

    private List<LeafMenu> leafMenus;
	
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

    public List<GroupMenu> getGroupMenus() {
        return groupMenus;
    }

    public void setGroupMenus(List<GroupMenu> groupMenus) {
        this.groupMenus = groupMenus;
    }

    public List<LeafMenu> getLeafMenus() {
        return leafMenus;
    }

    public void setLeafMenus(List<LeafMenu> leafMenus) {
        this.leafMenus = leafMenus;
    }
}
