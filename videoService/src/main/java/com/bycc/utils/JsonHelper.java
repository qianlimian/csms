package com.bycc.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHelper {
	//饿汉式方式定义一个全局的ObjectMapper实例
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 对象转化为JSON
	 * @param obj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static String getJson(Object obj){
		if(obj==null)return "";
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			LogHelper.log(JsonHelper.class, LogHelper.ERROR, "pojoToJson 翻译时出错");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			LogHelper.log(JsonHelper.class, LogHelper.ERROR, "pojoToJson 翻译时出错");
			e.printStackTrace();
		} catch (IOException e) {
			LogHelper.log(JsonHelper.class, LogHelper.ERROR, "pojoToJson 翻译时出错");
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * JSON转化为对象
	 * @param obj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static <T> T getBean(String json, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(json, valueType);
	}
	
	/**
	 * JSON转化为对象
	 * @param obj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static <T> T getBean(String json, TypeReference<T> valueType) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(json, valueType);
	}
	
}
