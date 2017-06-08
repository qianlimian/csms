package org.smartframework.manager.dao.menu;

import org.smartframework.manager.dto.menu.MenuCondition;
import org.smartframework.manager.entity.Menu;

import java.util.List;

public interface MenuDaoExtend {

    List<Menu> findByCondition(MenuCondition condition);
}
