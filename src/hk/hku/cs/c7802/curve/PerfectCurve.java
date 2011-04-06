package hk.hku.cs.c7802.curve;

import hk.hku.cs.c7802.base.time.TimeDiff;
import hk.hku.cs.c7802.base.time.TimePoint;

public class PerfectCurve implements YieldCurve{
	private TimePoint timepoint;
	private double rate;
	
	public PerfectCurve(TimePoint timepoint, double rate) {
		this.timepoint = timepoint;
		this.rate = rate;
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
		return disFactorAfter(diff.getDay()/365.0); 
	}
	
	public double disFactorAfter(double deltaT) {
		return Math.pow(1 + rate, -deltaT);
	}

}
