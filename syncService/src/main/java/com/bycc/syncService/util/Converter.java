package com.bycc.syncService.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * http响应转换工具类
 * <p>
 * 用于将HTTP响应转换成对应的实体
 * User: yumingzhe
 * Time: 2017-4-19 10:22
 */
public class Converter {

	/**
	 * 将json响应转换为实体
	 *
	 * @param payload json串
	 * @param type    目标类型
	 * @return 实体
	 */
	public static <T extends Object> T jsonToEntity(String payload, Class<T> type) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(payload, type);
	}
}
