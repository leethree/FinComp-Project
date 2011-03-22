package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import hk.hku.cs.c7802.base.BaseException;
import hk.hku.cs.c7802.base.BaseRuntimeException;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;

import org.junit.Before;
import org.junit.Test;

public class TimeTest {

	@Before
	public void setUp() throws Exception {
		time = TimePoint.parse("2011-03-21 17:18:42 GMT+8");
	}

	@Test
	public void testToString() {
		assertEquals("2011-03-21 17:18:42 GMT+08:00", time.toString());
	}

	@Test
	public void testDiff() {
		try {
			TimePoint time2 = TimePoint.parse("2011-02-20 23:18:42 GMT+8");
			TimePoint time3 = TimePoint.parse("2012-01-12 17:18:42 GMT+8");
			assertEquals(-29, time2.minus(time).getDay());
			assertEquals(297, time3.minus(time).getDay());
		} catch (BaseException e) {
			fail(e.getMessage());
		}
	}
	
	@Test(expected=BaseRuntimeException.class)
	public void testDiffEx() throws BaseException {
		TimePoint time4 = TimePoint.parse("2012-01-12 17:18:42 GMT+2");
		time4.minus(time);
	}
	
	@Test
	public void testOffset() throws Exception {
		TimePoint time2 = TimePoint.parse("2011-01-30 23:18:42 GMT+8");
		assertEquals("2012-03-01 23:18:42 GMT+08:00", time2.plus(new TimeSpan(1, 1, 1)).toString());
		assertTrue(TimePoint.onSameDay(time2, time.plus(time2.minus(time))));
	}
	
	private TimePoint time;
}
