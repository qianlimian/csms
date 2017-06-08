package org.smartframework.platform.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 7425731151877359892L;

	public BusinessException() {
    }

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Exception cause) {
		super(message, cause);
	}

}
