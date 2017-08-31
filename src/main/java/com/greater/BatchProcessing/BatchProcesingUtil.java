package com.greater.BatchProcessing;

import java.time.LocalDateTime;

public class BatchProcesingUtil {
	public String getDateandTime() {
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		int hour = now.getHour();
		int minute = now.getMinute();	
		StringBuilder dateandtime = new StringBuilder();
		dateandtime.append(year).append(month).append(day).append(hour).append(minute);		
		return dateandtime.toString();
	}
}
