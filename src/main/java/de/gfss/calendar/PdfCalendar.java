package de.gfss.calendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Iterator;
import java.util.Locale;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs.Gray;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

public class PdfCalendar {

	private final YearMonth firstMonth;
	private final int numberOfMonths;
	private final Style dayOfMonthColumnStyle;

	public PdfCalendar(YearMonth firstMonth, int numberOfMonths) {
		this.firstMonth = firstMonth;
		this.numberOfMonths = numberOfMonths;

		dayOfMonthColumnStyle = new Style();
		dayOfMonthColumnStyle.setWidth(40);
		dayOfMonthColumnStyle.setBackgroundColor(WebColors.NAMES.getRGBColor("yellow"));
		dayOfMonthColumnStyle.setFontSize(8);
	}

	public void generateFile(File file) throws FileNotFoundException {
//	      String file = "C:/EXAMPLES/itextExamples/addingTableToPDF.pdf"; 

		// Creating a PdfDocument object
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));

		// Creating a Document object
		Document doc = new Document(pdfDoc);

		// Creating a table
		Table table = new Table(numberOfMonths * 2);

		table.setWidth(UnitValue.createPointValue(500));

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

		for (int monthIndex = 0; monthIndex < numberOfMonths; monthIndex++) {

			YearMonth currentMonth = firstMonth.plusMonths(monthIndex);

			LocalDate currentDay = null;
			try {
				currentDay = LocalDate.of(currentMonth.getYear(), currentMonth.getMonth(), dayOfMonth);
			} catch (DateTimeException invalidDateException) {
				// Consumed
			}
			
			Cell dayOfMonthColumn = new Cell();
			dayOfMonthColumn.addStyle(dayOfMonthColumnStyle);
			
			if (currentDay != null) {
				dayOfMonthColumn.add(new Paragraph(String.format("%02d", currentDay.getDayOfMonth())));
				dayOfMonthColumn.add(new Paragraph(GermanDayOfWeek.getTwoCharacterString(currentDay)));
			}
			table.addCell(dayOfMonthColumn);
			table.addCell("nix");
		}
	}

	private void printHeader(Table table) {

		for (int monthIndex = 0; monthIndex < numberOfMonths; monthIndex++) {

			YearMonth currentMonth = firstMonth.plusMonths(monthIndex);

			table.addHeaderCell("");
			table.addHeaderCell(currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN));

		}

	}

}
