package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.curve.CurveConfig;
import hk.hku.cs.c7802.curve.SimpleCurve;
import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.curve.util.LinearInterpolator;
import hk.hku.cs.c7802.rate.CompoundRate;

import org.junit.Before;
import org.junit.Test;

public class CurveTest {

	@Before
	public void setUp() throws Exception {
		CurveConfig config = new CurveConfig();
		config.setCurveRateType(new CompoundRate(DayBase.ACT365, 4));
		config.setInterpolator(new LinearInterpolator());
		SimpleCurve s = new SimpleCurve(TimePoint.now(), config);
		s.addDataPoint(new TimeDiff(50), 0.92);
		s.addDataPoint(new TimeDiff(1), 0.999);
		this.curve = s;
	}

	@Test
	public void testDisFactorAt() {
		assertEquals(0.975036, curve.disFactorAfter(new TimeDiff(20)), 0.000001);
	}

	public YieldCurve getCurve() {
		return curve;
	}
	
	private YieldCurve curve;
}