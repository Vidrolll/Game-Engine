package com.tb2dge.main.os;

public class UnsupportedOSException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnsupportedOSException(String os) {
		System.err.println("Operating system " + os + " not supported!");
	}
}
