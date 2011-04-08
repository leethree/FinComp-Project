package hk.hku.cs.c7802.curve;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.rate.ContinuousRate;

public class PerfectCurve implements YieldCurve{
	private TimePoint timepoint;
	private ContinuousRate rateType;
	private DayBase dayBase;
	private double rate;
	
	public PerfectCurve(TimePoint timepoint, double rate, DayBase dayBase) {
		this.timepoint = timepoint;
		this.rate = rate;
		this.dayBase = dayBase;
		rateType = new ContinuousRate(this.dayBase);
	}
	
	public PerfectCurve(TimePoint timepoint, double rate) {
		this(timepoint, rate, DayBase.ACT365);
	}

	@Override
	public TimePoint getTimestamp() {
		return timepoint;
	}

	@Override
	public double disFactorAt(TimePoint time) {
		return disFactorAfter(time.minus(timepoint));
	}

	@Override
	public double disFactorAfter(TimeDiff diff) {
		return rateType.disFactorAfter(rate, diff);
	}
	
	public double disFactorAfter(double deltaT) {
		throw new RuntimeException("Not yet implemented");
	}

}