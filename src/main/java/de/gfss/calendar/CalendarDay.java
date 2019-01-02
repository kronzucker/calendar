package de.gfss.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalendarDay {

	private final LocalDate date;
	private CalendarEvent calendarEvent = null;

	public CalendarDay(LocalDate date) {
		super();
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	public boolean isSaturday() {
		return date.getDayOfWeek().equals(DayOfWeek.SATURDAY);
	}
	
	public boolean isSunday() {
		return date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
	}
	
	public boolean hasEvent() {
		return calendarEvent != null;
	}
	
	public void setCalendarEvent(CalendarEvent calendarEvent) {
		this.calendarEvent = calendarEvent;
	}
	
	public CalendarEvent getCalendarEvent() {
		return calendarEvent;
	}
	
}
