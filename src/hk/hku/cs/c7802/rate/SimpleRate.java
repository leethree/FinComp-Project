package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public class SimpleRate extends InterestType {

	public SimpleRate(DayBase base) {
		super(base);
	}

	// TODO test me
	@Override
	protected double payOutAfter(double rate, TimeDiff diff) {
		return rate * dcf(diff);
	}

	// TODO test me
	@Override
	public double fromDisFactor(double df, TimeDiff diff) {
		return (1 / df - 1) / dcf(diff);
	}
	
}
