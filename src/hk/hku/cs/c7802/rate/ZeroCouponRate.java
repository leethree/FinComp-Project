package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public class ZeroCouponRate extends CompoundRate {

	public ZeroCouponRate(DayBase base) {
		this(0, base);
	}
	
	public ZeroCouponRate(double rate, DayBase base) {
		super(rate, base, 4);
	}

	public void fromDisFactor(double df, TimeDiff diff) {
		double dayCountFactor = base.factor(diff);
		// TODO
		this.rate = 0;
	}
	
}
