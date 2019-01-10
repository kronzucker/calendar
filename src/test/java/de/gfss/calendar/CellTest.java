package de.gfss.calendar;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

public class CellTest {

	@Test
	public void canCreateNoPaddingCell() throws IOException {
		
		// given
		
		// when
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter("test.pdf"));
		Document doc = new Document(pdfDoc);
		
		Table table = new Table(4);
		table.setWidth(200);
		
		
		for (int i=0; i<4; i++) {
			for (int j=0; j<3; j++) {
				Cell cell = new Cell();
				cell.setPadding(0);
				
				Paragraph eventDescription = new Paragraph();
				eventDescription.add("Großer Texs  sssss");
				eventDescription.setBold();
				eventDescription.setFontSize(9);
				eventDescription.setFixedLeading(10);
				eventDescription.setTextAlignment(TextAlignment.CENTER);
//				eventDescription.setFirstLineIndent(0);
				
				cell.add(eventDescription);
				
				Paragraph eventLocationTime = new Paragraph();
				eventLocationTime.add("Kleiner Text");
				eventLocationTime.setFontSize(5f);
				eventLocationTime.setFixedLeading(6);
				eventLocationTime.setTextAlignment(TextAlignment.CENTER);

				cell.add(eventLocationTime);
				
				table.addCell(cell);
				
			}
		}
	
		doc.add(table);
	
		// Closing the document
		doc.close();

		Desktop.getDesktop().open(new File("test.pdf"));
		
		// then
		
	}
	
	
}
