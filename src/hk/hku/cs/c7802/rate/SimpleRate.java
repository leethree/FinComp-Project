package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public class SimpleRate extends InterestRate {

	public SimpleRate(DayBase base) {
		this(0, base);
	}
	
	public SimpleRate(double rate, DayBase base) {
		super(rate, base);
	}

	@Override
	public double payOutAfter(TimeDiff diff) {
		return base.factor(diff) * rate;
	}
	
}
