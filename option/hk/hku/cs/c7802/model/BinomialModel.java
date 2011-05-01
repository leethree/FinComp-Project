package hk.hku.cs.c7802.model;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.binomialtree.BasicBinomialTree;
import hk.hku.cs.c7802.binomialtree.BinomialTreeOption;
import hk.hku.cs.c7802.option.Option;
import hk.hku.cs.c7802.option.OptionEvaluator;
import hk.hku.cs.c7802.option.VanillaOption;

public class BinomialModel extends BaseModel implements OptionEvaluator {
	@Override
	public CashFlow evaluate(Option option, TimePoint ref) {
		if (!(option instanceof VanillaOption))
			throw new IllegalArgumentException("Not supported option type for Binomial Model.");
		VanillaOption op = (VanillaOption) option;
	
		double S0 = this.getCurrentStockPrice(option);		
		double T = DayBase.ACT365.factor(option.getExpiry().minus(ref));
		double r = this.getContinuousRate(option.getExpiry());
		double sigma = this.getVolatility(option);
	
		BasicBinomialTree mc = new BasicBinomialTree(10);
		
		double v=mc.value(new BinomialTreeOption.Adaptor(option), 
				S0, T, r, sigma, op.isEuropean());
		return CashFlow.create(v);
	}
}
