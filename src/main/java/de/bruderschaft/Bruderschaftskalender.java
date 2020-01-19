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
import de.gfss.calendar.pdf.eventactivities.EventActivitiesFormattingCsvReader;

public class Bruderschaftskalender {

	public static void main(String[] args) throws IOException {

		// 2019
		Jahresuebersicht bruderschaftskalender2019 = Bruderschaftskalender2019.build();

		File file2019 = new File("bruderschaft2019.pdf");
		bruderschaftskalender2019.generateToFile(file2019);
		
		Desktop.getDesktop().open(file2019);
		
		// 2020
		Jahresuebersicht bruderschaftskalender2020 = Bruderschaftskalender2020.build();

		File file2020 = new File("bruderschaft2020.pdf");
		bruderschaftskalender2020.generateToFile(file2020);
		
		Desktop.getDesktop().open(file2020);
		
	}

}
