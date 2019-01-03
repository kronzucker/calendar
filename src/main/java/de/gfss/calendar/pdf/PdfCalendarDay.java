package de.gfss.calendar.pdf;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;

import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import de.gfss.calendar.CalendarDay;
import de.gfss.calendar.CalendarEvent;

public class PdfCalendarDay {

	private static final float COLUMN_DAY_NUMBER_WIDTH = 20;
	private static final float COLUMN_DAY_FONT_SIZE = 7.5f;

	private final Style cellStyleDayNumber;
	private final Style cellStyleDayContent;

	private final Cell dayNumberCell;
	private final Cell dayContentCell;
	private final CalendarDay calendarDay;
	private final EventCategoriesFormatting eventCategories;

	public PdfCalendarDay(CalendarDay calendarDay, float dayWidth, EventCategoriesFormatting eventCategories) {
		this.calendarDay = calendarDay;
		this.eventCategories = eventCategories;

		// Cell Day Number
		this.cellStyleDayNumber = new Style();
		this.cellStyleDayNumber.setMaxWidth(COLUMN_DAY_NUMBER_WIDTH);
		this.cellStyleDayNumber.setFontSize(COLUMN_DAY_FONT_SIZE);
		this.cellStyleDayNumber.setTextAlignment(TextAlignment.CENTER);
		this.cellStyleDayNumber.setPadding(0);
		this.cellStyleDayNumber.setBorderRight(Border.NO_BORDER);

		dayNumberCell = new Cell();
		dayNumberCell.addStyle(cellStyleDayNumber);

		// Cell Day Content
		this.cellStyleDayContent = new Style();
		this.cellStyleDayContent.setMaxWidth(dayWidth - COLUMN_DAY_NUMBER_WIDTH);
		this.cellStyleDayContent.setFontSize(COLUMN_DAY_FONT_SIZE);
		this.cellStyleDayContent.setBorderLeft(Border.NO_BORDER);

		this.dayContentCell = new Cell();
		dayContentCell.addStyle(cellStyleDayContent);

		// Check for Valid Date
		if (calendarDay == null) {
			dayNumberCell.setBorder(Border.NO_BORDER);
			dayContentCell.setBorder(Border.NO_BORDER);
		} else {
			addWeekendStyle(dayNumberCell);
			addWeekendStyle(dayContentCell);

			CalendarEvent event = calendarDay.getCalendarEvent();
			boolean printDayNumber = true;
			if (event != null) {
				EventCategoryFormatting categoryFormattingInfo = eventCategories.ofCategory(event.getEventCategory());
				if (categoryFormattingInfo != null) {
					categoryFormattingInfo.formatBackground(dayNumberCell);
					categoryFormattingInfo.formatBackground(dayContentCell);
					
					printDayNumber = !categoryFormattingInfo.insertIcon(dayNumberCell, COLUMN_DAY_NUMBER_WIDTH);
				}
				
				Paragraph eventDescription = new Paragraph();
				eventDescription.add(event.getDescription());
				eventDescription.setBold();
				eventDescription.setTextAlignment(TextAlignment.CENTER);
				
				Paragraph eventLocationTime = new Paragraph();
				eventLocationTime.add(event.getLocation());
				eventLocationTime.setFontSize(5);
				eventLocationTime.setTextAlignment(TextAlignment.CENTER);
				
				if (event.getStartTime() != null) {
					eventLocationTime.add(" ");
					eventLocationTime.add(event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
				}
				dayContentCell.add(eventDescription).add(eventLocationTime);
			} 
			
			if (printDayNumber) {
				Paragraph p1 = new Paragraph(CalendarFormatter.getMonthTwoDigit(calendarDay.getDate()));
				dayNumberCell.add(p1);
				
				Paragraph p2 = new Paragraph(CalendarFormatter.getMonthTwoCharacters(calendarDay.getDate()));
				dayNumberCell.add(p2);
			}
			
		}
	}

	private void addWeekendStyle(Cell cell) {

		if (calendarDay.isSaturday()) {
			cell.setBackgroundColor(WebColors.getRGBColor("#f2f2f2"));
		}

		if (calendarDay.isSunday()) {
			cell.setBackgroundColor(WebColors.getRGBColor("#e6e6e6"));
		}
	}

	public void addCellsToTable(Table table) {
		table.addCell(dayNumberCell);
		table.addCell(dayContentCell);
	}

}
