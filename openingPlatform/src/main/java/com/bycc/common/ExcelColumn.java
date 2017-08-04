package com.bycc.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel导入注解.
 * 
 * @author 于明哲
 * @date 2015-01-30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ExcelColumn {
	/**
	 * 属性在excel中的列名.
	 */
	String title();
	
	/**
	 * 是否允许字段为空, 默认允许为空
	 */
	boolean allowBlank() default true;
	
	/**
	 * 是否是数值类型 
	 */
	//boolean isNumeric() default false;
	
	/**
	 * 数值型精度, 默认保留2位小数 
	 */
	int scale() default 2;
	
	/**
	 * 该注解所注释的属性是否必须出现在excel中
	 * <p>
	 * 最初设计为凡是用此注解注释的字段必须出现在excel文件中, 否则会抛出校验异常.
	 * 现由于业务需要, 要求即使加了该注解也不强制属性出现在excel中, 因此扩展出此属性
	 * 以便能更好地控制解析过程.
	 * 
	 */
	boolean required() default true;
	
	/**
	 * 时间格式，用于field是Date或String类型的数据，导出excel时进行格式指定
	 * @return
	 */
	String dateFormat() default "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 时间格式，用于field是String类型的数据，导入excel时将日期转换成的日期格式
	 * @return
	 */
	String impDateFormat() default "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 是否是时间类型
	 * @return
	 */
	boolean dateType() default false;
}