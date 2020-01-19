package de.bruderschaft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import de.gfss.calendar.Calendar;
import de.gfss.calendar.CalendarPeriod;
import de.gfss.calendar.Vacation;
import de.gfss.calendar.events.CalendarEvent;
import de.gfss.calendar.pdf.EventActivititesFormatting;
import de.gfss.calendar.pdf.PdfCalendarPage;
import de.gfss.calendar.pdf.eventactivities.EventActivitiesFormattingCsvReader;
import lombok.Builder;

@Builder
public class Jahresuebersicht {

	private final int jahr;
	private final String titel;
	private final Vacation schulferien;
	private final String version;
	private final List<CalendarEvent> termine = new ArrayList<CalendarEvent>();

	public Jahresuebersicht(int paramJahr, String paramTitel, Vacation paramSchulferien, String paramVersion) {
		super();
		this.jahr = paramJahr;
		this.titel = paramTitel;
		this.schulferien = paramSchulferien;
		this.version = paramVersion;
	}

	public Jahresuebersicht termine(List<CalendarEvent> paramTermine) {
		this.termine.addAll(paramTermine);
		return this;
	}

	public void generateToFile(File file) throws IOException {

		// categories
		EventActivititesFormatting eventCategoriesFormatting = EventActivitiesFormattingCsvReader
				.read(EventActivititesFormatting.class.getResourceAsStream("/activities/activities.csv"));

		CalendarPeriod calendarPeriod = CalendarPeriod.of(this.jahr, 1, 13);
		Calendar calendar = new Calendar(this.titel, calendarPeriod, this.termine, this.schulferien);

		// Creating a PdfDocument object
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
		Document doc = new Document(pdfDoc);
		doc.setTopMargin(25);

		CalendarPeriod firstHalfOfYear = calendarPeriod.subPeriod(0, 5);
		CalendarPeriod secondHalfOfYear = calendarPeriod.subPeriod(6, 12);

		PdfCalendarPage pdfCalendarFirstHalfYear = new PdfCalendarPage(calendar, 530, eventCategoriesFormatting,
				firstHalfOfYear, this.version);
		pdfCalendarFirstHalfYear.generateCalendarOn(doc);

		PdfCalendarPage pdfCalendarSecondHalfYear = new PdfCalendarPage(calendar, 530, eventCategoriesFormatting,
				secondHalfOfYear, this.version);
		pdfCalendarSecondHalfYear.generateCalendarOn(doc);

		// Closing the document
		doc.close();

	}

}
