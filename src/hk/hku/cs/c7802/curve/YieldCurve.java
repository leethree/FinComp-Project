package hk.hku.cs.c7802.curve;

import hk.hku.cs.c7802.base.time.TimeDiff;
import hk.hku.cs.c7802.base.time.TimePoint;

public interface YieldCurve {

	/**
	 * @return Reference time of this curve 
	 */
	public TimePoint getTimestamp();
	
	public double disFactorAt(TimePoint time);
	
	public double disFactorAfter(TimeDiff diff);
	
}
