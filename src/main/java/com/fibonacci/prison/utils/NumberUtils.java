package com.fibonacci.prison.utils;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NumberUtils {

	public static String intToSuffixedNumber(int number) {
		if (number < 1000) {
			return "" + number;
		} else {
			int exponent = (int) (Math.log(number) / Math.log(1000));
			return String.format("%.2f%c", number / Math.pow(1000, exponent), "kMBTQ".charAt(exponent - 1));
		}
	}

	public static String longToSuffixedNumber(long number) {
		if (number < 1000) {
			return "" + number;
		} else {
			int exponent = (int) (Math.log(number) / Math.log(1000));
			return String.format("%.2f%c", number / Math.pow(1000, exponent), "kMBTQ".charAt(exponent - 1));
		}
	}

	public static String doubleToSuffixedNumber(double number) {
		if (number < 1000) {
			return String.format("%.2f", number);
		} else {
			int exponent = (int) (Math.log(number) / Math.log(1000));
			return String.format("%.2f%c", number / Math.pow(1000, exponent), "kMBTQ".charAt(exponent - 1));
		}
	}

	public static String amTimeToFormattedString(long seconds) {
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24L);
		long minutes = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
		long secondCount = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

		if (day == 0 && hours == 0 && minutes == 0 && secondCount == 0) {
			return ColorUtils.colorize("&e0d 0h 0m 0s");
		}

		StringBuilder builder = new StringBuilder();
		if (day != 0) {
			builder.append("&e" + day + "d ");
		}

		if (hours != 0) {
			builder.append("&e" + hours + "h ");
		}

		if (minutes != 0) {
			builder.append("&e" + minutes + "m ");
		}

		if (secondCount != 0) {
			builder.append("&e" + secondCount + "s");
		}

		return ColorUtils.colorize(builder.toString());
	}


}
