package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public class SimpleRate extends InterestType {

	public SimpleRate(DayBase base) {
		super(base);
	}

	@Override
	public double payOutAfter(double rate, TimeDiff diff) {
		return rate * dcf(diff);
	}

	@Override
	public double fromDisFactor(double df, TimeDiff diff) {
		double t = dcf(diff);
		if(t == 0)
			return 0;
		return (1 / df - 1) / t;
	}

	@Override
	public String toString() {
		return "simple";
	}
	
}
