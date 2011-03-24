package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import hk.hku.cs.c7802.base.conv.DateRoller;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.conv.HolidayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;

import org.junit.Before;
import org.junit.Test;

public class ConventionTest {

	@Before
	public void setUp() throws Exception {
		time = TimePoint.parse("2011-03-21 17:18:42 GMT+8"); 	// This is a Monday
		time2 = TimePoint.parse("2011-04-30 11:11:20 GMT+8"); 	// This is a Saturday 
	}

	@Test
	public void testSimpleHolidayBase() {
		// Monday
		assertFalse(HolidayBase.WEEKEND_ONLY.isHoliday(time));
		// Sunday
		assertTrue(HolidayBase.WEEKEND_ONLY.isHoliday(time.plus(TimeSpan.LASTDAY)));
		// Saturday
		assertTrue(HolidayBase.WEEKEND_ONLY.isHoliday(time.plus(TimeSpan.LASTDAY).plus(TimeSpan.LASTDAY)));
		// Friday
		assertFalse(HolidayBase.WEEKEND_ONLY.isHoliday(time.plus(TimeSpan.LASTDAY).plus(TimeSpan.LASTDAY).plus(TimeSpan.LASTDAY)));
	}
	
	@Test
	public void testNextBusinessDayRoller() {
		assertTrue(TimePoint.onSameDay(time, DateRoller.NEXT_BUZ_DAY.roll(time)));
		assertTrue(TimePoint.onSameDay(time, DateRoller.NEXT_BUZ_DAY.roll(time.plus(TimeSpan.LASTDAY))));
		assertTrue(TimePoint.onSameDay(time, DateRoller.NEXT_BUZ_DAY.roll(time.plus(TimeSpan.LASTDAY).plus(TimeSpan.LASTDAY))));
	}
	
	@Test
	public void testModifiedNextBusinessDayRoller() {
		assertTrue(TimePoint.onSameDay(time, DateRoller.MOD_NEXT_BUZ_DAY.roll(time)));
		assertTrue(TimePoint.onSameDay(time, DateRoller.MOD_NEXT_BUZ_DAY.roll(time.plus(TimeSpan.LASTDAY))));
		assertTrue(TimePoint.onSameDay(time, DateRoller.MOD_NEXT_BUZ_DAY.roll(time.plus(TimeSpan.LASTDAY).plus(TimeSpan.LASTDAY))));
		assertTrue(TimePoint.onSameDay(time2.plus(TimeSpan.LASTDAY), DateRoller.MOD_NEXT_BUZ_DAY.roll(time2)));
	}
	
	@Test
	public void testActual365() {
		assertEquals(40.0/365.0, DayBase.ACT365.factor(time2.minus(time)), 0.000001);
	}
	
	private TimePoint time;
	private TimePoint time2;
}
