package hk.hku.cs.c7802.option;

import hk.hku.cs.c7802.base.cash.CashFlow;

/**
 * Vanilla Options are options whose pay-out only depends on the price of the underlying stock at expiry.
 * It could be either European or American.
 *
 */
public abstract class VanillaOption extends Option {

	protected VanillaOption(VanillaOptionBuilder builder) {
		super(builder);
		this.style = builder.style;
		if (style == null)
			throw new IllegalArgumentException("Missing parameter(s) for Vanilla Option.");
	}

	public abstract CashFlow payout(double stockPrice);
	
	@Override
	public final CashFlow payout(StockPricer pricer) {
		return payout(pricer.priceAt(expiry));
	}

	public boolean isEuropean() {
		return style == Style.EUROPEAN;
	}
	
	public boolean isAmerican() {
		return style == Style.AMERICAN;
	}
	
	protected Style style;
	
	public static abstract class VanillaOptionBuilder extends OptionBuilder{
		
		protected VanillaOptionBuilder(){
		}
		
		public VanillaOptionBuilder european() {
			this.style = Style.EUROPEAN;
			return this;
		}
		
		public VanillaOptionBuilder american() {
			this.style = Style.AMERICAN;
			return this;
		}
		
		private Style style;
	}
	
	private static enum Style {EUROPEAN, AMERICAN};
}
