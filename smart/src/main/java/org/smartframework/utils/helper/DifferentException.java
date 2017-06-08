package org.smartframework.utils.helper;

public class DifferentException extends Exception {
	private static final long serialVersionUID = 4296924574077017419L;

	public DifferentException(Object source, Object target) {
		super("对象不相同:" + source.getClass().getName() + "," + target.getClass().getName());
	}
}