package de.gfss.calendar.pdf;

import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import de.gfss.calendar.CalendarDay;

public class PdfCalendarDay {

	private static final float COLUMN_DAY_NUMBER_WIDTH = 12;
	private static final float COLUMN_DAY_FONT_SIZE = 7;

	private final Style cellStyleDayNumber;
	private final Style cellStyleDayContent;

	private final Cell dayNumberCell;
	private final Cell dayContentCell;
	private final CalendarDay calendarDay;

	public PdfCalendarDay(CalendarDay calendarDay, float dayWidth) {
		this.calendarDay = calendarDay;

		// Cell Day Number
		this.cellStyleDayNumber = new Style();
		this.cellStyleDayNumber.setMaxWidth(COLUMN_DAY_NUMBER_WIDTH);
		this.cellStyleDayNumber.setFontSize(COLUMN_DAY_FONT_SIZE);
		this.cellStyleDayNumber.setPaddings(0, 0, 0, 0);
		this.cellStyleDayNumber.setTextAlignment(TextAlignment.CENTER);
		this.cellStyleDayNumber.setHeight(22);
		this.cellStyleDayNumber.setBorderRight(Border.NO_BORDER);

		dayNumberCell = new Cell();
		dayNumberCell.addStyle(cellStyleDayNumber);

		// Cell Day Content
		this.cellStyleDayContent = new Style();
		this.cellStyleDayContent.setMaxWidth(dayWidth - COLUMN_DAY_NUMBER_WIDTH);
		this.cellStyleDayContent.setFontSize(7);
		this.cellStyleDayContent.setPadding(2);
		this.cellStyleDayContent.setBorderLeft(Border.NO_BORDER);

		this.dayContentCell = new Cell();
		dayContentCell.addStyle(cellStyleDayContent);

		// Check for Valid Date
		if (calendarDay == null) {
			dayNumberCell.setBorder(Border.NO_BORDER);
			dayContentCell.setBorder(Border.NO_BORDER);
		} else {
			addWeekendStyle(dayContentCell);
			addWeekendStyle(dayNumberCell);

			dayNumberCell.add(new Paragraph(CalendarFormatter.getMonthTwoDigit(calendarDay.getDate())));
			dayNumberCell.add(new Paragraph(CalendarFormatter.getMonthTwoCharacters(calendarDay.getDate())));

			dayContentCell.add(new Paragraph("nix").setTextAlignment(TextAlignment.CENTER));
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
