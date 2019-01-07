package de.bruderschaft;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import de.gfss.calendar.Calendar;
import de.gfss.calendar.CalendarPeriod;
import de.gfss.calendar.Vacation;
import de.gfss.calendar.events.CalendarEvent;
import de.gfss.calendar.events.CalendarEventsCsvReader;
import de.gfss.calendar.pdf.EventActivititesFormatting;
import de.gfss.calendar.pdf.PdfCalendarPage;
import de.gfss.calendar.pdf.SchulferienNrw2019;
import de.gfss.calendar.pdf.eventactivities.EventActivitiesFormattingCsvReader;

public class Jahresuebersicht2019 {

	public static void main(String[] args) throws IOException {

		Vacation schulferien2019 = SchulferienNrw2019.build();

		// categories
		EventActivititesFormatting eventCategoriesFormatting = EventActivitiesFormattingCsvReader
				.read(EventActivititesFormatting.class.getResourceAsStream("/activities/activities.csv"));

		List<CalendarEvent> termine2019 = CalendarEventsCsvReader
				.read(Jahresuebersicht2019.class.getResourceAsStream("/jahresuebersicht2019.csv"));
		List<CalendarEvent> feiertage2019 = CalendarEventsCsvReader
				.read(Jahresuebersicht2019.class.getResourceAsStream("/feiertage2019.csv"));
		termine2019.addAll(feiertage2019);
		
		CalendarPeriod calendarPeriod = CalendarPeriod.of(2019, 1, 13);
		Calendar calendar = new Calendar("Termine Bruderschaft Lüttelbracht-Genholt 2019", calendarPeriod, termine2019,
				schulferien2019);

		// Creating a PdfDocument object
		File file = new File("bruderschaft2019.pdf");
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
		Document doc = new Document(pdfDoc);
		doc.setTopMargin(25);

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

		Desktop.getDesktop().open(file);
	}

}
