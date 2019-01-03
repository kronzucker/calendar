package de.gfss.calendar.pdf;

import java.util.HashMap;
import java.util.Map;

public class EventCategoriesFormatting {

	private final Map<String, EventCategoryFormatting> eventCategoriesFormatting = new HashMap<>();

	public EventCategoriesFormatting() {
	}

	public void put(EventCategoryFormatting categoryFormattingInfo) {
		this.eventCategoriesFormatting.put(categoryFormattingInfo.getCategory(), categoryFormattingInfo);
	}

	public EventCategoryFormatting ofCategory(String eventCategory) {
		return eventCategoriesFormatting.get(eventCategory);
	}

	public int size() {
		return eventCategoriesFormatting.size();
	}

}
