package org.smartframework.platform.security.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginCtrl {

	/**
	 * @description 跳转到后台首页
	 */
	@RequestMapping(value = "/login")
	public String login() {
        return "/smart/login/index";
	}
	
	/**
	 * @description 后台首页
	 */
	@RequestMapping(value = {"/", "/home", "/admin"})
	public String home() {
		return "/smart/frame/home";
	}
}
