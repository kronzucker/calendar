package de.gfss.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;

import de.gfss.calendar.events.CalendarEvent;
import de.gfss.calendar.events.EventCategory;

public class CalendarDay {

	private final LocalDate date;
	private final boolean noSchoolDay;
	private CalendarEvent calendarEvent = null;

	public CalendarDay(LocalDate date, boolean noSchoolDay) {
		super();
		this.date = date;
		this.noSchoolDay = noSchoolDay;
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

	public boolean isNoSchoolDay() {
		return noSchoolDay;
	}

	public boolean hasEvent() {
		return calendarEvent != null;
	}

	public boolean hasEventOfCategory(EventCategory holiday) {
		if (calendarEvent == null) {
			return false;
		}
		return calendarEvent.getCategory() == holiday;
	}

}
