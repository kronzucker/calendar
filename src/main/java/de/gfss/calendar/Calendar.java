package de.gfss.calendar;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import de.gfss.calendar.events.CalendarEvent;

public class Calendar {

	private final TreeMap<YearMonth, CalendarMonth> months = new TreeMap<>();
	private final List<CalendarEvent> events = new ArrayList<>();
	private final String title;
	
	public Calendar(String title, CalendarPeriod calendarPeriod, List<CalendarEvent> calendarEvents, Vacation vacation) {
		
		for (YearMonth yearMonth : calendarPeriod) {
			CalendarMonth month = new CalendarMonth(yearMonth, vacation);
			months.put(yearMonth, month);
		}
		
		for (CalendarEvent event : calendarEvents) {
			addEvent(event);
		}
		
		this.title = title;
	}
	
	private void addEvent(CalendarEvent event) {
		events.add(event);
		CalendarMonth calMonth = months.get(event.getYearMonth());
		if (calMonth != null) {
			calMonth.addEvent(event);
		}
	}
	
	public int getNumberOfMonths() {
		return months.size();
	}

	public String getTitle() {
		return title;
	}

	public CalendarMonth getMonth(YearMonth yearMonth) {
		return months.get(yearMonth);
	}
	
	
}
