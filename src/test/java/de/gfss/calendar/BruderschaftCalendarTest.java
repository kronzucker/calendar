package de.gfss.calendar;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import de.gfss.calendar.pdf.EventCategoryFormatting;
import de.gfss.calendar.pdf.PdfCalendarPage;
import de.gfss.calendar.pdf.eventcategory.EventCategoriesFormattingCsvReader;
import de.gfss.calendar.events.CalendarEventsCsvReader;
import de.gfss.calendar.pdf.EventCategoriesFormatting;

public class BruderschaftCalendarTest {

	@Test
	public void canCreateBruderschaft2019Calendar() throws IOException {

		// categories
		FileInputStream categoryFormattingCsvFile = new FileInputStream(
				"src/main/resources/eventcategory/eventcategories.csv");
		EventCategoriesFormatting eventCategoriesFormatting = EventCategoriesFormattingCsvReader
				.read(categoryFormattingCsvFile);

		// events
		List<CalendarEvent> calendarEvents = new ArrayList<>();
		calendarEvents
				.add(new CalendarEvent(LocalDate.of(2019, 1, 5), LocalTime.of(19, 00), "JHV 2019", "Kirche", "E1"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 2, 4), null, "Tuppen", "Werner", "E2"));
		calendarEvents
				.add(new CalendarEvent(LocalDate.of(2019, 3, 6), LocalTime.of(20, 30), "Schützenfest", "Bracht", "schuetzenfest"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 5, 7), null, "", "", "bierchen"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 11, 6), null, "", "", "sf_sonstige"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 12, 22), null, "", "", "stmartin"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2020, 1, 22), null, "", "", "tuppen"));

		CalendarPeriod calendarPeriod = CalendarPeriod.of(2019, 1, 13);
		Calendar calendar = new Calendar("Termine Bruderschaft Lüttelbracht-Genholt 2019", calendarPeriod,
				calendarEvents);

		// Creating a PdfDocument object
		File file = new File("/home/developer/test.pdf");
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
		Document doc = new Document(pdfDoc);

		CalendarPeriod firstHalfOfYear = calendarPeriod.subPeriod(0, 5);
		CalendarPeriod secondHalfOfYear = calendarPeriod.subPeriod(6, 12);

		PdfCalendarPage pdfCalendarFirstHalfYear = new PdfCalendarPage(calendar, 520, eventCategoriesFormatting,
				firstHalfOfYear);
		pdfCalendarFirstHalfYear.generateCalendarOn(doc);

		PdfCalendarPage pdfCalendarSecondHalfYear = new PdfCalendarPage(calendar, 520, eventCategoriesFormatting,
				secondHalfOfYear);
		pdfCalendarSecondHalfYear.generateCalendarOn(doc);

		// Closing the document
		doc.close();
		System.out.println("Table created successfully..");

		Desktop.getDesktop().open(file);

	}
	
	@Test
	public void canCreateBruderschaft2018Calendar() throws IOException {
		
		// categories
		FileInputStream categoryFormattingCsvFile = new FileInputStream(
				"src/main/resources/eventcategory/eventcategories.csv");
		EventCategoriesFormatting eventCategoriesFormatting = EventCategoriesFormattingCsvReader
				.read(categoryFormattingCsvFile);

		InputStream eventsCsvFile = BruderschaftCalendarTest.class.getResourceAsStream("bruderschaft2018.csv");
		List<CalendarEvent> calendarEvents = CalendarEventsCsvReader.read(eventsCsvFile);

		CalendarPeriod calendarPeriod = CalendarPeriod.of(2018, 1, 13);
		Calendar calendar = new Calendar("Termine Bruderschaft Lüttelbracht-Genholt 2018", calendarPeriod,
				calendarEvents);

		// Creating a PdfDocument object
		File file = new File("/home/developer/bruderschaft2018.pdf");
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
		Document doc = new Document(pdfDoc);

		CalendarPeriod firstHalfOfYear = calendarPeriod.subPeriod(0, 5);
		CalendarPeriod secondHalfOfYear = calendarPeriod.subPeriod(6, 12);

		PdfCalendarPage pdfCalendarFirstHalfYear = new PdfCalendarPage(calendar, 520, eventCategoriesFormatting,
				firstHalfOfYear);
		pdfCalendarFirstHalfYear.generateCalendarOn(doc);

		PdfCalendarPage pdfCalendarSecondHalfYear = new PdfCalendarPage(calendar, 520, eventCategoriesFormatting,
				secondHalfOfYear);
		pdfCalendarSecondHalfYear.generateCalendarOn(doc);

		// Closing the document
		doc.close();
		System.out.println("Table created successfully..");

		Desktop.getDesktop().open(file);	
		
	}
	
}
