package hk.hku.cs.c7802.base.conv;

import java.util.Calendar;

import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;

public interface DateRoller {
	
	public static final DateRoller NEXT_BUZ_DAY = new NextBusinessDayRoller(HolidayBase.WEEKEND_ONLY);
	public static final DateRoller MOD_NEXT_BUZ_DAY = new ModifiedNextBusinessDayRoller(HolidayBase.WEEKEND_ONLY);
	
	public TimePoint roll(TimePoint t);
	
	static class NextBusinessDayRoller implements DateRoller{

		public NextBusinessDayRoller(HolidayBase hb) {
			this.hb = hb;
		}
		
		@Override
		public TimePoint roll(TimePoint t) {
			TimePoint ret = t;
			while (hb.isHoliday(ret))
				ret = ret.plus(TimeSpan.NEXTDAY);
			return ret;
		}
		
		private HolidayBase hb;
	}
	
	static class ModifiedNextBusinessDayRoller implements DateRoller{

		public ModifiedNextBusinessDayRoller(HolidayBase hb) {
			this.hb = hb;
		}
		
		@Override
		public TimePoint roll(TimePoint t) {
			TimePoint ret = t;
			int month = t.getTime().get(Calendar.MONTH);
			while (hb.isHoliday(ret))
				ret = ret.plus(TimeSpan.NEXTDAY);
			// if next month is reached
			if (ret.getTime().get(Calendar.MONTH) != month){
				// roll back
				ret = t;
				while (hb.isHoliday(ret))
					ret = ret.plus(TimeSpan.LASTDAY);
			}
			return ret;
		}
		
		private HolidayBase hb;
	}
}
