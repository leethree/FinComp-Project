package hk.hku.cs.c7802.base.time;

import hk.hku.cs.c7802.base.BaseException;
import hk.hku.cs.c7802.base.BaseRuntimeException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimePoint implements Comparable<TimePoint>{

	private TimePoint(TimeZone tz, Date time) {
		this.time = new GregorianCalendar(tz);
		this.time.setTime(time);
	}

	public TimePoint plus(TimeSpan span) {
		Calendar c = (Calendar) time.clone();
		c.add(Calendar.YEAR, span.getYear());
		c.add(Calendar.MONTH, span.getMonth());
		CalendarUtils.add(c, Calendar.DATE, span.getDay());
		return new TimePoint(time.getTimeZone(), c.getTime());
	}

	public TimeDiff minus(TimePoint tp) {
		if (!time.getTimeZone().hasSameRules(tp.time.getTimeZone()))
			throw new BaseRuntimeException("Incompatible timezone: " + tp.time.getTimeZone().getID());
		Calendar c1 = CalendarUtils.round(time);
		Calendar c2 = CalendarUtils.round(tp.time);
		if (c1.after(c2))
			return new TimeDiff(CalendarUtils.difference(c2, c1,
					CalendarUtils.Unit.DAY));
		else
			return new TimeDiff(-CalendarUtils.difference(c1, c2,
					CalendarUtils.Unit.DAY));
	}

	public TimeZone getTimezone() {
		return time.getTimeZone();
	}
	
	public Calendar getTime() {
		return (Calendar) time.clone();
	}
	
	@Override
	public String toString() {
		return new SimpleDateFormat("yyyy-MM-dd").format(time.getTime());
	}

	public String toCompleteString() {
		return FORMATTER.format(time.getTime()) + " "
				+ time.getTimeZone().getID();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimePoint other = (TimePoint) obj;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

	@Override
	public int compareTo(TimePoint other) {
		return this.time.compareTo(other.time);
	}
	
	/**
	 * 
	 * @param str
	 *            String representing the date or time, must be formatted like
	 *            "2011-03-21 17:18:42 GMT+08:00"
	 * @return
	 * @throws BaseException 
	 */
	public static TimePoint parse(String str) throws BaseException {
		String[] tokens = str.split(" ");
		if (tokens.length != 3)
			throw new BaseException("Incorrect time point format: " + str);
		TimeZone tz = TimeZone.getTimeZone(tokens[2]);
		Date time;
		try {
			time = FORMATTER.parse(str);
		} catch (ParseException e) {
			throw new BaseException("Incorrect time point format: " + str, e);
		}
		return new TimePoint(tz, time);
	}

	public static TimePoint now() {
		Date now = new Date();
		return new TimePoint(DEFAULT_TIMEZONE, now);
	}

	public static boolean onSameDay(TimePoint t1, TimePoint t2){
		return CalendarUtils.round(t1.time).equals(CalendarUtils.round(t2.time));
	}
	
	private Calendar time;

	private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();
	private static final DateFormat FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
}
