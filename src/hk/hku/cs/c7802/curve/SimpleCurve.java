package hk.hku.cs.c7802.curve;

import java.util.SortedMap;

import hk.hku.cs.c7802.base.time.TimeDiff;
import hk.hku.cs.c7802.base.time.TimePoint;

public class SimpleCurve implements YieldCurve{

	public SimpleCurve(TimePoint timestamp, CurveConfig config) {
		this.timestamp = timestamp;
		this.config = config;
	}
	
	@Override
	public TimePoint getTimestamp() {
		return timestamp;
	}
	
	public void addDataPoint(TimeDiff diff, double disFactor) {
		dfpoints.put(key(diff), disFactor);
		ratepoints.put(key(diff), config.getCurveRateType().fromDisFactor(disFactor, diff));
	}

	@Override
	public double disFactorAt(TimePoint time) {
		return disFactorAfter(time.diff(timestamp));
	}

	@Override
	public double disFactorAfter(TimeDiff diff) {
		// find saved discount factor
		Double ret = dfpoints.get(key(diff));
		// if nothing found, do interpolation
		if (ret == null) {
			double rate = config.getInterpolator().interpolate(key(diff), ratepoints);
			ret = config.getCurveRateType().disFactorAfter(rate, diff);
			// save interpolation result
			dfpoints.put(key(diff), ret);
		}
		return ret;
	}
	
	private double key(TimeDiff diff) {
		return diff.getDay();
	}
	
	private TimePoint timestamp;
	private CurveConfig config;
	private SortedMap<Double, Double> dfpoints;
	private SortedMap<Double, Double> ratepoints;
}
