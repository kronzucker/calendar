package de.gfss.calendar.pdf;

import java.util.HashMap;
import java.util.Map;

public class PdfEventCategories {

	private final Map<String, EventCategoryFormattingInfo> eventCategoryFormattingInfo = new HashMap<>();
	
	public PdfEventCategories() {
	}
	
	public void put(String category, EventCategoryFormattingInfo categoryFormattingInfo) {
		this.eventCategoryFormattingInfo.put(category, categoryFormattingInfo);
	}
	
	public EventCategoryFormattingInfo ofCategory(String eventCategory) {
		return eventCategoryFormattingInfo.get(eventCategory);
	}
	
}
