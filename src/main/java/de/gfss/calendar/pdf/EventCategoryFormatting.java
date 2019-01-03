package de.gfss.calendar.pdf;

import org.springframework.util.StringUtils;

import com.itextpdf.kernel.colors.Color;
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
	
	public boolean isBackgroundColorDefined() {
		return !StringUtils.isEmpty(backgroundColor);
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Image getIcon() {
		return icon;
	}
	
	public boolean isIconDefined() {
		return icon != null;
	}

}
