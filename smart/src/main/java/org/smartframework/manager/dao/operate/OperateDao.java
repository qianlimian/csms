package org.smartframework.manager.dao.operate;

import org.smartframework.manager.entity.Module;
import org.smartframework.manager.entity.Operate;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperateDao extends BaseJpaRepository<Operate, Integer> {

    Operate findTopByNameAndModule(String name, Module module);

    List<Operate> findByModule(Module module);
}
