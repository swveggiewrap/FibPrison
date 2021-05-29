package com.fibonacci.prison.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

	public static String convertTime(long time){
		Date date = new Date(time);
		Format format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return format.format(date) + " GMT";
	}

	public static long stringTimeToEndDate(String input) {
		long digit = 0;
		while (Character.isDigit(input.charAt(0))) {
			int charInt = Character.getNumericValue(input.charAt(0));
			digit = (digit * 10) + charInt;
			input = input.substring(1);
		}

		if (digit == 0) {
			return 0;
		}

		long duration;

		switch (input) {
			case "m":
				duration = (TimeUnit.MINUTES.toMillis(digit));
				break;
			case "h":
				duration = (TimeUnit.HOURS.toMillis(digit));
				break;
			case "d":
				duration = (TimeUnit.DAYS.toMillis(digit));
				break;
			case "w":
				duration = ((TimeUnit.DAYS.toMillis(digit)) * 7);
				break;
			case "mo":
				duration = ((TimeUnit.DAYS.toMillis(digit)) * 30);
				break;
			case "y":
				duration = (TimeUnit.DAYS.toMillis(digit) * 365);
				break;
			default:
				duration = 0;
				break;
		}

		return System.currentTimeMillis() + duration;
	}




}
