package com.tb2dge.main.util;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ArrayUtils {
	public static <T> T[] copyArray(T[] array) {
		@SuppressWarnings("unchecked")
		T[] newArray = (T[])Array.newInstance(array.getClass().getComponentType(),array.length);
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	public static int[] copyArray(int[] array) {
		int[] newArray = new int[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	public static byte[] copyArray(byte[] array) {
		byte[] newArray = new byte[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	public static short[] copyArray(short[] array) {
		short[] newArray = new short[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	public static long[] copyArray(long[] array) {
		long[] newArray = new long[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	public static float[] copyArray(float[] array) {
		float[] newArray = new float[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	public static double[] copyArray(double[] array) {
		double[] newArray = new double[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	public static char[] copyArray(char[] array) {
		char[] newArray = new char[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}
	public static <T> int indexOf(T[] array, T input) {
		for(int i = 0; i < array.length; i++) {
			if(array[i].equals(input))
				return i;
		}
		return -1;
	}
	public static int indexOf(int[] array, int input) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == input)
				return i;
		}
		return -1;
	}
	public static int indexOf(byte[] array, byte input) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == input)
				return i;
		}
		return -1;
	}
	public static int indexOf(short[] array, short input) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == input)
				return i;
		}
		return -1;
	}
	public static int indexOf(long[] array, long input) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == input)
				return i;
		}
		return -1;
	}
	public static int indexOf(float[] array, float input) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == input)
				return i;
		}
		return -1;
	}
	public static int indexOf(double[] array, double input) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == input)
				return i;
		}
		return -1;
	}
	public static int indexOf(char[] array, char input) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == input)
				return i;
		}
		return -1;
	}
	public static int[] parseInt(String[] array) {
		int[] newArray = new int[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = Integer.parseInt(array[i]);
		}
		return newArray;
	}
	public static double[] parseDouble(String[] array) {
		double[] newArray = new double[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = Double.parseDouble(array[i]);
		}
		return newArray;
	}
	public static long[] parseLong(String[] array) {
		long[] newArray = new long[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = Long.parseLong(array[i]);
		}
		return newArray;
	}
	public static byte[] parseByte(String[] array) {
		byte[] newArray = new byte[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = Byte.parseByte(array[i]);
		}
		return newArray;
	}
	public static short[] parseShort(String[] array) {
		short[] newArray = new short[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = Short.parseShort(array[i]);
		}
		return newArray;
	}
	public static float[] parseFloat(String[] array) {
		float[] newArray = new float[array.length];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = Float.parseFloat(array[i]);
		}
		return newArray;
	}
	public static int[] parseInt(ArrayList<Integer> array) {
		int[] newArray = new int[array.size()];
		for(int i = 0; i < array.size(); i++) {
			newArray[i] = array.get(i);
		}
		return newArray;
	}
	public static double[] parseDouble(ArrayList<Double> array) {
		double[] newArray = new double[array.size()];
		for(int i = 0; i < array.size(); i++) {
			newArray[i] = array.get(i);
		}
		return newArray;
	}
	public static long[] parseLong(ArrayList<Long> array) {
		long[] newArray = new long[array.size()];
		for(int i = 0; i < array.size(); i++) {
			newArray[i] = array.get(i);
		}
		return newArray;
	}
	public static byte[] parseByte(ArrayList<Byte> array) {
		byte[] newArray = new byte[array.size()];
		for(int i = 0; i < array.size(); i++) {
			newArray[i] = array.get(i);
		}
		return newArray;
	}
	public static short[] parseShort(ArrayList<Short> array) {
		short[] newArray = new short[array.size()];
		for(int i = 0; i < array.size(); i++) {
			newArray[i] = array.get(i);
		}
		return newArray;
	}
	public static float[] parseFloat(ArrayList<Float> array) {
		float[] newArray = new float[array.size()];
		for(int i = 0; i < array.size(); i++) {
			newArray[i] = array.get(i);
		}
		return newArray;
	}
	public static <T> T[] toArray(ArrayList<T> lines, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		T[] newArray = (T[])Array.newInstance(clazz,lines.size());
		for(int i = 0; i < newArray.length; i++) {
			newArray[i] = lines.get(i);
		}
		return newArray;
	}
	public static int[] toArray(ArrayList<Integer> ints) {
		int[] newArray = new int[ints.size()];
		for(int i = 0; i < ints.size(); i++) {
			newArray[i] = ints.get(i);
		}
		return newArray;
	}
}