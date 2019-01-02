package de.gfss.calendar.pdf;

import java.time.LocalDate;

public class CalendarFormatter {

	public static String getMonthTwoCharacters(LocalDate date) {

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

	public static String getMonthTwoDigit(LocalDate date) {
		return String.format("%02d", date.getDayOfMonth());
	}

}
