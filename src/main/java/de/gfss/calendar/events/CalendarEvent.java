package de.gfss.calendar.events;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

import lombok.Builder;

@Builder
public class CalendarEvent {

	private final LocalDate date;
	private final LocalTime startTime;
	private final String description;
	private final String location;
	private final EventCategory category;
	private final String activity;

	public CalendarEvent(LocalDate date, LocalTime startTime, String description, String location,
			EventCategory category, String activity) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.description = description;
		this.location = location;
		this.activity = activity;
		this.category = category;
	}

	public YearMonth getYearMonth() {
		return YearMonth.of(date.getYear(), date.getMonth());
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

	public EventCategory getCategory() {
		return category;
	}
	
	public String getActivity() {
		return activity;
	}

}
