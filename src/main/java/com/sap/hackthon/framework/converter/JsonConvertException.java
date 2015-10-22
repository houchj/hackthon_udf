package com.sap.hackthon.framework.converter;

import org.springframework.http.converter.HttpMessageNotReadableException;

public class JsonConvertException extends HttpMessageNotReadableException {

	private static final long serialVersionUID = -1187705011579715339L;

	public JsonConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonConvertException(String message) {
		super(message);
	}
	
	
}
