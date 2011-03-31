package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;
import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.curve.util.SimpleEvaluator;
import hk.hku.cs.c7802.inst.CashInstrument;
import hk.hku.cs.c7802.rate.SimpleRate;

import org.junit.Before;
import org.junit.Test;

public class CashInstrumentTest {

	@Before
	public void setUp() throws Exception {
		cash = (CashInstrument) CashInstrument.create()
								.usingRateType(new SimpleRate(DayBase.ACT365))
								.rate(0.05)
								.maturingAfter(new TimeSpan(0, 1, 0))
								.withTimestamp(TimePoint.now())
								.withName("HIBOR-1M")
								.build();
		CurveTest test = new CurveTest();
		test.setUp();
		this.curve = test.getCurve();
	}

	@Test
	public void testValueWith() {
		assertEquals(0.959542, cash.valueWith(new SimpleEvaluator(curve)).getAmount(), 0.000001);
		
//		cash.valueWith(new Instrument.InstrumentEvaluator() {
//			
//			@Override
//			public CashFlow getValue() {
//				return null;
//			}
//			
//			@Override
//			public CashStreamVisitor getCashStreamVisitor() {
//				return new CashStreamVisitor() {
//					
//					@Override
//					public void visit(CashFlow cf, TimePoint tp) {
//						System.out.println(cf + "@" + tp);
//					}
//					
//					@Override
//					public void before() {
//					}
//					
//					@Override
//					public void after() {
//					}
//				};
//			}
//		});
	}

	@Test
	public void testToString() {
		System.out.println(cash.toString());
	}

	private CashInstrument cash;
	private YieldCurve curve;
}
