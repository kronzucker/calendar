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

import de.gfss.calendar.pdf.EventActivityFormatting;
import de.gfss.calendar.pdf.PdfCalendarPage;
import de.gfss.calendar.pdf.eventactivities.EventActivitiesFormattingCsvReader;
import de.gfss.calendar.events.CalendarEvent;
import de.gfss.calendar.events.CalendarEventsCsvReader;
import de.gfss.calendar.events.EventCategory;
import de.gfss.calendar.pdf.EventActivititesFormatting;

public class BruderschaftCalendarTest {

	@Test
	public void canCreateBruderschaft2019Calendar() throws IOException {

		// categories
		FileInputStream categoryFormattingCsvFile = new FileInputStream(
				"src/main/resources/activities/activities.csv");
		EventActivititesFormatting eventCategoriesFormatting = EventActivitiesFormattingCsvReader
				.read(categoryFormattingCsvFile);

		// events
		List<CalendarEvent> calendarEvents = new ArrayList<>();
		calendarEvents
				.add(new CalendarEvent(LocalDate.of(2019, 1, 5), LocalTime.of(19, 00), "JHV 2019", "Kirche", EventCategory.HIGHLIGHT, "E1"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 2, 4), null, "Tuppen", "Werner", EventCategory.EVENT, "E2"));
		calendarEvents
				.add(new CalendarEvent(LocalDate.of(2019, 3, 6), LocalTime.of(20, 30), "Schützenfest", "Bracht", EventCategory.HIGHLIGHT, "schuetzenfest"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 5, 7), null, "", "", EventCategory.INFO, "bierchen"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 11, 6), null, "", "", EventCategory.INFO, "sf_sonstige"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 12, 22), null, "", "", EventCategory.INFO, "stmartin"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2020, 1, 22), null, "", "", EventCategory.EVENT, "tuppen"));

		Vacation vacation = Vacation.builder().
			period("21.12.2018", "04.01.2019").
			period("15.04.2019", "27.04.2019").
			period("11.06.2019", "11.06.2019").
			period("15.07.2019", "27.08.2019").
			period("14.10.2019", "26.10.2019").
			period("23.12.2019", "06.01.2020").
			period("06.04.2020", "18.04.2020").build();
		
		CalendarPeriod calendarPeriod = CalendarPeriod.of(2019, 1, 13);
		Calendar calendar = new Calendar("Termine Bruderschaft Lüttelbracht-Genholt 2019", calendarPeriod,
				calendarEvents, vacation);

		// Creating a PdfDocument object
		File file = new File("/home/developer/test.pdf");
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
		Document doc = new Document(pdfDoc);

		CalendarPeriod firstHalfOfYear = calendarPeriod.subPeriod(0, 5);
		CalendarPeriod secondHalfOfYear = calendarPeriod.subPeriod(6, 12);

		PdfCalendarPage pdfCalendarFirstHalfYear = new PdfCalendarPage(calendar, 520, eventCategoriesFormatting,
				firstHalfOfYear, "V1");
		pdfCalendarFirstHalfYear.generateCalendarOn(doc);

		PdfCalendarPage pdfCalendarSecondHalfYear = new PdfCalendarPage(calendar, 520, eventCategoriesFormatting,
				secondHalfOfYear, "V1");
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
				"src/main/resources/activities/activities.csv");
		EventActivititesFormatting eventCategoriesFormatting = EventActivitiesFormattingCsvReader
				.read(categoryFormattingCsvFile);

		InputStream eventsCsvFile = BruderschaftCalendarTest.class.getResourceAsStream("bruderschaft2018.csv");
		List<CalendarEvent> calendarEvents = CalendarEventsCsvReader.read(eventsCsvFile);

		Vacation vacation = Vacation.builder().
				period("21.12.2017", "04.01.2018").
				period("15.04.2018", "27.04.2018").
				period("11.06.2018", "11.06.2018").
				period("15.07.2018", "27.08.2018").
				period("14.10.2018", "26.10.2018").
				period("23.12.2018", "06.01.2019").
				period("06.04.2019", "18.04.2020").build();
		
		CalendarPeriod calendarPeriod = CalendarPeriod.of(2018, 1, 13);
		Calendar calendar = new Calendar("Termine Bruderschaft Lüttelbracht-Genholt 2018", calendarPeriod,
				calendarEvents, vacation);

		// Creating a PdfDocument object
		File file = new File("/home/developer/bruderschaft2018.pdf");
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
		Document doc = new Document(pdfDoc);

		CalendarPeriod firstHalfOfYear = calendarPeriod.subPeriod(0, 5);
		CalendarPeriod secondHalfOfYear = calendarPeriod.subPeriod(6, 12);

		PdfCalendarPage pdfCalendarFirstHalfYear = new PdfCalendarPage(calendar, 520, eventCategoriesFormatting,
				firstHalfOfYear, "V1");
		pdfCalendarFirstHalfYear.generateCalendarOn(doc);

		PdfCalendarPage pdfCalendarSecondHalfYear = new PdfCalendarPage(calendar, 520, eventCategoriesFormatting,
				secondHalfOfYear, "V1");
		pdfCalendarSecondHalfYear.generateCalendarOn(doc);

		// Closing the document
		doc.close();
		System.out.println("Table created successfully..");

		Desktop.getDesktop().open(file);	
		
	}
	
}
