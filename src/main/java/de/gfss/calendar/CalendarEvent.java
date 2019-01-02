package de.gfss.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

public class CalendarEvent {

	private final LocalDate date;
	private final LocalTime startTime;
	private final String description;
	private final String location;
	private final YearMonth yearMonth;
	private final String eventCategory;
	
	public CalendarEvent(LocalDate date, LocalTime startTime, String description, String location, String eventCategory) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.description = description;
		this.location = location;
		this.yearMonth = YearMonth.of(date.getYear(), date.getMonth());
		this.eventCategory = eventCategory;
	}
	
	public YearMonth getYearMonth() {
		return yearMonth;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getEventCategory() {
		return eventCategory;
	}
	
}
