package hk.hku.cs.c7802.base.conv;

import hk.hku.cs.c7802.base.time.TimePoint;

public interface HolidayBase {

	public static final HolidayBase DEFAULT = new SimpleHolidayBase();

	public boolean isHoliday(TimePoint time);

	static class SimpleHolidayBase implements HolidayBase {

		@Override
		public boolean isHoliday(TimePoint time) {
			// TODO Auto-generated method stub
			return false;
		}

	}
}
