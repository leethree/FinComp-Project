package hk.hku.cs.c7802.option;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.time.TimePoint;

public interface OptionEvaluator {

	/**
	 * 
	 * @param option The option to be evaluated
	 * @return The cash value of this option 
	 */
	public CashFlow evaluate(Option option, TimePoint ref);
	
}
