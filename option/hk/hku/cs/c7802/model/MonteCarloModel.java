package hk.hku.cs.c7802.model;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.montecarlo.Antithetic;
import hk.hku.cs.c7802.montecarlo.BasicMonteCarlo;
import hk.hku.cs.c7802.montecarlo.CachedRandomGenerator;
import hk.hku.cs.c7802.montecarlo.MonteCarloOption;
import hk.hku.cs.c7802.montecarlo.NormalGenerator;
import hk.hku.cs.c7802.montecarlo.RandomGenerator;
import hk.hku.cs.c7802.option.Option;
import hk.hku.cs.c7802.option.OptionEvaluator;
import hk.hku.cs.c7802.option.VanillaOption;
import hk.hku.cs.c7802.option.IEuropeanOption;

public class MonteCarloModel extends BaseModel implements OptionEvaluator {

	@Override
	public CashFlow evaluate(Option option) {
		if(option instanceof IEuropeanOption && !((IEuropeanOption)option).isEuropean())
			throw new IllegalArgumentException("Not supported option type for Monte Carlo Model.");
		return evaluateOption(option);
	}
	
	private CashFlow evaluateOption(Option option) {
		if ((option instanceof VanillaOption) && !((VanillaOption)option).isEuropean())
			throw new IllegalArgumentException("Not supported option type for Monte Carlo Model.");
		
		TimePoint ref = TimePoint.now();
		double S0 = this.getCurrentStockPrice(option);		
		double T = DayBase.ACT365.factor(option.getExpiry().minus(ref));
		double r = this.getContinuousRate(option.getExpiry());
		double sigma = this.getVolatility(option);
		
		BasicMonteCarlo mc = new BasicMonteCarlo(10000, 1000);
		CachedRandomGenerator crg = new CachedRandomGenerator(new NormalGenerator.BoxMuller2(), mc.numberOfRandomNeeded());
		RandomGenerator rg = new Antithetic(crg);
		rg.setSeed(2);
		
		double v = mc.value(rg, new MonteCarloOption.Adaptor(option), S0, T, r, sigma);
		v = v * this.getDiscountFactor(option.getExpiry());
		double error0 = mc.error(0);
		double error = mc.error(5);
		return CashFlow.create(v);
	}


}
