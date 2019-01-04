package de.gfss.calendar.events;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CalendarEventsCsvReader {

	private static final Logger LOG = LoggerFactory.getLogger(CalendarEventsCsvReader.class);
	
	private static final class CalendarEventFormat {
		public String date;
		public String start_time;
		public String description;
		public String location;
		public String category;
		public String activity;
	}

	public static List<CalendarEvent> read(InputStream stream) throws IOException {

		List<CalendarEvent> calendarEvents = new ArrayList<>();

		CsvMapper mapper = new CsvMapper();

		CsvSchema csvSchema = mapper.schemaFor(CalendarEventFormat.class).withColumnSeparator(';').withHeader()
				.withColumnReordering(true);
		ObjectReader reader = mapper.readerFor(CalendarEventFormat.class).with(csvSchema);

		int rowNumber = 0;
		MappingIterator<CalendarEventFormat> iterator = reader.readValues(stream);
		while (iterator.hasNext()) {
			rowNumber++;
			LOG.debug("reading event line {}.", rowNumber);
			
			CalendarEventFormat line = iterator.next();

			LocalTime startTime = null;
			if (!StringUtils.isEmpty(line.start_time)) {
				startTime = LocalTime.parse(line.start_time, DateTimeFormatter.ofPattern("HH:mm"));
			}
			
			EventCategory category = EventCategory.fromString(line.category);

			CalendarEvent calendarEvent = CalendarEvent.builder()
					.date(LocalDate.parse(line.date, DateTimeFormatter.ofPattern("dd.MM.yyyy")))
					.description(line.description).category(category).location(line.location)
					.startTime(startTime).activity(line.activity).build();

			calendarEvents.add(calendarEvent);
		}

		return calendarEvents;
	}

}
