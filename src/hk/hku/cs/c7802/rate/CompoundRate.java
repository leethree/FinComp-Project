package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public class CompoundRate extends InterestRate {

	public CompoundRate(DayBase base, int feq) {
		this(0, base, feq);
	}
	
	public CompoundRate(double rate, DayBase base, int feq) {
		super(rate, base);
		if (feq <= 0)
			; // TODO error
		this.frequency = feq;
	}

	@Override
	public double payOutAfter(TimeDiff diff) {
		double dayCountFactor = base.factor(diff);
		return Math.pow(1 + dayCountFactor * rate / frequency, frequency * dayCountFactor) - 1;
	}
	
	private int frequency;
}
