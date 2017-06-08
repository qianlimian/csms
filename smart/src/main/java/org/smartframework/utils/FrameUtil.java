package org.smartframework.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

/** 
 * @ClassName: FrameUtil 
 * @Description: 框架的工具类对象
 * @author nidongsheng
 * @date 2012-11-17
 *  
 */
public class FrameUtil {
	
	/**
	 * @Title: getFeilds 
	 * @Description: 获取类内部定义的所有属性
	 * @author nidongsheng 2012-11-21
	 * @param  @param clazz
	 * @param  @return
	 * @return Field[] 
	 * @throws
	 */
	public static Field[] getFeilds(Class<?> clazz){
		return clazz.getDeclaredFields();
	}
	/**
	 * @Title: getAllFeilds 
	 * @Description: 获取类所有属性，包含继承的属性
	 * @author nidongsheng 2012-11-21
	 * @param  @param clazz
	 * @param  @return
	 * @return Field[] 
	 * @throws
	 */
	public static Field[] getAllFeilds(Class<?> clazz){
		List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        final int size =  fields.size();
        Field[] result = (Field[])fields.toArray(new Field[size]);
        return result;
	}
	
	/**
	 * 判断是否为Ajax请求
	 * @param request   HttpServletRequest
	 * @return  是true, 否false
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
	    String requestType = request.getHeader("X-Requested-With");
	    if (requestType != null && requestType.equals("XMLHttpRequest")) {
	        return true;
	    } else {
	        return false;
	    }
	}
	/**
	 * @Title: isClassImplementInterface 
	 * @Description: 判断指定类是否实现了接口 
	 * @author nidongsheng 2013-5-24
	 * @param  @param clazz
	 * @param  @param face
	 * @return boolean 
	 * @throws
	 */
	public static boolean isClassImplementInterface(Class<?> clazz,Class<?> face){
		Class<?>[] array = clazz.getInterfaces();
		for(Class<?> clz:array){
			if(clz.equals(face)){
				return true;
			}
		}
		return false;
	}
	/**
	 * @Title: getZoonTime 
	 * @Description: 获取东八区时间
	 * @author liuguangyin 2013-6-25
	 * @param  @return
	 * @return long 
	 * @throws
	 */
	public static long getZoonTime(){
		TimeZone time = TimeZone.getTimeZone("GMT+8"); //设置为东八区
		TimeZone.setDefault(time);// 设置时区
		Calendar calendar = Calendar.getInstance();// 获取实例
		long GMT8 = calendar.getTimeInMillis();//
		return GMT8;
	}
	/**
	 * @Title: getZoonTime 
	 * @Description: 获取指定时区时间
	 * @author liuguangyin 2013-6-25
	 * @param  @param GMT
	 * @param  @return
	 * @return long 
	 * @throws
	 */
	public static long getZoonTime(String GMT){
		TimeZone time = TimeZone.getTimeZone(GMT); //设置为东八区
		TimeZone.setDefault(time);// 设置时区
		Calendar calendar = Calendar.getInstance();// 获取实例
		long GMT8 = calendar.getTimeInMillis();//
		return GMT8;
	}
	/**
	 * @Title: getMethodName 
	 * @Description: TODO(获取当前函数的名称) 
	 * @author liuguangyin 2013-6-26
	 * @param  @return
	 * @return String 
	 * @throws
	 */
	public static String getCurrentMethodName(){
		return Thread.currentThread().getStackTrace()[2].getMethodName();
		
	}
}
