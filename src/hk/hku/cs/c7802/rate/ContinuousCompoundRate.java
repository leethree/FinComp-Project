package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public class ContinuousCompoundRate extends InterestType{

	public ContinuousCompoundRate(DayBase base) {
		super(base);
	}
	
	// TODO test me
	@Override
	protected double payOutAfter(double rate, TimeDiff diff) {
		return Math.pow(1 + rate, base.factor(diff)) - 1;
	}

	// TODO test me
	@Override
	public double fromDisFactor(double df, TimeDiff diff) {
		return Math.pow(df, - 1 / base.factor(diff));
	}

}
