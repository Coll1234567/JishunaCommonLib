package me.jishuna.commonlib.utils;

import java.util.concurrent.ThreadLocalRandom;

public class NumberUtils {

	public static double getRandomDouble(double min, double max) {
		return ThreadLocalRandom.current().nextDouble() * (max - min);
	}

}
