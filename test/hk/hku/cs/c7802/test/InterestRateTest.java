package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;
import hk.hku.cs.c7802.rate.CompoundRate;
import hk.hku.cs.c7802.rate.ContinuousRate;
import hk.hku.cs.c7802.rate.InterestType;
import hk.hku.cs.c7802.rate.SimpleRate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InterestRateTest {

	@Before
	public void setUp() throws Exception {
		simpleRate = new SimpleRate(DayBase.ACT365);
		comRate = new CompoundRate(DayBase.ACT365, 4);
		contRate = new ContinuousRate(DayBase.ACT365);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimpleRate() {
		double df = simpleRate.disFactorAfter(0.05, new TimeDiff(50));
		assertEquals(0.993197, df, 0.000001);
		double rate = simpleRate.fromDisFactor(df, new TimeDiff(50));
		assertEquals(0.05, rate, 0.000001);
		
		rate = comRate.fromDisFactor(1.0, new TimeDiff(0));
		assertEquals(0, rate, 0.0);
	}
	
	@Test
	public void testCompoundRate() {
		double df = comRate.disFactorAfter(0.05, new TimeDiff(50));
		assertEquals(0.993216, df, 0.000001);
		double rate = comRate.fromDisFactor(df, new TimeDiff(50));
		assertEquals(0.05, rate, 0.000001);
		
		rate = comRate.fromDisFactor(1.0, new TimeDiff(0));
		assertEquals(0, rate, 0.0);
	}
	
	@Test
	public void testContinuousRate() {
		double df = contRate.disFactorAfter(0.05, new TimeDiff(50));
		assertEquals(0.993174, df, 0.000001);
		double rate = contRate.fromDisFactor(df, new TimeDiff(50));
		assertEquals(0.05, rate, 0.000001);
		
		rate = contRate.fromDisFactor(1.0, new TimeDiff(0));
		assertEquals(0, rate, 0.0);
	}
	
	private InterestType simpleRate;
	private InterestType comRate;
	private InterestType contRate;
}
