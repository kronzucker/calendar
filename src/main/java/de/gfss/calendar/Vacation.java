package de.gfss.calendar;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Vacation {
	
	private static class VacationPeriod {
		private final LocalDate from;
		private final LocalDate to;
		
		public VacationPeriod(LocalDate from, LocalDate to) {
			super();
			this.from = from;
			this.to = to;
		}
		
		public boolean contains(LocalDate dateToCheck) {
			
			if (dateToCheck.isBefore(from)) {
				return false;
			}
			
			if (dateToCheck.isAfter(to)) {
				return false;
			}
			return true;
		}
	}
	
	public static class Builder {
		private static final DateTimeFormatter GERMAN_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		List<VacationPeriod> periods = new ArrayList<>();
		
		public Builder period(String from, String to) {
			LocalDate dateFrom = LocalDate.parse(from, GERMAN_DATE);
			LocalDate dateTo = LocalDate.parse(to, GERMAN_DATE);
			
			periods.add(new VacationPeriod(dateFrom, dateTo));
			return this;
		}
		
		public Vacation build() {
			return new Vacation(periods);
		}
		
	}
	
	private final List<VacationPeriod> vacationPeriods  = new ArrayList<>();
	
	public Vacation(List<VacationPeriod> periods) {
		this.vacationPeriods.addAll(periods);
	}

	public boolean contains(LocalDate date) {

		for (VacationPeriod vacationPeriod : vacationPeriods) {
			if (vacationPeriod.contains(date)) {
				return true;
			}
		}
		return false;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	
}
