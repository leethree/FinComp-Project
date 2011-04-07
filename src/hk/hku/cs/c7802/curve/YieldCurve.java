package hk.hku.cs.c7802.curve;

import hk.hku.cs.c7802.base.time.TimeDiff;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.curve.util.OutOfRangeException;

public interface YieldCurve {

	/**
	 * @return Reference time of this curve 
	 */
	public TimePoint getTimestamp();
	
	public double disFactorAt(TimePoint time) throws OutOfRangeException;
	
	public double disFactorAfter(TimeDiff diff) throws OutOfRangeException;
	
}
