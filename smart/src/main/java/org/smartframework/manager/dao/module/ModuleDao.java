package org.smartframework.manager.dao.module;


import org.smartframework.manager.entity.Module;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuleDao extends BaseJpaRepository<Module, Integer> {

    Module findTopByNameAndParent(String name, Module parent);

    List<Module> findByParent(Module parent);
}
