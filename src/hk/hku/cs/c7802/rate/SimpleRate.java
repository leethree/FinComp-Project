package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public class SimpleRate extends InterestType {

	public SimpleRate(DayBase base) {
		super(base);
	}

	@Override
	protected double payOutAfter(double rate, TimeDiff diff) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double fromDisFactor(double df, TimeDiff diff) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
