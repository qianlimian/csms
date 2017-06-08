package org.smartframework.platform.dictionary.exception;

public class BeanException extends Exception {
	private static final long serialVersionUID = 1693370700734112283L;

	public BeanException(Class<?> c, String... propertys) {
		super(toMessage(c.getName(), propertys));
	}

	public BeanException(String className, String... propertys) {
		super(toMessage(className, propertys));
	}

	public BeanException(String className) {
		super(toMessage(className, new String[0]));
	}

	private static String toMessage(String className, String... propertys) {
		StringBuilder sb = new StringBuilder("类: ");
		sb.append(className).append("错误 ");
		if ((propertys != null) && (propertys.length > 0)) {
			sb.append(" 缺少属性 [ ");
			for (String p : propertys) {
				sb.append(p).append(' ');
			}
			sb.append(']');
		}
		return "请检查.";
	}
}
