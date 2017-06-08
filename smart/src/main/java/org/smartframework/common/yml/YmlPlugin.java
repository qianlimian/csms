package org.smartframework.common.yml;

import java.util.List;

public class YmlPlugin {

	private String plugin;
	
	private String path;
	
	private List<YmlModule> modules;

	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<YmlModule> getModules() {
		return modules;
	}

	public void setModules(List<YmlModule> modules) {
		this.modules = modules;
	}
	
}
