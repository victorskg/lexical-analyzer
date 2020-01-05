package com.victorskg.exception;

public class AnalyzerException extends Exception {

	private String message;

	private int errorPosition;

	public AnalyzerException(String message, int errorPosition) {
		this.errorPosition = errorPosition;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public int getErrorPosition() {
		return errorPosition;
	}
}
