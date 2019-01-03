package de.gfss.calendar;

import java.time.YearMonth;
import java.util.Iterator;

import org.springframework.core.style.ToStringCreator;

public class CalendarPeriod implements Iterable<YearMonth> {

	private final YearMonth firstMonth;
	private final int numberOfMonths;

	public CalendarPeriod(YearMonth firstMonth, int numberOfMonths) {
		super();
		this.firstMonth = firstMonth;
		this.numberOfMonths = numberOfMonths;
	}

	public YearMonth getFirstMonth() {
		return firstMonth;
	}

	public int getNumberOfMonths() {
		return numberOfMonths;
	}

	public static CalendarPeriod of(int firstMonthYear, int firstMonthMonth, int numberOfMonths) {
		return new CalendarPeriod(YearMonth.of(firstMonthYear, firstMonthMonth), numberOfMonths);
	}

	public CalendarPeriod subPeriod(int fromMonth, int toMonth) {
		YearMonth subPeriodFrom = firstMonth.plusMonths(fromMonth);
		return new CalendarPeriod(subPeriodFrom, toMonth - fromMonth + 1);
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append("firstMonth", firstMonth).append("numberOfMonths", numberOfMonths)
				.toString();
	}

	@Override
	public Iterator<YearMonth> iterator() {
		return new Iterator<YearMonth>() {

			int index = 0;

			@Override
			public YearMonth next() {
				YearMonth nextMonth = firstMonth.plusMonths(index);
				index++;
				return nextMonth;
			}

			@Override
			public boolean hasNext() {
				return index < numberOfMonths;
			}
		};
	}
}
