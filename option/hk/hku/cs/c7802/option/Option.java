package hk.hku.cs.c7802.option;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.inst.Instrument;
import hk.hku.cs.c7802.stock.Stock;

public abstract class Option extends Instrument{
	
	protected Option(OptionBuilder builder) {
		super(builder);
		this.stock = builder.stock;
		this.expiry = builder.expiry;
		if ((stock == null) || (expiry == null))
			throw new IllegalArgumentException("Missing parameter(s) for Option: stock or expiry");
	}

	public abstract CashFlow payout(StockPricer pricer);
	
	public Stock getStock() {
		return stock;
	}
	
	public TimePoint getExpiry() {
		return expiry;
	}
	
	protected Stock stock;
	protected TimePoint expiry;
	
	public static abstract class OptionBuilder extends InstrumentBuilder{
		
		protected OptionBuilder(){
		}
		
		public OptionBuilder dependingOn(Stock stock) {
			this.stock = stock;
			return this;
		}
		
		public OptionBuilder expiringAt(TimePoint expiry) {
			this.expiry = expiry;
			return this;
		}
		
		private Stock stock;
		private TimePoint expiry;
	}
	
	public static interface StockPricer {
		public double priceAt(TimePoint time);
		
		public double maxBefore(TimePoint time);
		
		public double minBefore(TimePoint time);
	}
}
