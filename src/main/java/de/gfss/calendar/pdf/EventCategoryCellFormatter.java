package de.gfss.calendar.pdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.layout.element.Cell;

import de.gfss.calendar.events.CalendarEvent;
import de.gfss.calendar.events.EventCategory;

public class EventCategoryCellFormatter {

	public static final Color COLOR_HOLIDAY = WebColors.getRGBColor("#C0C0C0"); // dark gray
	private static final Color COLOR_EVENT = WebColors.getRGBColor("#FFFFCC"); // light yellow
	private static final Color COLOR_HIGHLIGHT = WebColors.getRGBColor("#FFFF66"); // strong yellow

	public static void formatCell(Cell cell, CalendarEvent calendarEvent) {

		if (calendarEvent == null) {
			return;
		}

		switch (calendarEvent.getCategory()) {
		case EVENT:
			cell.setBackgroundColor(COLOR_EVENT);
			break;
		case HIGHLIGHT:
			cell.setBackgroundColor(COLOR_HIGHLIGHT);
			break;
		case HOLIDAY:
			cell.setBackgroundColor(COLOR_HOLIDAY);
			break;
		case INFO:
			/* No cell formatting for INFO category */
			break;
		default:
			throw new IllegalArgumentException(calendarEvent.getCategory().toString());
		}
	}

}
