package org.smartframework.manager.service.menu;

import java.util.List;
import java.util.Map;

import org.smartframework.manager.dto.menu.MenuCondition;
import org.smartframework.manager.dto.menu.MenuDto;
import org.smartframework.manager.dto.menu.MenuCheckBoxDto;
import org.smartframework.manager.dto.plugin.GroupMenu;
import org.smartframework.manager.dto.plugin.LeafMenu;
import org.smartframework.manager.dto.plugin.ModuleMenu;
import org.smartframework.manager.dto.plugin.PluginMenu;
import org.smartframework.manager.dto.user.UserSettingDto;
import org.smartframework.manager.entity.Menu;
import org.smartframework.manager.entity.Plugin;
import org.smartframework.manager.entity.UserSetting;

public interface MenuService {

	//根据request路径匹配当前Leaf菜单
	Menu getCurrentLeafMenu(String servletPath);
	
	//所有plugin菜单
	List<PluginMenu> getUserPlugins(List<Integer> menuIds);

    //Plugin的所有Module菜单
    public List<ModuleMenu> getUserModuleMenus(Plugin plugin, List<Integer> userMenuIds);

    //Module的所有Group菜单
    public List<GroupMenu> getUserGroupMenus(Menu moduleMenu, List<Integer> userMenuIds);

    //Module(Group)的所有Leaf菜单
    public List<LeafMenu> getUserLeafMenus(Menu moduleOrGroupMenu, List<Integer> userMenuIds);

	//查询所有菜单
	List<MenuCheckBoxDto> getMenus();

	//查询菜单列表
	List<MenuDto> findByCondition(MenuCondition condition);
}
