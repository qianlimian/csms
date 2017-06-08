package org.smartframework.platform.dictionary.exception;

public class MethodException extends Exception {
	private static final long serialVersionUID = 1693370700734112283L;

	public MethodException(Class<?> c, String methodName) {
		super(toMessage(c.getName(), methodName));
	}

	public MethodException(String className, String methodName) {
		super(toMessage(className, methodName));
	}

	public MethodException(String methodName) {
		super(toMessage(methodName));
	}

	private static String toMessage(String className, String methodName) {
		StringBuilder sb = new StringBuilder("类 [ ");
		sb.append(className).append(" ] 方法 [ ").append(methodName).append(" ] 错误请检查");
		return sb.toString();
	}

	private static String toMessage(String methodName) {
		StringBuilder sb = new StringBuilder("方法 [ ").append(methodName).append(" ] 错误请检查");
		return sb.toString();
	}
}

