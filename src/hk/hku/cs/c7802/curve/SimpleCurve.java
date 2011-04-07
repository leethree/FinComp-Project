package hk.hku.cs.c7802.curve;

import java.util.SortedMap;
import java.util.TreeMap;

import hk.hku.cs.c7802.base.time.TimeDiff;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.curve.util.OutOfRangeException;

public class SimpleCurve implements YieldCurve{

	public SimpleCurve(TimePoint timestamp, CurveConfig config) {
		this.timestamp = timestamp;
		this.config = config;
		this.dfpoints = new TreeMap<Long, Double>();
		this.ratepoints = new TreeMap<Long, Double>();
		dfpoints.put(0L, 1.0);
	}
	
	@Override
	public TimePoint getTimestamp() {
		return timestamp;
	}
	
	public void addDataPoint(TimeDiff diff, double disFactor) {
		// TODO how about overwritting?
		dfpoints.put(key(diff), disFactor);
		ratepoints.put(key(diff), config.getCurveRateType().fromDisFactor(disFactor, diff));
	}

	@Override
	public double disFactorAt(TimePoint time) throws OutOfRangeException {
		return disFactorAfter(time.minus(timestamp));
	}

	@Override
	public double disFactorAfter(TimeDiff diff) throws OutOfRangeException {
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
	
	private long key(TimeDiff diff) {
		return diff.getDay();
	}
	
	private TimePoint timestamp;
	private CurveConfig config;
	private SortedMap<Long, Double> dfpoints;
	private SortedMap<Long, Double> ratepoints;
}
