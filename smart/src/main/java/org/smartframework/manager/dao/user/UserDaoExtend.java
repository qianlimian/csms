package org.smartframework.manager.dao.user;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.User;

import java.util.List;

public interface UserDaoExtend {

    public List<User> findByGroupId(QueryBean qb, Integer groupId);
}
