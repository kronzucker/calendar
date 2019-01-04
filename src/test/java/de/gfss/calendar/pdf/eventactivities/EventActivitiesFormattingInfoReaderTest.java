package de.gfss.calendar.pdf.eventactivities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import de.gfss.calendar.pdf.EventActivititesFormatting;
import de.gfss.calendar.pdf.eventactivities.EventActivitiesFormattingCsvReader;

public class EventActivitiesFormattingInfoReaderTest {

	@Test
	public void canReadFormattingInfo() throws IOException {

		// given
		FileInputStream categoryFormattingCsvFile = new FileInputStream(
				"src/main/resources/activities/activities.csv");

		// when
		EventActivititesFormatting eventActivitiesFormattingInfo = EventActivitiesFormattingCsvReader
				.read(categoryFormattingCsvFile);
		
		// then
		assertThat(eventActivitiesFormattingInfo.size(), equalTo(17));
	}

}
