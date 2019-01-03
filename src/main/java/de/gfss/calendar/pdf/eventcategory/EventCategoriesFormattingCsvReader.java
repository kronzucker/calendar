package de.gfss.calendar.pdf.eventcategory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.layout.element.Image;

import de.gfss.calendar.pdf.EventCategoriesFormatting;
import de.gfss.calendar.pdf.EventCategoryFormatting;

public class EventCategoriesFormattingCsvReader {

	private static final class CsvFormat {
		public String category;
		public String background_color;
		public String icon;
	}

	public static EventCategoriesFormatting read(InputStream stream) throws IOException {

		EventCategoriesFormatting eventCategoryFormattingInfos = new EventCategoriesFormatting();

		CsvMapper mapper = new CsvMapper();

		CsvSchema csvSchema = mapper.schemaFor(CsvFormat.class).withColumnSeparator(';').withHeader().withColumnReordering(true);
		ObjectReader reader = mapper.readerFor(CsvFormat.class).with(csvSchema);

		MappingIterator<CsvFormat> iterator = reader.readValues(stream);
		while (iterator.hasNext()) {
			CsvFormat line = iterator.next();

			Color backgroundColor = null;
			if (!StringUtils.isEmpty(line.background_color)) {
				backgroundColor = WebColors.getRGBColor(line.background_color);
			}

			Image icon = null;
			if (!StringUtils.isEmpty(line.icon)) {
				URL iconFileURL = EventCategoriesFormattingCsvReader.class.getClassLoader()
						.getResource("eventcategory/" + line.icon);
				ImageData iconData = ImageDataFactory.create(iconFileURL);
				icon = new Image(iconData);
			}

			EventCategoryFormatting eventCategoryFormattingInfo = new EventCategoryFormatting(line.category,
					backgroundColor, icon);
			eventCategoryFormattingInfos.put(eventCategoryFormattingInfo);
		}

		return eventCategoryFormattingInfos;
	}

}
