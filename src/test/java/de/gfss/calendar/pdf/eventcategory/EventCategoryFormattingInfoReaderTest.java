package de.gfss.calendar.pdf.eventcategory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import de.gfss.calendar.pdf.EventCategoriesFormatting;

public class EventCategoryFormattingInfoReaderTest {

	@Test
	public void canReadFormattingInfo() throws IOException {

		// given
		FileInputStream categoryFormattingCsvFile = new FileInputStream(
				"src/main/resources/eventcategory/eventcategories.csv");

		// when
		EventCategoriesFormatting eventCategoryFormattingInfo = EventCategoriesFormattingCsvReader
				.read(categoryFormattingCsvFile);
		
		// then
		assertThat(eventCategoryFormattingInfo.size(), equalTo(17));
	}

}
