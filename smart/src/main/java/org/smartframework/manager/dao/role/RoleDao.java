package org.smartframework.manager.dao.role;

import org.smartframework.manager.entity.Role;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;

public interface RoleDao extends BaseJpaRepository<Role, Integer>, RoleDaoExtend {

    Role findByName(String name);

}
