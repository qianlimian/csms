package org.smartframework.manager.service.plugin;

import org.smartframework.manager.dao.plugin.PluginDao;
import org.smartframework.manager.entity.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PluginServiceImpl implements PluginService {

	@Autowired
	private PluginDao pluginDao;
	
	@Override
	public void save(Plugin plugin) {
		pluginDao.save(plugin);
	}

}
