package dateTest;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateTestMain {
	public static void main(String[] args) {
		Date now = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		
		System.out.println(calendar.get(Calendar.DAY_OF_WEEK)); // the day of the week in numerical format

		LocalDate localDate = LocalDate.now();

		// Getting the day of week for a given date
		java.time.DayOfWeek dayOfWeek = localDate.getDayOfWeek();
		System.out.println(localDate + " was a " + dayOfWeek);
		LocalDate firstWorkingDay;

		// Using DayOfWeek to execute dependent business logic
		switch (dayOfWeek) {
		case FRIDAY:
			firstWorkingDay = localDate.plusDays(3);
			break;
		case SATURDAY:
			firstWorkingDay = localDate.plusDays(2);
			break;
		default:
			firstWorkingDay = localDate.plusDays(1);
			break;
		}
		System.out.println("First working day of 2016 was " + firstWorkingDay);

		System.out.println("\n\n\n\n");

		// Create a calendar with year and day of year.
		// Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2017);
		calendar.set(Calendar.DAY_OF_YEAR, 180);

		// See the full information of the calendar object.
		System.out.println(calendar.getTime().toString());

		// Get the weekday and print it
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("Weekday: " + weekday);

		// Get weekday name
		DateFormatSymbols dfs = new DateFormatSymbols();
		System.out.println("Weekday: " + dfs.getWeekdays()[weekday]);

		System.out.println("\n\n\n\n");

		System.out.println("////////////////////////////////////////////////////");
	}
}
