package org.smartframework.manager.service.module;

import org.smartframework.manager.dto.module.ModuleCheckBoxDto;

import java.util.List;

public interface ModuleService {

    void initYmlModule(Object o);

    List<ModuleCheckBoxDto> getModules();
}
