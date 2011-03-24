package hk.hku.cs.c7802.base.time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modified from Aaron's work:
 * http://www.gadberry.com/aaron/2007/08/17/find-the-difference-between-two-java-dates-calendars/
 * @author Aaron Gadberry
 */

class CalendarUtils {

	/**
	 * Unit is utilized to distinguish the valid types of units that can be
	 * utilized by a series of difference methods within this class.
	 */
	public enum Unit {
		/**
		 * Represents a unit of time defined by Calendar.DAY_OF_MONTH
		 */
		DAY(Calendar.DAY_OF_MONTH, 1000l * 60 * 60 * 24),
		/**
		 * Represents a unit of time defined by Calendar.HOUR_OF_DAY
		 */
		HOUR(Calendar.HOUR_OF_DAY, 1000l * 60 * 60),
		/**
		 * Represents a unit of time defined by Calendar.MILLISECOND
		 */
		MILLISECOND(Calendar.MILLISECOND, 1),
		/**
		 * Represents a unit of time defined by Calendar.MINUTE
		 */
		MINUTE(Calendar.MINUTE, 1000l * 60),
		/**
		 * Represents a unit of time defined by Calendar.MONTH
		 */
		MONTH(Calendar.MONTH, 1000l * 60 * 60 * 24 * 28),
		/**
		 * Represents a unit of time defined by Calendar.SECOND
		 */
		SECOND(Calendar.SECOND, 1000l),
		/**
		 * Represents a unit of time defined by Calendar.YEAR
		 */
		YEAR(Calendar.YEAR, 1000l * 60 * 60 * 24 * 365);

		private final int calendarUnit;
		private final long estimate;

		Unit(int calendarUnit, long estimate) {
			this.calendarUnit = calendarUnit;
			this.estimate = estimate;
		}
	}

	/**
	 * Add a long amount to a calendar. Similar to calendar.add() but accepts a
	 * long argument instead of limiting it to an int.
	 * 
	 * @param c
	 *            the calendar
	 * 
	 * @param unit
	 *            the unit to increment
	 * 
	 * @param increment
	 *            the amount to increment
	 */
	public static void add(Calendar c, int unit, long increment) {
		while (increment > Integer.MAX_VALUE) {
			c.add(unit, Integer.MAX_VALUE);
			increment -= Integer.MAX_VALUE;
		}
		c.add(unit, (int) increment);
	}

	/**
	 * Find the number of units passed between two {@link Calendar} objects.
	 * 
	 * @param c1
	 *            The first occurring {@link Calendar}
	 * 
	 * @param c2
	 *            The later occurring {@link Calendar}
	 * 
	 * @param unit
	 *            The unit to calculate the difference in
	 * 
	 * @return the number of units passed
	 */
	public static long difference(Calendar c1, Calendar c2, Unit unit) {

		Calendar first = (Calendar) c1.clone();
		Calendar last = (Calendar) c2.clone();

		long difference = c2.getTimeInMillis() - c1.getTimeInMillis();

		long increment = (long) Math.floor((double) difference / (double) unit.estimate);
		increment = Math.max(increment, 1);

		long total = 0;

		while (increment > 0) {
			add(first, unit.calendarUnit, increment);
			if (first.after(last)) {
				add(first, unit.calendarUnit, increment * -1);
				increment = (long) Math.floor(increment / 2);
			} else {
				total += increment;
			}
		}

		return total;
	}

	/**
	 * Find the number of units passed between two {@link Date} objects.
	 * 
	 * @param d1
	 *            The first occurring {@link Date}
	 * 
	 * @param d2
	 *            The later occurring {@link Date}
	 * 
	 * @param unit
	 *            The unit to calculate the difference in
	 * 
	 * @return the number of units passed
	 */
	public static long difference(Date d1, Date d2, Unit unit) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		return difference(c1, c2, unit);
	}

	/**
	 * Find the number of units, including a fraction, passed between two
	 * {@link Calendar} objects.
	 * 
	 * @param c1
	 *            The first occurring {@link Calendar}
	 * 
	 * @param c2
	 *            The later occurring {@link Calendar}
	 * 
	 * @param unit
	 *            The unit to calculate the difference in
	 * 
	 * @return the number of units passed
	 */
	public static double exactDifference(Calendar c1, Calendar c2, Unit unit) {
		long unitDifference = difference(c1, c2, unit);
		Calendar mid = (Calendar) c1.clone();
		CalendarUtils.add(mid, unit.calendarUnit, unitDifference);

		Calendar end = (Calendar) mid.clone();
		end.add(unit.calendarUnit, 1);

		long millisPassed = CalendarUtils.difference(mid, c2, Unit.MILLISECOND);
		long millisTotal = CalendarUtils.difference(mid, end, Unit.MILLISECOND);

		double remainder = (double) millisPassed / (double) millisTotal;

		return unitDifference + remainder;
	}

	/**
	 * Find the number of units passed between two {@link Calendar} objects in
	 * all units. This would return a result like 1 year, 2 months and 3 days.
	 * 
	 * This method assumes you want the difference broken down in all available
	 * units.S
	 * 
	 * @param c1
	 *            The first occurring {@link Calendar}
	 * 
	 * @param c2
	 *            The later occurring {@link Calendar}
	 * 
	 * @return the number of units passed without overlap
	 */
	public static Map<Unit, Long> tieredDifference(Calendar c1, Calendar c2) {
		return tieredDifference(c1, c2, Arrays.asList(Unit.values()));
	}

	/**
	 * Find the number of units passed between two {@link Calendar} objects in
	 * all units. This would return a result like 1 year, 2 months and 3 days.
	 * 
	 * @param c1
	 *            The first occurring {@link Calendar}
	 * 
	 * @param c2
	 *            The later occurring {@link Calendar}
	 * 
	 * @param units
	 *            The list of units to calculate the difference in
	 * 
	 * @return the number of units passed without overlap
	 */
	public static Map<Unit, Long> tieredDifference(Calendar c1, Calendar c2, List<Unit> units) {
		Calendar first = (Calendar) c1.clone();
		Calendar last = (Calendar) c2.clone();

		Map<Unit, Long> differences = new HashMap<Unit, Long>();

		List<Unit> allUnits = new ArrayList<Unit>();
		allUnits.add(Unit.YEAR);
		allUnits.add(Unit.MONTH);
		allUnits.add(Unit.DAY);
		allUnits.add(Unit.HOUR);
		allUnits.add(Unit.MINUTE);
		allUnits.add(Unit.SECOND);
		allUnits.add(Unit.MILLISECOND);

		for (Unit unit : allUnits) {
			if (units.contains(unit)) {
				long difference = difference(first, last, unit);
				differences.put(unit, difference);
				CalendarUtils.add(first, unit.calendarUnit, difference);
			}
		}

		return differences;
	}

	/**
	 * Round time of Calendar to 12:00nn
	 * @param c
	 * @return
	 */
	public static Calendar round(Calendar c) {
		Calendar ret = (Calendar) c.clone();
		ret.set(Calendar.HOUR_OF_DAY, 12);
		ret.set(Calendar.MINUTE, 0);
		ret.set(Calendar.SECOND, 0);
		ret.set(Calendar.MILLISECOND, 0);
		return ret;
	}
	
}
