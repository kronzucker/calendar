package de.gfss.calendar.pdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.layout.element.Cell;

import de.gfss.calendar.CalendarDay;

public class WeekendCellFormatter {

	private static final Color COLOR_SATURDAY = WebColors.getRGBColor("#e3e3e3"); // light gray
	private static final Color COLOR_SUNDAY = EventCategoryCellFormatter.COLOR_HOLIDAY;

	public static void formatCell(Cell cell, CalendarDay calendarDay) {

		if (calendarDay.isSaturday()) {
			cell.setBackgroundColor(COLOR_SATURDAY);
		}

		if (calendarDay.isSunday()) {
			cell.setBackgroundColor(COLOR_SUNDAY);
		}
	}

}
