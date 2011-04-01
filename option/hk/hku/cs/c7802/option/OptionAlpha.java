package hk.hku.cs.c7802.option;

import hk.hku.cs.c7802.base.cash.CashFlow;

public class OptionAlpha extends VanillaOption {

	protected OptionAlpha(OptionAlphaBuilder builder) {
		super(builder);
	}

	@Override
	public CashFlow payout(double stockPrice) {
		double payout = stockPrice - 100;
		if (payout > 0 && payout <= 25)
			return CashFlow.create(stock.getCurrency(), payout);
		else if (payout < 0 && payout >= -25)
			return CashFlow.create(stock.getCurrency(), - payout);
		else
			return CashFlow.createEmpty();
	}

	public static class OptionAlphaBuilder extends VanillaOptionBuilder{
		
		protected OptionAlphaBuilder(){
		}
		
		@Override
		public OptionAlpha build() {
			return new OptionAlpha(this);
		}
	}
	
	public static OptionAlphaBuilder create() {
		return new OptionAlphaBuilder();
	}
}
