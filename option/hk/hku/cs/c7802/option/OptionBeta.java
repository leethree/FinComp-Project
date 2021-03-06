package hk.hku.cs.c7802.option;

import hk.hku.cs.c7802.base.cash.CashFlow;

public class OptionBeta extends Option implements IEuropeanOption{

	protected OptionBeta(OptionBetaBuilder builder) {
		super(builder);
	}

	@Override
	public CashFlow payout(StockPricer pricer) {
		double payout = pricer.maxBefore(expiry) - pricer.minBefore(expiry);
		if (payout >= 50)
			return CashFlow.create(stock.getCurrency(), 0.5 * payout);
		else if (payout < 50 && payout >= 20)
			return CashFlow.create(stock.getCurrency(), payout);
		return CashFlow.createEmpty();
	}

	public static class OptionBetaBuilder extends OptionBuilder{
		
		protected OptionBetaBuilder(){
		}

		@Override
		public OptionBeta build() {
			return new OptionBeta(this);
		}
	}
	
	public static OptionBetaBuilder create() {
		return new OptionBetaBuilder();
	}

	@Override
	public boolean isEuropean() {
		return true;
	}
}
