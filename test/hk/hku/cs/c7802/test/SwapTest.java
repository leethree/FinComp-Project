package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;
import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.curve.util.SimpleEvaluator;
import hk.hku.cs.c7802.inst.SwapInstrument;
import hk.hku.cs.c7802.rate.SimpleRate;
import hk.hku.cs.c7802.test.util.ConstantCurve;

import org.junit.Before;
import org.junit.Test;

public class SwapTest {

	@Before
	public void setUp() throws Exception {
		swap = (SwapInstrument) SwapInstrument.create()
									.withCouponInterval(new TimeSpan(0, 3, 0))
									.matruingAfter(new TimeSpan(2, 0, 0))
									.usingRateType(new SimpleRate(DayBase.ACT365))
									.rate(0.05)
									.withTimestamp(TimePoint.parse("2011-03-31 14:59:10 GMT+8"))
									.withName("Swap-2Y")
									.build();
		this.constcurve = new ConstantCurve();
	}

	@Test
	public void testValueWith() {
		assertEquals(1.099863, swap.valueWith(new SimpleEvaluator(constcurve)).getAmount(), 0.000001);
	}

	@Test
	public void testToString() {
		assertEquals("Swap-2Y T2011-03-31 @simple:0.05 P3m M2y.", swap.toString());
	}

	private SwapInstrument swap;
	private YieldCurve constcurve;
}
