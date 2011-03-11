package hk.hku.cs.c7802.base.conv;

import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;

public interface DateRoller {
	
	public static final DateRoller NEXT_BUZ_DAY = new NextBusinessDayRoller(HolidayBase.DEFAULT);
	
	public TimePoint roll(TimePoint t);
	
	static class NextBusinessDayRoller implements DateRoller{

		public NextBusinessDayRoller(HolidayBase hb) {
			this.hb = hb;
		}
		
		@Override
		public TimePoint roll(TimePoint t) {
			TimePoint ret = t;
			while (hb.isHoliday(ret))
				ret = ret.offset(TimeSpan.NEXTDAY);
			return ret;
		}
		
		private HolidayBase hb;
	}
}
