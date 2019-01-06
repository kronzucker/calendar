package de.gfss.calendar;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.TreeMap;

import de.gfss.calendar.events.CalendarEvent;

public class CalendarMonth {

	private final YearMonth yearMonth;
	private final TreeMap<LocalDate, CalendarDay> days = new TreeMap<>();

	public CalendarMonth(YearMonth yearMonth, Vacation vacation) {
		super();
		this.yearMonth = yearMonth;

		for (int dayOfMonth = 1; dayOfMonth <= yearMonth.lengthOfMonth(); dayOfMonth++) {

			LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), dayOfMonth);
			boolean noSchoolDay = vacation.contains(date);
			
			CalendarDay calendarDay = new CalendarDay(date, noSchoolDay);
			days.put(date, calendarDay);
		}
	}

	public void addEvent(CalendarEvent event) {
		// Currently only one event is supported per day
		days.get(event.getDate()).setCalendarEvent(event);
	}

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public CalendarDay getDay(int dayOfMonth) {
		try {
			LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), dayOfMonth);
			return days.get(date);
			
		} catch (DateTimeException ex) {
			/* consumed invalid date */
			return null;
		}
	}

}
