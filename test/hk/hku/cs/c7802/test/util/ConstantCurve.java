package hk.hku.cs.c7802.test.util;

import hk.hku.cs.c7802.base.time.TimeDiff;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.curve.YieldCurve;

public class ConstantCurve implements YieldCurve {

	@Override
	public TimePoint getTimestamp() {
		return null;
	}

	@Override
	public double disFactorAt(TimePoint time) {
		return 1;
	}

	@Override
	public double disFactorAfter(TimeDiff diff) {
		return 1;
	}

}
