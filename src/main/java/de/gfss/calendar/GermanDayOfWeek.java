package de.gfss.calendar;

import java.time.LocalDate;

public class GermanDayOfWeek {

	public static String getTwoCharacterString(LocalDate date) {
		
		switch (date.getDayOfWeek()) {
		case FRIDAY:
			return "Fr";
		case MONDAY:
			return "Mo";
		case SATURDAY:
			return "Sa";
		case SUNDAY:
			return "So";
		case THURSDAY:
			return "Do";
		case TUESDAY:
			return "Di";
		case WEDNESDAY:
			return "Mi";
		}
		return "";
	}

}
