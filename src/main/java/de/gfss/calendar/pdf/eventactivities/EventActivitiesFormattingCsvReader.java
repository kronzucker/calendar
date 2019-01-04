package de.gfss.calendar.pdf.eventactivities;

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

import de.gfss.calendar.pdf.EventActivititesFormatting;
import de.gfss.calendar.pdf.EventActivityFormatting;

public class EventActivitiesFormattingCsvReader {

	private static final class CsvFormat {
		public String activity;
		public String icon;
	}

	public static EventActivititesFormatting read(InputStream stream) throws IOException {

		EventActivititesFormatting eventActivitiesFormattingInfos = new EventActivititesFormatting();

		CsvMapper mapper = new CsvMapper();

		CsvSchema csvSchema = mapper.schemaFor(CsvFormat.class).withColumnSeparator(';').withHeader()
				.withColumnReordering(true);
		ObjectReader reader = mapper.readerFor(CsvFormat.class).with(csvSchema);

		MappingIterator<CsvFormat> iterator = reader.readValues(stream);
		while (iterator.hasNext()) {
			CsvFormat line = iterator.next();

			Image icon = null;
			if (!StringUtils.isEmpty(line.icon)) {
				URL iconFileURL = EventActivitiesFormattingCsvReader.class.getClassLoader()
						.getResource("activities/" + line.icon);
				ImageData iconData = ImageDataFactory.create(iconFileURL);
				icon = new Image(iconData);
			}

			EventActivityFormatting eventActivityFormattingInfo = new EventActivityFormatting(line.activity, icon);
			eventActivitiesFormattingInfos.put(eventActivityFormattingInfo);
		}

		return eventActivitiesFormattingInfos;
	}

}
