package de.gfss.calendar;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;

import org.junit.Test;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.layout.element.Image;

import de.gfss.calendar.pdf.EventCategoryFormattingInfo;
import de.gfss.calendar.pdf.PdfCalendar;
import de.gfss.calendar.pdf.PdfEventCategories;

public class CalendarGeneratorTest {

	@Test
	public void canCreateEmptyCalendar() throws IOException {

		Calendar calendar = new Calendar("Termine Bruderschaft Lüttelbracht-Genholt 2019",
				YearMonth.of(2019, 1), 6);
		calendar.addEvent(new CalendarEvent(LocalDate.of(2019, 1, 5), null, "", "", "E1"));
		calendar.addEvent(new CalendarEvent(LocalDate.of(2019, 2, 4), null, "", "", "E2"));
		calendar.addEvent(new CalendarEvent(LocalDate.of(2019, 3, 6), null, "", "", "E2"));
		calendar.addEvent(new CalendarEvent(LocalDate.of(2019, 5, 7), null, "", "", "E2"));
		
		PdfEventCategories categories = new PdfEventCategories();
		categories.put("E1", new EventCategoryFormattingInfo("E1", WebColors.getRGBColor("yellow"), null));
		categories.put("E2", new EventCategoryFormattingInfo("E2", WebColors.getRGBColor("red"), new Image(ImageDataFactory.create("src/test/java/de/gfss/calendar/christmas-tree.png"))));
		
		PdfCalendar pdfCalendar = new PdfCalendar(calendar, 520, categories);
		
		File file = new File("/home/developer/test.pdf");
		pdfCalendar.generateFile(file);

		Desktop.getDesktop().open(file);

	}

}
