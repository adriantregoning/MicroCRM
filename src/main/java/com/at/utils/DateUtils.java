package com.at.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static String calendarDisplayConverter(Calendar calendar, String dateFormat) {
		if(calendar==null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = calendar.getTime();
        String formatted = sdf.format(date);
        
        return formatted;
	} 
}
