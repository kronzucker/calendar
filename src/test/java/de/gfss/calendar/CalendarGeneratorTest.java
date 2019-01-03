package de.gfss.calendar;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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

import de.gfss.calendar.pdf.EventCategoryFormattingInfo;
import de.gfss.calendar.pdf.PdfCalendarPage;
import de.gfss.calendar.pdf.EventCategoryFormatting;

public class CalendarGeneratorTest {

	@Test
	public void canCreateEmptyCalendar() throws IOException {

		List<CalendarEvent> calendarEvents = new ArrayList<>();
		calendarEvents
				.add(new CalendarEvent(LocalDate.of(2019, 1, 5), LocalTime.of(19, 00), "JHV 2019", "Kirche", "E1"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 2, 4), null, "Tuppen", "Werner", "E2"));
		calendarEvents
				.add(new CalendarEvent(LocalDate.of(2019, 3, 6), LocalTime.of(20, 30), "Schützenfest", "Bracht", "E2"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 5, 7), null, "", "", "E2"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 11, 6), null, "", "", "E1"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2019, 12, 22), null, "", "", "E2"));
		calendarEvents.add(new CalendarEvent(LocalDate.of(2020, 1, 22), null, "", "", "E2"));

		CalendarPeriod calendarPeriod = CalendarPeriod.of(2019, 1, 13);
		Calendar calendar = new Calendar("Termine Bruderschaft Lüttelbracht-Genholt 2019", YearMonth.of(2019, 1), 13,
				calendarEvents);

		EventCategoryFormatting categories = new EventCategoryFormatting();
		categories.put("E1", new EventCategoryFormattingInfo("E1", WebColors.getRGBColor("yellow"), null));
		categories.put("E2", new EventCategoryFormattingInfo("E2", WebColors.getRGBColor("red"),
				new Image(ImageDataFactory.create("src/test/java/de/gfss/calendar/christmas-tree.png"))));

		// Creating a PdfDocument object
		File file = new File("/home/developer/test.pdf");
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
		Document doc = new Document(pdfDoc);

		CalendarPeriod firstHalfOfYear = calendarPeriod.subPeriod(0, 5);
		CalendarPeriod secondHalfOfYear = calendarPeriod.subPeriod(6, 12);

		PdfCalendarPage pdfCalendarFirstHalfYear = new PdfCalendarPage(calendar, 520, categories, firstHalfOfYear);
		pdfCalendarFirstHalfYear.generateCalendarOn(doc);

		PdfCalendarPage pdfCalendarSecondHalfYear = new PdfCalendarPage(calendar, 520, categories, secondHalfOfYear);
		pdfCalendarSecondHalfYear.generateCalendarOn(doc);

		// Closing the document
		doc.close();
		System.out.println("Table created successfully..");

		Desktop.getDesktop().open(file);

	}

}
