package hk.hku.cs.c7802.rate;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimeDiff;

public abstract class InterestRate {

	public InterestRate(double rate, DayBase base) {
		this.rate = rate;
		this.base = base;
	}
	
	/**
	 * 
	 * @param diff Time duration for interest calculation
	 * @return Pay-out interest amount for principal = 1
	 */
	public abstract double payOutAfter(TimeDiff diff);
	
	/***
	 * 
	 * @param diff Time duration for interest calculation
	 * @return The discount factor when the interest is payed-out
	 */
	public double disFactorAfter(TimeDiff diff) {
		return 1 / (1 + payOutAfter(diff));
	}
	
	protected double rate;
	protected DayBase base;
}
