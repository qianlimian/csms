package org.smartframework.manager.dto.menu;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.smartframework.utils.helper.JsonHelper;

import java.io.IOException;

public class MenuCondition {

	private String code;
	
	private String name;

	private String plugin;
	
	private String type;

	public MenuCondition() {// jackson调用该构造方法构造新的bean对象
	}

	public MenuCondition(String json) {// @RequestParam注解调用该构造函数将json字符串参数传入
	}

	// 名称固定的静态方法，转换json字符串为对象
	public static MenuCondition valueOf(String json) throws JsonParseException, JsonMappingException, IOException {
		MenuCondition MenuCondition = JsonHelper.getBean(json, MenuCondition.class);
		return MenuCondition;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
