package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;
import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.curve.util.SimpleEvaluator;
import hk.hku.cs.c7802.inst.FRAInstrument;
import hk.hku.cs.c7802.rate.SimpleRate;
import hk.hku.cs.c7802.test.util.ConstantCurve;

import org.junit.Before;
import org.junit.Test;

public class FRATest {

	@Before
	public void setUp() throws Exception {
		fra = (FRAInstrument) FRAInstrument.create()
								.effectiveAfter(new TimeSpan(0, 3, 0))
								.terminatingAfter(new TimeSpan(0, 6, 0))
								.usingRateType(new SimpleRate(DayBase.ACT365))
								.rate(0.05)
								.withTimestamp(TimePoint.parse("2011-03-31 14:59:10 GMT+8"))
								.withName("FRA-3x6")
								.build();
		this.constcurve = new ConstantCurve();
	}
	
	@Test
	public void testValueWith() {
		assertEquals(0.012603, fra.valueWith(new SimpleEvaluator(constcurve)).getAmount(), 0.000001);
	}
	
	@Test
	public void testToString() {
		assertEquals("FRA-3x6 T2011-03-31 @simple:0.05 E3m M6m.", fra.toString());
	}
	
	private FRAInstrument fra;
	private YieldCurve constcurve;
}
