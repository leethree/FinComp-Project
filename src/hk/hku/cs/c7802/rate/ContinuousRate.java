package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public class ContinuousRate extends InterestType {

	public ContinuousRate(DayBase base) {
		super(base);
	}

	@Override
	public double payOutAfter(double rate, TimeDiff diff) {
		return Math.expm1(rate * dcf(diff));
	}

	@Override
	public double fromDisFactor(double df, TimeDiff diff) {
		return Math.log(1 / df) / dcf(diff);
	}

	@Override
	public String toString() {
		return "continuous-compounding";
	}

}
