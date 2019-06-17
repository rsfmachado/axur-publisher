package com.publisher.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PublisherUtil {

	public static String getCurrentTimestamp() {
		DateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
		Calendar currentCal = Calendar.getInstance();
		return df.format(currentCal.getTime());
	}
}
