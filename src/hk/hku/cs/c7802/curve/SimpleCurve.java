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
	}

	@Override
	public double disFactorAt(TimePoint time) {
		return disFactorAfter(time.diff(timestamp));
	}

	@Override
	public double disFactorAfter(TimeDiff diff) {
		// TODO the following is not correct
		config.getInterpolator().interpolate(key(diff), datapoints);
		return 0;
	}
	
	private double key(TimeDiff diff) {
		return diff.getDay();
	}
	
	private TimePoint timestamp;
	private CurveConfig config;
	private SortedMap<Double, Double> datapoints;
}
