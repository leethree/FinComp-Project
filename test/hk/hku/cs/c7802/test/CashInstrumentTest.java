package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;
import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.curve.util.SimpleEvaluator;
import hk.hku.cs.c7802.inst.CashInstrument;
import hk.hku.cs.c7802.rate.SimpleRate;
import hk.hku.cs.c7802.test.util.ConstantCurve;

import org.junit.Before;
import org.junit.Test;

public class CashInstrumentTest {

	@Before
	public void setUp() throws Exception {
		cash = (CashInstrument) CashInstrument.create()
								.maturingAfter(new TimeSpan(0, 1, 0))
								.usingRateType(new SimpleRate(DayBase.ACT365))
								.rate(0.05)
								.withTimestamp(TimePoint.parse("2011-03-31 14:59:10 GMT+8"))
								.withName("HIBOR-1M")
								.build();
		CurveTest test = new CurveTest();
		test.setUp();
		this.curve = test.getCurve();
		this.constcurve = new ConstantCurve();
	}

	@Test
	public void testValueWith() {
		assertEquals(1.003973, cash.valueWith(new SimpleEvaluator(constcurve)).getAmount(), 0.000001);
		
		// TODO number to be checked manually
		assertEquals(0.964400, cash.valueWith(new SimpleEvaluator(curve)).getAmount(), 0.000001);
	}

	@Test
	public void testToString() {
		assertEquals("HIBOR-1M T2011-03-31 @simple:0.05 M1m.", cash.toString());
	}

	private CashInstrument cash;
	private YieldCurve curve;
	private YieldCurve constcurve;
}
