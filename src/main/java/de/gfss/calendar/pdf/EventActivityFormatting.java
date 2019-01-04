package de.gfss.calendar.pdf;

import com.itextpdf.layout.element.Image;

public class EventActivityFormatting {

	private final String category;
	private final Image icon;
	
	public EventActivityFormatting(String category, Image icon) {
		super();
		this.category = category;
		this.icon = icon;
	}

	public String getCategory() {
		return category;
	}
	
	public Image getIcon() {
		return icon;
	}
	
	public boolean isIconDefined() {
		return icon != null;
	}

}
