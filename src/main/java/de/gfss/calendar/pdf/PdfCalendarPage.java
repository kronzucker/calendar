package de.gfss.calendar.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import de.gfss.calendar.Calendar;
import de.gfss.calendar.CalendarDay;
import de.gfss.calendar.CalendarMonth;
import de.gfss.calendar.CalendarPeriod;

public class PdfCalendarPage {
	
	private static final Logger LOG = LoggerFactory.getLogger(PdfCalendarPage.class);

	private final Calendar calendar;
	private final float displayCalendarWidth;
	private final float monthWidth;
	private final EventCategoryFormatting pdfEventCategories;
	private final CalendarPeriod displayPeriod;

	public PdfCalendarPage(Calendar calendar, float calendarWidth, EventCategoryFormatting pdfEventCategories,
			CalendarPeriod displayPeriod) {
		this.calendar = calendar;
		this.displayCalendarWidth = calendarWidth;
		this.pdfEventCategories = pdfEventCategories;
		this.displayPeriod = displayPeriod;
		this.monthWidth = calendarWidth / displayPeriod.getNumberOfMonths();
		
		LOG.debug("new PdfCalenderPage: {}", displayPeriod.toString());
	}

	public void generateCalendarOn(Document doc) {

		Paragraph titleParagraph = new Paragraph(calendar.getTitle());
		titleParagraph.setTextAlignment(TextAlignment.CENTER);
		titleParagraph.setBold();
		doc.add(titleParagraph);

		// Creating a table
		Table table = new Table(displayPeriod.getNumberOfMonths() * 2);
		table.setWidth(displayCalendarWidth);

		printHeader(table);
		for (int dayOfMonth = 1; dayOfMonth <= 31; dayOfMonth++) {
			printDayRow(table, dayOfMonth);
		}

		// Adding Table to document
		doc.add(table);
	}

	private void printDayRow(Table table, int dayOfMonth) {

		for (YearMonth monthToDisplay : displayPeriod) {

			LOG.debug(monthToDisplay.toString());
			
			CalendarMonth calendarMonth = calendar.getMonth(monthToDisplay);
			CalendarDay calendarDay = calendarMonth.getDay(dayOfMonth);

			PdfCalendarDay pdfCalendarDayCells = new PdfCalendarDay(calendarDay, monthWidth, pdfEventCategories);
			pdfCalendarDayCells.addCellsToTable(table);
		}
	}

	private void printHeader(Table table) {

		for (YearMonth monthToDisplay : displayPeriod) {

			Cell headerCell = new Cell(1, 2);
			headerCell.setTextAlignment(TextAlignment.CENTER);

			headerCell.add(new Paragraph(monthToDisplay.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN)));
			table.addHeaderCell(headerCell);

		}

	}

}
