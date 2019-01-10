package de.gfss.calendar.pdf;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.itextpdf.io.util.EnumUtil;
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
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import de.gfss.calendar.CalendarDay;
import de.gfss.calendar.events.CalendarEvent;
import de.gfss.calendar.events.EventCategory;

public class PdfCalendarDay {

	private static final Logger LOG = LoggerFactory.getLogger(PdfCalendarDay.class);

	private static final float COLUMN_DAY_NUMBER_WIDTH = 20;
	private static final float CELL_HEIGHT = 22;
	private static final float TEXT_HEADLINE_FONT_SIZE = 9f;
	private static final float TEXT_HEADLINE_LEADING = TEXT_HEADLINE_FONT_SIZE + 1;
	private static final float TEXT_LOCATION_FONT_SIZE = 6f;
	private static final float TEXT_LOCATION_LEADING = TEXT_LOCATION_FONT_SIZE + 2;
	private static final float TEXT_DAYNUMBER_FONT_SIZE = 9f;
	private static final float TEXT_DAYNUMBER_LEADING = TEXT_DAYNUMBER_FONT_SIZE + 1;

	private final Style cellStyleDayNumber = new Style();
	private final Style cellStyleDayContent = new Style();

	private final Cell dayNumberCell = new Cell();
	private final Cell dayContentCell = new Cell();
	private final CalendarDay calendarDay;
	private final EventActivityFormatting eventActivityFormattingInfo;

	public PdfCalendarDay(CalendarDay calendarDay, float dayWidth,
			EventActivititesFormatting eventActivitiesFormatting) {
		this.calendarDay = calendarDay;
		this.eventActivityFormattingInfo = (calendarDay == null) ? null
				: eventActivitiesFormatting.ofEvent(calendarDay.getCalendarEvent());

		initializeCellsAndStyles(dayWidth);
		
		// Check for Valid Date
		if (calendarDay == null) {
			dayNumberCell.setBorder(Border.NO_BORDER);
			dayContentCell.setBorder(Border.NO_BORDER);
			return;
		}

		LOG.debug(calendarDay.getDate().toString());
		if (calendarDay.hasEvent()) {
			LOG.debug(calendarDay.getCalendarEvent().getDescription().toString());
		}

		markCalendarDayAsWeekend();
		markCalendarDayWithEventCategoryColor();
		markCalendarDayAsHoliday();
		fillIconOrDayNumber();
		fillEventDescription();

	}

	private void initializeCellsAndStyles(float dayWidth) {
		// Cell Day Number
		this.cellStyleDayNumber.setMaxWidth(COLUMN_DAY_NUMBER_WIDTH);
		this.cellStyleDayNumber.setHeight(CELL_HEIGHT);
		this.cellStyleDayNumber.setFontSize(TEXT_DAYNUMBER_FONT_SIZE);
		this.cellStyleDayNumber.setTextAlignment(TextAlignment.CENTER);
		this.cellStyleDayNumber.setVerticalAlignment(VerticalAlignment.MIDDLE);
		this.cellStyleDayNumber.setPadding(0);
		this.cellStyleDayNumber.setBorderRight(Border.NO_BORDER);

		dayNumberCell.addStyle(cellStyleDayNumber);

		// Cell Day Content
		this.cellStyleDayContent.setMaxWidth(dayWidth - COLUMN_DAY_NUMBER_WIDTH);
		this.cellStyleDayContent.setHeight(CELL_HEIGHT);
		this.cellStyleDayContent.setBorderLeft(Border.NO_BORDER);
		this.cellStyleDayContent.setHorizontalAlignment(HorizontalAlignment.CENTER);
		this.cellStyleDayContent.setVerticalAlignment(VerticalAlignment.MIDDLE);
		this.cellStyleDayContent.setPadding(0);

		dayContentCell.addStyle(cellStyleDayContent);

	}

	private void fillEventDescription() {
		if (!calendarDay.hasEvent()) {
			return;
		}

		CalendarEvent event = calendarDay.getCalendarEvent();

		Paragraph eventDescription = new Paragraph();
		eventDescription.add(event.getDescription());
		
		if ((event.getCategory() == EventCategory.EVENT) || (event.getCategory() == EventCategory.HIGHLIGHT)) {
			eventDescription.setBold();
		}
		eventDescription.setFontSize(TEXT_HEADLINE_FONT_SIZE);
		eventDescription.setFixedLeading(TEXT_HEADLINE_LEADING);
		eventDescription.setCharacterSpacing(0.5f);
		eventDescription.setTextAlignment(TextAlignment.CENTER);

		Paragraph eventLocationTime = new Paragraph();
		eventLocationTime.add(event.getLocation());
		eventLocationTime.setFontSize(TEXT_LOCATION_FONT_SIZE);
		eventLocationTime.setFixedLeading(TEXT_LOCATION_LEADING);
		eventLocationTime.setTextAlignment(TextAlignment.CENTER);

		if (calendarDay.getCalendarEvent().getStartTime() != null) {
			eventLocationTime.add(" ");
			eventLocationTime.add(event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		}
		dayContentCell.add(eventDescription).add(eventLocationTime);
	}

	private void fillIconOrDayNumber() {

		if (calendarDay.hasEvent() && (eventActivityFormattingInfo != null)
				&& (eventActivityFormattingInfo.isIconDefined())) {
			Image icon = eventActivityFormattingInfo.getIcon();
			float imageHeight = icon.getImageHeight();

			icon.scaleToFit(COLUMN_DAY_NUMBER_WIDTH, imageHeight);
			dayNumberCell.add(icon);

		} else {
			Paragraph paragraphDayNumber = new Paragraph(CalendarFormatter.getMonthTwoDigit(calendarDay.getDate()));
			paragraphDayNumber.setFontSize(TEXT_DAYNUMBER_FONT_SIZE);
			paragraphDayNumber.setFixedLeading(TEXT_DAYNUMBER_LEADING);
			dayNumberCell.add(paragraphDayNumber);

			Paragraph paragraphDayName = new Paragraph(CalendarFormatter.getMonthTwoCharacters(calendarDay.getDate()));
			dayNumberCell.add(paragraphDayName);
			paragraphDayName.setFontSize(TEXT_DAYNUMBER_FONT_SIZE);
			paragraphDayName.setFixedLeading(TEXT_DAYNUMBER_LEADING);
		}
	}

	private void markCalendarDayWithEventCategoryColor() {

		if (!calendarDay.hasEvent()) {
			return;
		}

		EventCategoryCellFormatter.formatCell(dayNumberCell, calendarDay.getCalendarEvent());
		EventCategoryCellFormatter.formatCell(dayContentCell, calendarDay.getCalendarEvent());
	}

	private void markCalendarDayAsHoliday() {
		if (!calendarDay.isNoSchoolDay() || calendarDay.isWeekend() || calendarDay.hasEventOfCategory(EventCategory.HOLIDAY)) {
			return;
		}

		Color lightGrayBlue = WebColors.getRGBColor("#c1d0f0");
		dayNumberCell.setBackgroundColor(lightGrayBlue);
	}

	private void markCalendarDayAsWeekend() {
		WeekendCellFormatter.formatCell(dayNumberCell, calendarDay);
		WeekendCellFormatter.formatCell(dayContentCell, calendarDay);
	}

	public void addCellsToTable(Table table) {
		table.addCell(dayNumberCell);
		table.addCell(dayContentCell);
	}

}
