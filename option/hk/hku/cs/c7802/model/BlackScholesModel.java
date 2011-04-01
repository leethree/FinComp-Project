package hk.hku.cs.c7802.model;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.option.CallPutOption;
import hk.hku.cs.c7802.option.Option;
import hk.hku.cs.c7802.option.OptionEvaluator;

public class BlackScholesModel extends BaseModel implements OptionEvaluator {
	
	@Override
	public CashFlow evaluate(Option option) {
		if (!(option instanceof CallPutOption))
			throw new IllegalArgumentException("Not supported option type for Black Scholes Model.");
		CallPutOption op = (CallPutOption) option;
		// Current stock price
		double initPrice = data.getPrice(op.getStock());
		// Volatility of the stock
		double volatility = data.getVolatilities(op.getStock());
		// TODO Auto-generated method stub
		return null;
	}
	
	public double implyVolatility(Option option, CashFlow optionPrice) {
		if (!(option instanceof CallPutOption))
			throw new IllegalArgumentException("Not supported option type for Black Scholes Model.");
		CallPutOption op = (CallPutOption) option;
		// Current stock price
		double stockPrice = data.getPrice(op.getStock());
		// TODO Auto-generated method stub
		return 0;
	}

}
