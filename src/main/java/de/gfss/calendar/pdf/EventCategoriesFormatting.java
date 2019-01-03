package de.gfss.calendar.pdf;

import java.util.HashMap;
import java.util.Map;

import de.gfss.calendar.CalendarEvent;

public class EventCategoriesFormatting {

	private final Map<String, EventCategoryFormatting> eventCategoriesFormatting = new HashMap<>();

	public EventCategoriesFormatting() {
	}

	public void put(EventCategoryFormatting categoryFormattingInfo) {
		this.eventCategoriesFormatting.put(categoryFormattingInfo.getCategory(), categoryFormattingInfo);
	}

	public EventCategoryFormatting ofEvent(CalendarEvent calendarEvent) {
		if (calendarEvent == null) {
			return null;
		}
		return eventCategoriesFormatting.get(calendarEvent.getEventCategory());
	}

	public int size() {
		return eventCategoriesFormatting.size();
	}

}
