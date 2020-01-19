package de.schulferien;

import de.gfss.calendar.Vacation;

public class SchulferienNrw2019 {

	public static Vacation build() {
		Vacation schulferienNrw2019 = Vacation.builder().period("21.12.2018", "04.01.2019")
				.period("15.04.2019", "27.04.2019").period("11.06.2019", "11.06.2019")
				.period("15.07.2019", "27.08.2019").period("14.10.2019", "26.10.2019")
				.period("23.12.2019", "06.01.2020").build();

		return schulferienNrw2019;

	}
	
}
