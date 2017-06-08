package org.smartframework.manager.dto.plugin;

import java.io.Serializable;
import java.util.List;

import org.smartframework.manager.entity.Plugin;

public class PluginMenu implements Serializable {

	private static final long serialVersionUID = 3755172919837777457L;

	private Integer id;
	
	private String name;
	
	private String plugin;
	//是否显示
	private Boolean display;
	
	private List<ModuleMenu> moduleMenus;
	
	//构造函数
	public PluginMenu() {
	}

	public PluginMenu(Plugin plugin) {
		this.id = plugin.getId();
		this.name = plugin.getName();
		this.plugin = plugin.getCode();
		this.display = plugin.getDisplayOrNot();
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

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public List<ModuleMenu> getModuleMenus() {
        return moduleMenus;
    }

    public void setModuleMenus(List<ModuleMenu> moduleMenus) {
        this.moduleMenus = moduleMenus;
    }
}

