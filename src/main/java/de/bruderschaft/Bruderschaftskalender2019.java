package de.bruderschaft;

import java.io.IOException;
import java.util.List;

import de.gfss.calendar.events.CalendarEvent;
import de.gfss.calendar.events.CalendarEventsCsvReader;
import de.schulferien.SchulferienNrw2019;

public class Bruderschaftskalender2019 {

	private Bruderschaftskalender2019() {
	}

	public static Jahresuebersicht build() throws IOException {
		Jahresuebersicht jahresuebersicht2019 = Jahresuebersicht.builder().jahr(2019)
				.schulferien(SchulferienNrw2019.build()).titel("Termine Bruderschaft Lüttelbracht-Genholt 2019")
				.version("V1.0 22.01.2019").build();

		// Bruderschaftstermine
		List<CalendarEvent> termine2019 = CalendarEventsCsvReader
				.read(Bruderschaftskalender.class.getResourceAsStream("/jahresuebersicht2019.csv"));
		jahresuebersicht2019.termine(termine2019);

		// Feiertage 2019
		List<CalendarEvent> feiertage2019 = CalendarEventsCsvReader
				.read(Bruderschaftskalender.class.getResourceAsStream("/feiertage2019.csv"));
		jahresuebersicht2019.termine(feiertage2019);

		return jahresuebersicht2019;
	}
}
