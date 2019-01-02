package de.gfss.calendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.YearMonth;

import org.junit.Test;

public class CalendarGeneratorTest {

	@Test
	public void canCreateEmptyCalendar() throws FileNotFoundException {

		//
		PdfCalendar yearCalendar = new PdfCalendar(YearMonth.of(2019,  1), 7);

		yearCalendar.generateFile(new File("/home/developer/test.pdf"));

	}

}
