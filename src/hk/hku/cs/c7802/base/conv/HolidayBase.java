package hk.hku.cs.c7802.base.conv;

import java.util.Calendar;

import hk.hku.cs.c7802.base.time.TimePoint;

public interface HolidayBase {

	public static final HolidayBase WEEKEND_ONLY = new SimpleHolidayBase();

	public boolean isHoliday(TimePoint time);

	static class SimpleHolidayBase implements HolidayBase {

		@Override
		public boolean isHoliday(TimePoint time) {
			int day = time.getTime().get(Calendar.DAY_OF_WEEK);
			return day == Calendar.SATURDAY || day == Calendar.SUNDAY;
		}

	}
}
