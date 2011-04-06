package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public class CompoundRate extends InterestType {

	public CompoundRate(DayBase base, int feq) {
		super(base);
		if (feq <= 0)
			throw new IllegalArgumentException("Compounding frequency for interest rate can not be 0.");
		this.frequency = feq;
	}

	// TODO test me
	@Override
	protected double payOutAfter(double rate, TimeDiff diff) {
		return Math.pow(1 + rate / frequency, frequency * dcf(diff)) - 1;
	}

	// TODO test me
	@Override
	public double fromDisFactor(double df, TimeDiff diff) {
		double acc = Math.pow(1 / df, 1 / (frequency * dcf(diff)));
		return frequency * (acc - 1);
	}

	private int frequency;
}
