package org.smartframework.utils.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.smartframework.utils.MD5Utils;

public final class StringHelper {
	public static String getUUID() {
		return StringUtils.remove(UUID.randomUUID().toString(), "-");
	}

	public static String md5Encode(String origin) {
		return MD5Utils.encode(origin);
	}

	public static boolean isAnyEmpty(String... strings) {
		if ((strings == null) || (strings.length == 0)) {
			return true;
		}
		String[] arrayOfString = strings;
		int j = strings.length;
		for (int i = 0; i < j; i++) {
			String s = arrayOfString[i];
			if ((s == null) || (s.isEmpty())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAllEmpty(String... strings) {
		if ((strings == null) || (strings.length == 0)) {
			return true;
		}
		String[] arrayOfString = strings;
		int j = strings.length;
		for (int i = 0; i < j; i++) {
			String s = arrayOfString[i];
			if ((s != null) && (!s.isEmpty())) {
				return false;
			}
		}
		return true;
	}

	public static String dot2Path(String packageName) {
		return packageName.replace('.', '/');
	}

	public static String path2Dot(String path) {
		return path.replace('/', '.').replace('\\', '.');
	}

	public static Object str2obj(Class<?> clazz, String str)
			throws FormatException {
		if (str == null) {
			return null;
		}
		try {
			if ((Boolean.class.equals(clazz)) || (Boolean.TYPE.equals(clazz))) {
				return Boolean.valueOf(str);
			}
		} catch (Exception e) {
			throw new FormatException("格式错误应该是布尔值");
		}
		try {
			if ((Integer.class.equals(clazz)) || (Integer.TYPE.equals(clazz))) {
				return Integer.valueOf(str);
			}
			if ((Long.class.equals(clazz)) || (Long.TYPE.equals(clazz))) {
				return Long.valueOf(str);
			}
			if ((Short.class.equals(clazz)) || (Short.TYPE.equals(clazz))) {
				return Short.valueOf(str);
			}
			if (clazz.isEnum()) {
				return clazz.getEnumConstants()[Integer.valueOf(str).intValue()];
			}
		} catch (Exception e) {
			throw new FormatException("格式错误应该是整数");
		}
		try {
			if ((Double.class.equals(clazz)) || (Double.TYPE.equals(clazz))) {
				return Double.valueOf(str);
			}
			if ((Float.class.equals(clazz)) || (Float.TYPE.equals(clazz))) {
				return Float.valueOf(str);
			}
			if (BigDecimal.class.equals(clazz)) {
				return new BigDecimal(str);
			}
		} catch (Exception e) {
			throw new FormatException("格式错误应该是数值");
		}
		String format = null;
		try {
			if (Date.class.equals(clazz)) {
				if (str.equalsIgnoreCase("&nbsp;")) {
					return null;
				}
				switch (str.length()) {
				case 4:
					format = "yyyy";
					return new SimpleDateFormat(format).parse(str);
				case 7:
					format = "yyyy-MM";
					return new SimpleDateFormat(format).parse(str);
				case 6:
					format = "yyyyMM";
					return new SimpleDateFormat(format).parse(str);
				case 10:
					format = "yyyy-MM-dd";
					return new SimpleDateFormat(format).parse(str);
				case 8:
					format = "yyyyMMdd";
					return new SimpleDateFormat(format).parse(str);
				case 13:
					format = "yyyy-MM-dd HH";
					return new SimpleDateFormat(format).parse(str);
				case 11:
					format = "yyyyMMdd HH";
					return new SimpleDateFormat(format).parse(str);
				case 16:
					format = "yyyy-MM-dd HH:mm";
					return new SimpleDateFormat(format).parse(str);
				case 19:
					format = "yyyy-MM-dd HH:mm:ss";
					return new SimpleDateFormat(format).parse(str);
				}
				return null;
			}
		} catch (ParseException e) {
			throw new FormatException("格式错误应该是日期格式" + format);
		} catch (Exception e) {
			throw new FormatException("格式错误应该是日期格式" + format);
		}
		return str;
	}

	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	public static Integer[] toArray(String str) throws Exception {
		if ((str == null) || ("".equals(str))) {
			throw new Exception("参数传递错误");
		}
		String[] ss = str.split(",");
		Integer[] ids = new Integer[ss.length];
		if (ss.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				ids[i] = Integer.valueOf(Integer.parseInt(ss[i]));
			}
		} else {
			ids[0] = Integer.valueOf(Integer.parseInt(str));
		}
		return ids;
	}

	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String tmp = "";
		for (int i = 0; i < b.length; i++) {
			tmp = Integer.toHexString(b[i] & 0xFF);
			if (tmp.length() == 1) {
				sb.append("0" + tmp);
			} else {
				sb.append(tmp);
			}
		}
		return sb.toString();
	}

	public static byte[] hex2byte(String str) {
		if (str == null) {
			return null;
		}
		str = str.trim();
		int len = str.length();
		if ((len == 0) || (len % 2 == 1)) {
			return null;
		}
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[(i / 2)] = ((byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue());
			}
			return b;
		} catch (Exception e) {
		}
		return null;
	}
}

