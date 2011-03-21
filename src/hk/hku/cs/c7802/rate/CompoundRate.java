package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public class CompoundRate extends InterestType {

	public CompoundRate(DayBase base, int feq) {
		super(base);
		if (feq <= 0)
			; // TODO error
		this.frequency = feq;
	}

	@Override
	protected double payOutAfter(double rate, TimeDiff diff) {
		double dayCountFactor = base.factor(diff);
		return Math.pow(1 + dayCountFactor * rate / frequency, frequency * dayCountFactor) - 1;
	}

	@Override
	public double fromDisFactor(double df, TimeDiff diff) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int frequency;
}
