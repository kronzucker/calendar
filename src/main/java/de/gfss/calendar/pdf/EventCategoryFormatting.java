package de.gfss.calendar.pdf;

import java.util.HashMap;
import java.util.Map;

public class EventCategoryFormatting {

	private final Map<String, EventCategoryFormattingInfo> eventCategoryFormattingInfo = new HashMap<>();
	
	public EventCategoryFormatting() {
	}
	
	public void put(String category, EventCategoryFormattingInfo categoryFormattingInfo) {
		this.eventCategoryFormattingInfo.put(category, categoryFormattingInfo);
	}
	
	public EventCategoryFormattingInfo ofCategory(String eventCategory) {
		return eventCategoryFormattingInfo.get(eventCategory);
	}
	
}
