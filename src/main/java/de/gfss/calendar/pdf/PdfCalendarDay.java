package de.gfss.calendar.pdf;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.itextpdf.kernel.colors.Color;
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

	private static final Logger LOG = LoggerFactory.getLogger(PdfCalendarDay.class);

	private static final float COLUMN_DAY_NUMBER_WIDTH = 20;
	private static final float COLUMN_DAY_FONT_SIZE = 7.5f;

	private final Style cellStyleDayNumber = new Style();
	private final Style cellStyleDayContent = new Style();

	private final Cell dayNumberCell = new Cell();
	private final Cell dayContentCell = new Cell();
	private final CalendarDay calendarDay;
	private final EventCategoryFormatting eventCategoryFormattingInfo;

	public PdfCalendarDay(CalendarDay calendarDay, float dayWidth, EventCategoriesFormatting eventCategories) {
		this.calendarDay = calendarDay;
		this.eventCategoryFormattingInfo = (calendarDay == null) ? null
				: eventCategories.ofEvent(calendarDay.getCalendarEvent());

		initializeCellsAndStyles(dayWidth);

		// Check for Valid Date
		if (calendarDay == null) {
			dayNumberCell.setBorder(Border.NO_BORDER);
			dayContentCell.setBorder(Border.NO_BORDER);
			return;
		}

		markCalendarDayAsHoliday();
		markCalendarDayAsWeekend();
		markCalendarDayWithEventCategoryColor();
		fillIconOrDayNumber();
		fillEventDescription();

	}

	private void initializeCellsAndStyles(float dayWidth) {
		// Cell Day Number
		this.cellStyleDayNumber.setMaxWidth(COLUMN_DAY_NUMBER_WIDTH);
		this.cellStyleDayNumber.setFontSize(COLUMN_DAY_FONT_SIZE);
		this.cellStyleDayNumber.setTextAlignment(TextAlignment.CENTER);
		this.cellStyleDayNumber.setPadding(0);
		this.cellStyleDayNumber.setBorderRight(Border.NO_BORDER);

		dayNumberCell.addStyle(cellStyleDayNumber);

		// Cell Day Content
		this.cellStyleDayContent.setMaxWidth(dayWidth - COLUMN_DAY_NUMBER_WIDTH);
		this.cellStyleDayContent.setFontSize(COLUMN_DAY_FONT_SIZE);
		this.cellStyleDayContent.setBorderLeft(Border.NO_BORDER);

		dayContentCell.addStyle(cellStyleDayContent);

	}

	private void fillEventDescription() {
		if (!calendarDay.hasEvent()) {
			return;
		}

		CalendarEvent event = calendarDay.getCalendarEvent();

		Paragraph eventDescription = new Paragraph();
		eventDescription.add(event.getDescription());
		eventDescription.setBold();
		eventDescription.setTextAlignment(TextAlignment.CENTER);

		Paragraph eventLocationTime = new Paragraph();
		eventLocationTime.add(event.getLocation());
		eventLocationTime.setFontSize(5);
		eventLocationTime.setTextAlignment(TextAlignment.CENTER);

		if (calendarDay.getCalendarEvent().getStartTime() != null) {
			eventLocationTime.add(" ");
			eventLocationTime.add(event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		}
		dayContentCell.add(eventDescription).add(eventLocationTime);
	}

	private void fillIconOrDayNumber() {

		if (calendarDay.hasEvent() && (eventCategoryFormattingInfo != null)
				&& (eventCategoryFormattingInfo.isIconDefined())) {
			Image icon = eventCategoryFormattingInfo.getIcon();
			float imageHeight = icon.getImageHeight();

			icon.scaleToFit(COLUMN_DAY_NUMBER_WIDTH, imageHeight);
			dayNumberCell.add(icon);
			dayNumberCell.setPaddingTop(1).setPaddingBottom(1);

		} else {
			Paragraph paragraphDayNumber = new Paragraph(CalendarFormatter.getMonthTwoDigit(calendarDay.getDate()));
			dayNumberCell.add(paragraphDayNumber);

			Paragraph paragraphDayName = new Paragraph(CalendarFormatter.getMonthTwoCharacters(calendarDay.getDate()));
			dayNumberCell.add(paragraphDayName);
		}
	}

	private void markCalendarDayWithEventCategoryColor() {
		if (!calendarDay.hasEvent()) {
			return;
		}

		if (eventCategoryFormattingInfo == null) {
			LOG.warn("event category {} konnte nicht ermittelt werden",
					calendarDay.getCalendarEvent().getEventCategory());
			return;
		}

		if (eventCategoryFormattingInfo.isBackgroundColorDefined()) {
			dayNumberCell.setBackgroundColor(eventCategoryFormattingInfo.getBackgroundColor());
			dayContentCell.setBackgroundColor(eventCategoryFormattingInfo.getBackgroundColor());
		}

	}

	private void markCalendarDayAsHoliday() {
		if (!calendarDay.isNrwHoliday()) {
			return;
		}

		Color lightGrayBlue = WebColors.getRGBColor("#c1d0f0");
		dayNumberCell.setBackgroundColor(lightGrayBlue);
	}

	private void markCalendarDayAsWeekend() {
		if (!calendarDay.isWeekend()) {
			return;
		}

		Color backgroundColor;
		backgroundColor = calendarDay.isSaturday() ? WebColors.getRGBColor("#f2f2f2") : null;
		backgroundColor = calendarDay.isSunday() ? WebColors.getRGBColor("#e6e6e6") : null;

		dayNumberCell.setBackgroundColor(backgroundColor);
		dayContentCell.setBackgroundColor(backgroundColor);
	}

	public void addCellsToTable(Table table) {
		table.addCell(dayNumberCell);
		table.addCell(dayContentCell);
	}

}
