package de.gfss.calendar;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

import org.junit.Test;

public class VacationTest {

	@Test
	public void canCreateVacation() {

		// given

		// when
		Vacation vacation = Vacation.builder().period("05.05.2019", "12.05.2019").period("16.10.2019", "20.10.2019")
				.build();

		// then
		assertThat(vacation.contains(LocalDate.of(2019, 5, 5)), equalTo(Boolean.TRUE));
		assertThat(vacation.contains(LocalDate.of(2019, 5, 12)), equalTo(Boolean.TRUE));
		assertThat(vacation.contains(LocalDate.of(2019, 10, 16)), equalTo(Boolean.TRUE));
		assertThat(vacation.contains(LocalDate.of(2019, 10, 15)), equalTo(Boolean.FALSE));

	}

}
