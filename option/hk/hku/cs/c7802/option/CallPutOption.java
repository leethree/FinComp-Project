package hk.hku.cs.c7802.option;

import hk.hku.cs.c7802.base.cash.CashFlow;

public class CallPutOption extends VanillaOption {

	protected CallPutOption(CallPutOptionBuilder builder) {
		super(builder);
		this.strike = builder.strike;
		this.isCall = builder.isCall;
	}

	@Override
	public CashFlow payout(double stockPrice) {
		double payout = stockPrice - strike;
		if (isCall && payout > 0)
			return CashFlow.create(stock.getCurrency(), payout);
		else if (!isCall && payout < 0)
			return CashFlow.create(stock.getCurrency(), - payout);
		else
			return CashFlow.createEmpty();
	}

	public double getStrike() {
		return strike;
	}
	
	public boolean isCall() {
		return isCall;
	}
	
	public boolean isPut() {
		return !isCall;
	}
	
	private double strike;
	private boolean isCall;
	
	public static class CallPutOptionBuilder extends VanillaOptionBuilder{
		
		protected CallPutOptionBuilder() {
		}

		public CallPutOptionBuilder withStrike(double strike) {
			this.strike = strike;
			return this;
		}
		
		@Override
		public CallPutOption build() {
			return new CallPutOption(this);
		}
		
		private double strike;
		private boolean isCall;
	}
	
	public static CallPutOptionBuilder createCall() {
		CallPutOptionBuilder ret = new CallPutOptionBuilder();
		ret.isCall = true;
		return ret;
	}
	
	public static CallPutOptionBuilder createPut() {
		CallPutOptionBuilder ret = new CallPutOptionBuilder();
		ret.isCall = false;
		return ret;
	}
}
