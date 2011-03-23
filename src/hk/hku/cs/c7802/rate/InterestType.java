package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public abstract class InterestType {

	public InterestType(DayBase base) {
		this.base = base;
	}
	
	/**
	 * 
	 * @param rate Rate of Interest
	 * @param diff Time duration for interest calculation
	 * @return Pay-out interest amount for principal = 1
	 */
	protected abstract double payOutAfter(double rate, TimeDiff diff);
	
	/***
	 * 
	 * @param rate Rate of Interest
	 * @param diff Time duration for interest calculation
	 * @return The discount factor when the interest is payed-out
	 */
	public double disFactorAfter(double rate, TimeDiff diff) {
		return 1 / (1 + payOutAfter(rate, diff));
	}
	
	/***
	 * 
	 * @param df Discount factor
	 * @param diff Time duration
	 * @return Rate of Interest
	 */
	public abstract double fromDisFactor(double df, TimeDiff diff);
	
	/**
	 * 
	 * @param diff
	 * @return The day count factor for the period of time
	 */
	protected double dcf(TimeDiff diff) {
		return base.factor(diff);
	}
	
	protected DayBase base;
}
