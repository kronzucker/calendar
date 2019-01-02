package de.gfss.calendar.pdf;

import com.itextpdf.layout.element.Image;

public class PdfCalendarEventCategory {

	private final String category;
	private final String colorCode;
	private final Image icon;
	
	public PdfCalendarEventCategory(String category, String colorCode, Image icon) {
		super();
		this.category = category;
		this.colorCode = colorCode;
		this.icon = icon;
	}

	public String getCategory() {
		return category;
	}
	
	public String getColorCode() {
		return colorCode;
	}
	
	public Image getIcon() {
		return icon;
	}
}
