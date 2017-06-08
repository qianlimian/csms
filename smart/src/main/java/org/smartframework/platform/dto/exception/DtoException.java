package org.smartframework.platform.dto.exception;

public class DtoException extends Exception {
	private static final long serialVersionUID = 1L;

	public DtoException(ExceptionType type) {
		super(type.getMessage());
	}

	public DtoException(String message) {
		super(message);
	}

	public DtoException(Exception exception) {
		super(ExceptionType.Property.getMessage() + exception.getMessage());
	}

	public DtoException(Exception exception, String message) {
		super(ExceptionType.Property.getMessage() + exception.getMessage() + " " + message);
	}

	public DtoException(Exception exception, Class<?> dtoBeanClass) {
		super(dtoBeanClass.getName() + ExceptionType.Property.getMessage() + exception.getMessage());
	}

	public static enum ExceptionType {
		Annotation("缺少@DtoClass标注"), Instance("DTO无法实例化"), Property("Property设置错误:");

		private String message;

		private ExceptionType(String message) {
			this.message = message;
		}

		String getMessage() {
			return this.message;
		}
	}
}
