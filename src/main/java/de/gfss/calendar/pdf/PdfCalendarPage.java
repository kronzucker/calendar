package de.gfss.calendar.pdf;

import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.layout.Document;
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
	private final EventActivititesFormatting pdfEventCategories;
	private final CalendarPeriod displayPeriod;
	private final String version;

	public PdfCalendarPage(Calendar calendar, float calendarWidth, EventActivititesFormatting pdfEventCategories,
			CalendarPeriod displayPeriod, String version) {
		this.calendar = calendar;
		this.displayCalendarWidth = calendarWidth;
		this.pdfEventCategories = pdfEventCategories;
		this.displayPeriod = displayPeriod;
		this.monthWidth = calendarWidth / displayPeriod.getNumberOfMonths();
		this.version = version;
		
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

		Paragraph footerParagraph = new Paragraph("Version " + this.version);
		footerParagraph.setFontSize(5);
		doc.add(footerParagraph);
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
			headerCell.setBold();
			headerCell.setFontColor(WebColors.getRGBColor("#ffffff"));
			headerCell.setBackgroundColor(WebColors.getRGBColor("#846666"));

			headerCell.add(new Paragraph(monthToDisplay.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN)));
			table.addHeaderCell(headerCell);

		}

	}

}
