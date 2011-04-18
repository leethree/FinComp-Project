package hk.hku.cs.c7802.model;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.blackscholes.BlackScholesPredictor;
import hk.hku.cs.c7802.blackscholes.BlackScholesSolver;
import hk.hku.cs.c7802.curve.util.OutOfRangeException;
import hk.hku.cs.c7802.option.CallPutOption;
import hk.hku.cs.c7802.option.ImpliedVolatilityEvaluator;
import hk.hku.cs.c7802.option.Option;
import hk.hku.cs.c7802.option.OptionEvaluator;

public class BlackScholesModel extends BaseModel implements OptionEvaluator,
		ImpliedVolatilityEvaluator {
	
	private BlackScholesSolver getSolver(CallPutOption option) {
		TimePoint ref = TimePoint.now();
		double S0 = this.getCurrentStockPrice(option);
		double K = option.getStrike();
		double T = DayBase.ACT365.factor(option.getExpiry().minus(ref));
		double r = this.getContinuousRate(option.getExpiry());
		
		return new BlackScholesSolver(S0, K, T, r);
	}
	
	private BlackScholesPredictor getPredictor(CallPutOption option) {
		TimePoint ref = TimePoint.now();
		double S0 = this.getCurrentStockPrice(option);
		double K = option.getStrike();
		double T = DayBase.ACT365.factor(option.getExpiry().minus(ref));
		double r = this.getContinuousRate(option.getExpiry());
		double sigma = this.getVolatility(option);
		
		return new BlackScholesPredictor(S0, K, T, r, sigma);
	}

	@Override
	public CashFlow evaluate(Option option) {
		if (!(option instanceof CallPutOption))
			throw new IllegalArgumentException(
					"Not supported option type for Black Scholes Model.");
		CallPutOption op = (CallPutOption) option;
		BlackScholesPredictor bsp = getPredictor(op);
		double price = 0;
		if(op.isCall())
			price = bsp.call();
		else 
			price = bsp.put();

		return CashFlow.create(price);
	}

	@Override
	public double implyVolatility(Option option, CashFlow optionPrice) {
		if (!(option instanceof CallPutOption))
			throw new IllegalArgumentException("Not supported option type for Black Scholes Model. Only European Call/Put supported.");
		CallPutOption op = (CallPutOption) option;
		if(!op.isEuropean())
			throw new IllegalArgumentException("Not supported option type for Black Scholes Model. Only European Call/Put supported.");
		
		BlackScholesSolver bss = getSolver(op);
		
		if(op.isCall()) {
			return bss.volatilityFromCall(optionPrice.getAmount()); 
		}
		else {
			return bss.volatilityFromPut(optionPrice.getAmount());
		}
	}
}
