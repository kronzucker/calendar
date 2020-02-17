package de.bruderschaft;

import java.io.IOException;
import java.util.List;

import de.gfss.calendar.events.CalendarEvent;
import de.gfss.calendar.events.CalendarEventsCsvReader;
import de.schulferien.SchulferienNrw2020;

public class Bruderschaftskalender2020 {

	private Bruderschaftskalender2020() {
	}

	public static Jahresuebersicht build() throws IOException {
		Jahresuebersicht jahresuebersicht2020 = Jahresuebersicht.builder().jahr(2020)
				.schulferien(SchulferienNrw2020.build()).titel("Termine Bruderschaft Lüttelbracht-Genholt 2020")
				.version("V1.0 17.02.2020").build();

		// Bruderschaftstermine
		List<CalendarEvent> termine2019 = CalendarEventsCsvReader
				.read(Bruderschaftskalender.class.getResourceAsStream("/jahresuebersicht2020.csv"));
		jahresuebersicht2020.termine(termine2019);

		// Feiertage 2019
		List<CalendarEvent> feiertage2019 = CalendarEventsCsvReader
				.read(Bruderschaftskalender.class.getResourceAsStream("/feiertage2020.csv"));
		jahresuebersicht2020.termine(feiertage2019);

		return jahresuebersicht2020;
	}
}
