package de.schulferien;

import de.gfss.calendar.Vacation;

public class SchulferienNrw2020 {

	public static Vacation build() {
		Vacation schulferienNrw2020 = Vacation.builder().period("23.12.2019", "06.01.2020")
				.period("06.04.2020", "18.04.2020").period("02.06.2020", "02.06.2020")
				.period("29.06.2020", "11.08.2020").period("12.10.2020", "24.10.2020")
				.period("23.12.2020", "06.01.2021").build();

		return schulferienNrw2020;

	}
	
}
