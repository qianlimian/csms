package org.smartframework.utils.helper;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @功能名 公共服务-工具包
 * @类描述 时间工具类
 * @author 郭董宁
 */
public class DateHelper {

	/**
	 * 日期明天,省略时间
	 */
	public static Date tomorrowWithoutTime(Date date) {
		return dateWithoutTime(changeDateByDay(date, 1));
	}
	
	/**
	 * 获取一个与时间无关的日期,即时间为零点的日期
	 */
	public static Date dateWithoutTime(Date date) {
		try {
			return DateFormat.getDateInstance().parse(
					DateFormat.getDateInstance().format(date));
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 获取一个月的天数
	 */
	public static int numberOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当月的第一天
	 * @throws ParseException 
	 */
	public static Date firstDayOfMonth(Date date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.parse((dateFormat.format(date).substring(0, 8) + "01"));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取当月的最后一天
	 * @throws ParseException 
	 */
	public static Date lastDayOfMonth(Date date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.parse((dateFormat.format(date).substring(0, 8) + numberOfMonth(date)));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 判断两个日期是否在一年的一个月内
	 */
	public static boolean sameMonth(Date date, Date other) {
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		Calendar otherCal = Calendar.getInstance();
		otherCal.setTime(other);
		return (dateCal.get(Calendar.YEAR) == otherCal.get(Calendar.YEAR)) 
				&& (dateCal.get(Calendar.MONTH) == otherCal.get(Calendar.MONTH));
	}
	
	/**
	 * 获取当前系统时间
	 * @return
	 */
	public static Date getCurrentDate(){
		Calendar current = Calendar.getInstance();
		return current.getTime();
	}

	/**
	 * 按天时间推移
	 * @param currentDate 当前时间
	 * @param days 推移的天数
	 * 例：
	 * +1 向后移一天
	 * -1 向前移一天
	 * @return 推移后的时间
	 */
	public static Date changeDateByDay(Date currentDate, int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE,days);
		return calendar.getTime();
	}
	
	/**
	 * 按参数时间推移
	 * @param currentDate 当前时间
	 * @param typeParam 时间类型，Calendar中的常量
	 * @param value 推移的数量
	 * 例：
	 * +1 向后移一
	 * -1 向前移一
	 * @return 推移后的时间
	 */
	public static Date changeDateByType(Date currentDate, int typeParam, int value){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(typeParam,value);
		return calendar.getTime();
	}
	
	/**
	 * 将时间格式化成字符串
	 * @param changeDate 要转换的时间
	 * @param pattern 时间格式
	 * @return
	 */
	public static String formatDateToString(Date changeDate, String pattern){
		if(changeDate == null){
			return null;
		} else {
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(changeDate);
		}
	}
	
	/**
	 * 将日期转换为指定格式的日期
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDateToDate(Date date,String pattern) throws ParseException{
		String dateStr = formatDateToString(date, pattern);
		return formatStringToDate(dateStr, pattern);
	}
	
	/**
	 * 将字符串格式化成时间
	 * @param changeDate 要转换的字符串
	 * @param pattern 时间格式
	 * @return
	 * @throws ParseException 
	 */
	public static Date formatStringToDate(String changeDate, String pattern) throws ParseException{
		if(changeDate == null){
			return null;
		} else {
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.parse(changeDate);
		}
	}
	
	/**
	 * 自动将字符串格式化成时间，用于未知格式，效率较低
	 * @param changeDate 要转换的字符串
	 * @param pattern 时间格式
	 * @return
	 * @throws ParseException 
	 */
	public static Date autoFormatStringToDate(String changeDate) throws ParseException{
		if(changeDate == null){
			return null;
		} else {
			DateFormat dateFormat = null;
			if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")){
				dateFormat = new SimpleDateFormat("yy-M-d H:m:s");
			} else if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2}")){
				dateFormat = new SimpleDateFormat("yy-M-d");
			} else if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}")){
				dateFormat = new SimpleDateFormat("yy-M-d H:m");
			} else if(changeDate.matches("\\d{2,4}-\\d{1,2}-\\d{1,2} \\d{1,2}")){
				dateFormat = new SimpleDateFormat("yy-M-d H");
			} else {
				dateFormat = new SimpleDateFormat("yy-M-d H:m:s");
			}
			return dateFormat.parse(changeDate);
		}
	}

	//String date = "2016-08-15T16:00:00.000Z";
	public static Date formatUTCToDate(String changeDate) throws ParseException{
		if(changeDate == null){
			return null;
		} else {
			return formatStringToDate(changeDate.replace("Z", " UTC"), "yyyy-MM-dd'T'HH:mm:ss.SSS Z");
		}
	}

	/**
	 * 获得数据年度
	 * @return
	 */
	public static String getDataYear(){
		return formatDateToString(new Date(),"yyyy");
	}
	
	/**
	 * 求两个时间的日期差
	 * @param startDay 开始时间
	 * @param endDay 结束时间
	 * @param offSet 偏移量
	 * @return
	 */
	public static BigDecimal getDayDiffer(Date startDay, Date endDay, String offSet) 
	{
		// 去掉时分秒
		Date calStart = dateWithoutTime(startDay);
		Date calEnd = dateWithoutTime(endDay);
		if(calStart==null||calEnd==null){
			return new BigDecimal("0");
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDay);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDay);
	    return BigDecimal.valueOf((calEnd.getTime()- calStart.getTime()+endCalendar.get(Calendar.DST_OFFSET) - startCalendar.get(Calendar.DST_OFFSET)) / 86400000).add(new BigDecimal(offSet));
	} 
}
