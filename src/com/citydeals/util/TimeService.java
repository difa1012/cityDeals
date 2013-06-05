package com.citydeals.util;

import java.util.Date;

public class TimeService {

	public static String[] getRemainingTime(Date dtTill) {

		Date dtNow = new Date();

		long diff = dtTill.getTime() - dtNow.getTime();

		// long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		return new String[] { String.valueOf(diffDays),
				String.valueOf(diffHours), String.valueOf(diffMinutes) };

	}
}
