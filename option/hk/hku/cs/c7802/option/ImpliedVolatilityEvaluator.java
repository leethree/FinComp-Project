package hk.hku.cs.c7802.option;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.time.TimePoint;

public interface ImpliedVolatilityEvaluator {
	public double implyVolatility(Option option, CashFlow optionPrice, TimePoint ref);
}
