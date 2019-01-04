package de.gfss.calendar.events;

public enum EventCategory {
	INFO, HOLIDAY, EVENT, HIGHLIGHT;

	public static EventCategory fromString(String category) {
		switch (category) {
		case "info":
			return EventCategory.INFO;
		case "holiday":
			return EventCategory.HOLIDAY;
		case "event":
			return EventCategory.EVENT;
		case "highlight":
			return EventCategory.HIGHLIGHT;
		}

		return null;
	}
}
