package de.gfss.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;

import de.gfss.calendar.events.CalendarEvent;

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
	
	public void setCalendarEvent(CalendarEvent calendarEvent) {
		this.calendarEvent = calendarEvent;
	}
	
	public CalendarEvent getCalendarEvent() {
		return calendarEvent;
	}

	public boolean isWeekend() {
		return isSaturday() || isSunday();
	}

	public boolean isNrwHoliday() {
		return false;
	}

	public boolean hasEvent() {
		return calendarEvent != null;
	}
	
}
