package hk.hku.cs.c7802.model;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.option.Option;
import hk.hku.cs.c7802.option.OptionEvaluator;
import hk.hku.cs.c7802.option.VanillaOption;

public class MonteCarloModel extends BaseModel implements OptionEvaluator {

	@Override
	public CashFlow evaluate(Option option) {
		if (option instanceof VanillaOption)
			return evaluate((VanillaOption) option);
		// Current stock price
		double initPrice = data.getPrice(option.getStock());
		// Volatility of the stock
		double volatility = data.getVolatilities(option.getStock());
		// TODO Not yet implemented
		return null;
	}
	
	public CashFlow evaluate(VanillaOption option) {
		if (!option.isEuropean())
			throw new IllegalArgumentException("Not supported option type for Monte Carlo Model.");
		// TODO Not yet implemented
		return null;
	}

}
