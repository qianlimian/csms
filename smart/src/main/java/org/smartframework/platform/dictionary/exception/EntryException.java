package org.smartframework.platform.dictionary.exception;

public class EntryException extends Exception {
	private static final long serialVersionUID = 1L;

	public EntryException() {
	}

	public EntryException(Exception e) {
		super(e.getMessage());
	}
}
