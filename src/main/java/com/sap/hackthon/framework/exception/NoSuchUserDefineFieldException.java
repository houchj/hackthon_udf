package com.sap.hackthon.framework.exception;

public class NoSuchUserDefineFieldException extends NoSuchMethodException {

	private static final long serialVersionUID = 176106589526925699L;
	
	private final static String indicator = "No such user defined field: ";

	public NoSuchUserDefineFieldException(String field) {
		super(indicator + field);
	}

	
}
