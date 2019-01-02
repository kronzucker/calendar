package de.gfss.calendar.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

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

public class PdfCalendar {

	private final Calendar calendar;
	private final float calendarWidth;
	private final float monthWidth;

	public PdfCalendar(Calendar calendar, float calendarWidth) {
		this.calendar = calendar;
		this.calendarWidth = calendarWidth;
		this.monthWidth = calendarWidth / calendar.getNumberOfMonths();
	}

	public void generateFile(File file) throws FileNotFoundException {

		// Creating a PdfDocument object
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));

		// Creating a Document object
		Document doc = new Document(pdfDoc);

		Paragraph titleParagraph = new Paragraph(calendar.getTitle());
		titleParagraph.setTextAlignment(TextAlignment.CENTER);
		doc.add(titleParagraph);

		// Creating a table
		Table table = new Table(calendar.getNumberOfMonths() * 2);
		table.setWidth(calendarWidth);

		printHeader(table);
		for (int dayOfMonth = 1; dayOfMonth <= 31; dayOfMonth++) {
			printDayRow(table, dayOfMonth);
		}

		// Adding Table to document
		doc.add(table);

		// Closing the document
		doc.close();
		System.out.println("Table created successfully..");
	}

	private void printDayRow(Table table, int dayOfMonth) {

		for (CalendarMonth month : calendar.getMonths()) {

			CalendarDay calendarDay = month.getDay(dayOfMonth);

			PdfCalendarDay pdfCalendarDay = new PdfCalendarDay(calendarDay, monthWidth);
			pdfCalendarDay.addCellsToTable(table);
		}
	}

	private void printHeader(Table table) {

		for (CalendarMonth month : calendar.getMonths()) {

			Cell headerCell = new Cell(1, 2);
			headerCell.setTextAlignment(TextAlignment.CENTER);

			headerCell
					.add(new Paragraph(month.getYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN)));
			table.addHeaderCell(headerCell);

		}

	}

}
