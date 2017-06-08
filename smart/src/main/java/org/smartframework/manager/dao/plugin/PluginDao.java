package org.smartframework.manager.dao.plugin;

import org.smartframework.manager.entity.Plugin;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;

public interface PluginDao extends BaseJpaRepository<Plugin, Integer> {

	Plugin findByCode(String plugin);

}
