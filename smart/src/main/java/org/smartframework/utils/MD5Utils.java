package org.smartframework.utils;

import java.security.MessageDigest;

/**
 * 
 * @ClassName: MD5Helper 
 * @Description: MD5工具类
 * @author liuguangyin
 * @date 2012-8-15
 *
 */
public final class MD5Utils {
	
	private final static String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private MD5Utils() {

	}

	/**
	 * 将二进制数组转化为字符串
	 * @Title: byteArrayToHexString 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @author nidongsheng 2012-8-15
	 * @param  bytes
	 * @return String 
	 * @throws
	 */
	private static String byteArrayToHexString(byte[] bytes) {
		if (bytes == null)
			return "";
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			resultSb.append(byteToHexString(bytes[i]));
		}
		return resultSb.toString();
	}
	/**
	 * 将字节转化为16进制字符
	 * @Title: byteToHexString 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @author nidongsheng 2012-8-15
	 * @param by
	 * @return String 
	 * @throws
	 */
	private static String byteToHexString(byte by) {
		int bInt = by;
		if (bInt < 0)
			bInt = 256 + bInt;
		int d1 = bInt / 16;
		int d2 = bInt % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}

	/**
	 * 
	 * @Title: encode 
	 * @Description: 将指定字符串编码并返回 
	 * @author nidongsheng 2012-8-15
	 * @param data
	 * @return String 
	 * @throws
	 */
	public static String encode(String data) {
		if (data == null)
			return encode("");
		String resultString = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(data.getBytes()));
		} catch (Exception ex) {
			resultString = data;
		}
		return resultString;
	}
	
}
