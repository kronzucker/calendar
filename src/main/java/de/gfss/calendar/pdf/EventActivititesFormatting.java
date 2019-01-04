package de.gfss.calendar.pdf;

import java.util.HashMap;
import java.util.Map;

import de.gfss.calendar.events.CalendarEvent;

public class EventActivititesFormatting {

	private final Map<String, EventActivityFormatting> eventActivititiesFormatting = new HashMap<>();

	public EventActivititesFormatting() {
	}

	public void put(EventActivityFormatting categoryFormattingInfo) {
		this.eventActivititiesFormatting.put(categoryFormattingInfo.getCategory(), categoryFormattingInfo);
	}

	public EventActivityFormatting ofEvent(CalendarEvent calendarEvent) {
		if (calendarEvent == null) {
			return null;
		}
		return eventActivititiesFormatting.get(calendarEvent.getActivity());
	}

	public int size() {
		return eventActivititiesFormatting.size();
	}

}
