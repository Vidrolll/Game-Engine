package com.tb2dge.main.util;

public class Console {
	public static void log(Object...input) {
		for(Object object : input) {
			String s = String.valueOf(object);
	        System.out.print(s);
		}
		System.out.println();
	}
	public static void warn(Object...input) {
		for(Object object : input) {
			String s = String.valueOf(object);
	        System.err.print(s);
		}
		System.err.println();
	}
}