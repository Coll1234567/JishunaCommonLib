package me.jishuna.commonlib;

import java.util.regex.Pattern;

public class NumberUtils {

	private static final Pattern numbersOnly = Pattern.compile("^[0-9]+$");

	public static boolean isNumber(String arg) {
		return numbersOnly.matcher(arg).find();
	}

}
