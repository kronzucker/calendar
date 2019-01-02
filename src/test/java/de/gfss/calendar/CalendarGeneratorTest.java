package de.gfss.calendar;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.YearMonth;

import org.junit.Test;

import de.gfss.calendar.pdf.PdfCalendar;

public class CalendarGeneratorTest {

	@Test
	public void canCreateEmptyCalendar() throws IOException {

		Calendar calendar = new Calendar("Termine Bruderschaft Lüttelbracht-Genholt 2019",
				YearMonth.of(2019, 1), 6);
		
		PdfCalendar pdfCalendar = new PdfCalendar(calendar, 520);
		
		File file = new File("/home/developer/test.pdf");
		pdfCalendar.generateFile(file);

		Desktop.getDesktop().open(file);

	}

}
