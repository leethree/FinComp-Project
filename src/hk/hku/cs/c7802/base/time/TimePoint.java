package hk.hku.cs.c7802.base.time;

import java.util.Date;
import java.util.TimeZone;

public class TimePoint {
	
	private TimePoint(TimeZone tz, Date time) {
		this.tz = tz;
		this.time = time;
	}
	
	public TimePoint offset(TimeSpan span) {
		// TODO
		return null;
	}
	
	/*
	 * positive if tp is before this
	 */
	public TimeDiff diff(TimePoint tp) {
		// TODO
		return null;
	}
	
	/**
	 * 
	 * @param str String representing the date or time
	 * @return
	 */
	public static TimePoint parse(String str) {
		// TODO parse
		return new TimePoint(null, null);
	}
	
	private TimeZone tz;
	
	private Date time; 	// its timezone must be ignored. use tz above instead.
}
