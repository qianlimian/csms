package org.smartframework.manager.service.menu;

import java.util.ArrayList;
import java.util.List;

import org.smartframework.manager.dao.menu.MenuDao;
import org.smartframework.manager.dao.plugin.PluginDao;
import org.smartframework.manager.dto.menu.MenuCheckBoxDto;
import org.smartframework.manager.dto.menu.MenuCondition;
import org.smartframework.manager.dto.menu.MenuDto;
import org.smartframework.manager.dto.plugin.GroupMenu;
import org.smartframework.manager.dto.plugin.LeafMenu;
import org.smartframework.manager.dto.plugin.ModuleMenu;
import org.smartframework.manager.dto.plugin.PluginMenu;
import org.smartframework.manager.entity.Menu;
import org.smartframework.manager.entity.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
	

	@Autowired
    private MenuDao menuDao;
	
	@Autowired
    private PluginDao pluginDao;

	/**
     * 根据request路径匹配当前Leaf菜单
     */
	@Override
	public Menu getCurrentLeafMenu(String url) {
		Menu leafMenu = menuDao.findByTypeAndUrl("LEAF", url);
		return leafMenu;
	}
	
	/**
	 * 所有Plugin菜单
	 */
	@Override
	public List<PluginMenu> getUserPlugins(List<Integer> menuIds) {
		List<PluginMenu> menus = new ArrayList<PluginMenu>();
		//fixed
		if (menuIds.isEmpty()) return menus;

		List<Plugin> plugins = pluginDao.findAll();
		for (Plugin plugin : plugins) {
			//Plugin的所有Module菜单
			List<ModuleMenu> moduleMenus = this.getUserModuleMenus(plugin, menuIds);
			
			PluginMenu pluginMenu = new PluginMenu(plugin);
			pluginMenu.setModuleMenus(moduleMenus);

			menus.add(pluginMenu);
		}
		return menus;
	}


	/**
     * Plugin的所有Module菜单
     */
	public List<ModuleMenu> getUserModuleMenus(Plugin plugin, List<Integer> menuIds) {
		List<ModuleMenu> list = new ArrayList<ModuleMenu>();
		List<Menu> menus = menuDao.findByTypeAndPluginLimitIds("MODULE", plugin, menuIds);
		for (Menu menu : menus) {
			//Module的所有Group菜单和Leaf菜单
			List<GroupMenu> groupMenus = this.getUserGroupMenus(menu, menuIds);
			List<LeafMenu> leafMenus = this.getUserLeafMenus(menu, menuIds);

			ModuleMenu moduleMenu = new ModuleMenu(menu);
			moduleMenu.setGroupMenus(groupMenus);
			moduleMenu.setLeafMenus(leafMenus);
			list.add(moduleMenu);
		}
		return list;
	}

	/**
     * Module的所有Group菜单
     */
	public List<GroupMenu> getUserGroupMenus(Menu moduleMenu, List<Integer> menuIds) {
		List<GroupMenu> list = new ArrayList<GroupMenu>();
		List<Menu> menus = menuDao.findByTypeAndParentLimitIds("GROUP", moduleMenu, menuIds);
		for (Menu menu : menus) {
			//Group的所有Leaf菜单
			List<LeafMenu> leafMenus = this.getUserLeafMenus(menu, menuIds);

			GroupMenu groupMenu = new GroupMenu(menu);
			groupMenu.setLeafMenus(leafMenus);

			list.add(groupMenu);
		}
		return list;
	}

	/**
     * Module(Group)的所有Leaf菜单
     */
	public List<LeafMenu> getUserLeafMenus(Menu moduleOrGroupMenu, List<Integer> menuIds) {
		List<LeafMenu> list = new ArrayList<LeafMenu>();
		List<Menu> menus = menuDao.findByTypeAndParentLimitIds("LEAF", moduleOrGroupMenu, menuIds);
		for (Menu menu : menus) {
			LeafMenu leafMenu = new LeafMenu(menu);
			list.add(leafMenu);
		}
		return list;
	}

	/**
	 * 查询所有菜单
	 */
	@Override
	public List<MenuCheckBoxDto> getMenus() {
		return getChildren(null);
	}

	/**
	 * 查询子菜单
	 */
	private List<MenuCheckBoxDto> getChildren(Menu parent) {
		List<MenuCheckBoxDto> items = new ArrayList<MenuCheckBoxDto>();

		List<Menu> menus = menuDao.findByParentOrderByDisplayOrder(parent);
		for (Menu menu : menus) {
			//递归查询
			List<MenuCheckBoxDto> children = getChildren(menu);

			MenuCheckBoxDto dto = MenuCheckBoxDto.toDto(menu);
			dto.setHasChildren(!"LEAF".equals(menu.getType()));
			dto.setItems(children);
			items.add(dto);
		}

		return items;
	}

	/**
	 * 查询菜单列表
	 */
	@Override
	public List<MenuDto> findByCondition(MenuCondition condition) {
		List<MenuDto> dtos = new ArrayList<MenuDto>();
		List<Menu> menus = menuDao.findByCondition(condition);
		for (Menu menu : menus) {
			dtos.add(MenuDto.toDto(menu));
		}
		return dtos;
	}

}
