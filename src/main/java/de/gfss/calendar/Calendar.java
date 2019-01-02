package de.gfss.calendar;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public class Calendar {

	private final TreeMap<YearMonth, CalendarMonth> months = new TreeMap<>();
	private final List<CalendarEvent> events = new ArrayList<>();
	private final String title;
	
	public Calendar(String title, YearMonth firstMonth, int numberOfMonths) {
		
		for (int monthIndex = 0; monthIndex < numberOfMonths; monthIndex++) {
			YearMonth yearMonth = firstMonth.plusMonths(monthIndex);
			CalendarMonth month = new CalendarMonth(yearMonth);
			months.put(yearMonth, month);
		}
		
		this.title = title;
	}
	
	public void addEvent(CalendarEvent event) {
		events.add(event);
		months.get(event.getYearMonth()).addEvent(event);
	}
	
	public int getNumberOfMonths() {
		return months.size();
	}

	public String getTitle() {
		return title;
	}

	public Collection<CalendarMonth> getMonths() {
		return months.values();
	}
	
}
