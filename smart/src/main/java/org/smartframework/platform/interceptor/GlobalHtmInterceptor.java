package org.smartframework.platform.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.smartframework.manager.dto.plugin.GroupMenu;
import org.smartframework.manager.dto.plugin.LeafMenu;
import org.smartframework.manager.dto.plugin.ModuleMenu;
import org.smartframework.manager.dto.plugin.PluginMenu;
import org.smartframework.manager.entity.*;
import org.smartframework.manager.service.menu.MenuService;
import org.smartframework.manager.service.user.UserService;
import org.smartframework.utils.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @description 公用.htm拦截器，处理请求的菜单权限等
 * @author zhaochuanfeng
 */
public class GlobalHtmInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	
	// 进入 Handler方法之前执行
	// 应用场景：身份认证
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return super.preHandle(request, response, handler);
	}

	// 进入Handler方法之后，返回modelAndView之前执行
	// 应用场景从modelAndView出发：将公用的模型数据(比如菜单导航)在这里传到视图，也可以在这里统一指定视图
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		//System.out.println("---------------拦截器postHandle-------------------：   " + request.getServletPath());

		LoginUser loginUser = User.getCurrentUser();
		if (loginUser != null) {
			User user = userService.findByLoginName(loginUser.getLoginName());
			
			//session中的用户菜单、权限、角色等
			HttpSession session = request.getSession();

			if (session.getAttribute("userPlugins") == null) {
				List<PluginMenu> userPlugins = menuService.getUserPlugins(user.getMenuIds());
				session.setAttribute("userPlugins", userPlugins);
			}
			if (session.getAttribute("userOperates") == null) {
				session.setAttribute("userOperates", JsonHelper.getJson(user.getOperates()));
			}

			modelAndView.addObject("loginUser", user);
            modelAndView.addObject("userSetting", user.getUserSetting());
			modelAndView.addObject("userPlugins", session.getAttribute("userPlugins"));
			modelAndView.addObject("userOperates", session.getAttribute("userOperates"));

			//根据当前访问路径匹配当前Leaf菜单
			String servletPath = request.getServletPath();
			Menu leafMenu = menuService.getCurrentLeafMenu(servletPath);
			if (null != leafMenu) {
				//当前leaf菜单的父菜单
                Menu groupMenu = leafMenu.getGroupMenu();
                Menu moduleMenu = leafMenu.getModuleMenu();
                Plugin plugin = leafMenu.getPlugin();

                if (plugin != null && moduleMenu != null) {
                    if (session.getAttribute("userModuleMenus_" + plugin.getCode()) == null) {
                        List<ModuleMenu> menus = menuService.getUserModuleMenus(plugin, user.getMenuIds());
                        session.setAttribute("userModuleMenus_" + plugin.getCode(), menus);
                    }
                    if (session.getAttribute("userGroupMenus_" + moduleMenu) == null) {
                        List<GroupMenu> menus = menuService.getUserGroupMenus(moduleMenu, user.getMenuIds());
                        session.setAttribute("userGroupMenus_" + moduleMenu, menus);
                    }
                    if (session.getAttribute("userLeafMenus_" + moduleMenu) == null) {
                        List<LeafMenu> menus = menuService.getUserLeafMenus(moduleMenu, user.getMenuIds());
                        session.setAttribute("userLeafMenus_" + moduleMenu, menus);
                    }

                    modelAndView.addObject("userModuleMenus", session.getAttribute("userModuleMenus_" + plugin.getCode()));
                    modelAndView.addObject("userGroupMenus", session.getAttribute("userGroupMenus_" + moduleMenu));
                    modelAndView.addObject("userLeafMenus", session.getAttribute("userLeafMenus_" + moduleMenu));
                }

                modelAndView.addObject("currentLeafMenu", leafMenu);
                modelAndView.addObject("currentGroupMenu", groupMenu);
                modelAndView.addObject("currentModuleMenu", moduleMenu);
                modelAndView.addObject("currentPlugin", plugin);
			}
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}

	// 执行Handler完成执行此方法
	// 应用场景：统一异常处理，统一日志处理
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

}
