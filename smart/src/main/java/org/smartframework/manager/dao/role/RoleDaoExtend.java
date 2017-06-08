package org.smartframework.manager.dao.role;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.Role;

import java.util.List;

public interface RoleDaoExtend {

    public List<Role> findByGroupId(QueryBean qb, Integer groupId);
}
