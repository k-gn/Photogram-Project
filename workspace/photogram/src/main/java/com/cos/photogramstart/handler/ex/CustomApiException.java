package com.cos.photogramstart.handler.ex;

public class CustomApiException extends RuntimeException {

	// 객체 구분
	private static final long serialVersionUID = 1L;
	
	public CustomApiException(String message) {
		super(message);
	}
	
}
