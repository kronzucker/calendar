package de.gfss.calendar.pdf;

import org.springframework.util.StringUtils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;

public class EventCategoryFormatting {

	private final String category;
	private final Color backgroundColor;
	private final Image icon;
	
	public EventCategoryFormatting(String category, Color backgroundColor, Image icon) {
		super();
		this.category = category;
		this.backgroundColor = backgroundColor;
		this.icon = icon;
	}

	public String getCategory() {
		return category;
	}

	public boolean formatBackground(Cell cell) {
		if (!StringUtils.isEmpty(backgroundColor)) {
			cell.setBackgroundColor(backgroundColor);
			return true;
		}
		return false;
	}

	public boolean insertIcon(Cell cell, float cellWidth) {
		if (icon != null) {
			float imageHeight = icon.getImageHeight();
			
			icon.scaleToFit(cellWidth, imageHeight);
			cell.add(icon);
			cell.setPaddingTop(1).setPaddingBottom(1);
			return true;
		}
		return false;
	}

	public Image getIcon() {
		return icon;
	}

}
