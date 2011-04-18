package hk.hku.cs.c7802.inst;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.cash.CashStream.CashStreamVisitor;
import hk.hku.cs.c7802.rate.InterestType;

public abstract class InterestRateInstrument extends Instrument {

	public InterestRateInstrument(InterestRateInstrumentBuilder builder) {
		super(builder);
		this.rate = builder.rate;
		this.rateType = builder.rateType;
		if (rateType == null)
			throw new IllegalArgumentException("Missing parameter(s) for Interest Rate Instrument.");
	}
	
	public abstract CashFlow valueWith(InstrumentEvaluator ev);
	
	public InterestType getRateType() {
		return rateType;
	}
	
	public double getRate() {
		return rate;
	}
	
	@Override
	public String toString() {
		return super.toString() + " @" + rateType + ":" + rate;
	}
	
	protected double rate;
	protected InterestType rateType;
	
	public static abstract class InterestRateInstrumentBuilder extends InstrumentBuilder{
		
		protected InterestRateInstrumentBuilder(){
		}
		
		public InterestRateInstrumentBuilder rate(double rate) {
			this.rate = rate;
			return this;
		}
		
		public InterestRateInstrumentBuilder usingRateType(InterestType rateType) {
			this.rateType = rateType;
			return this;
		}
		
		@Override
		public abstract InterestRateInstrument build();
		
		private double rate = 0;
		private InterestType rateType;
	}
	
	public static interface InstrumentEvaluator {
		
		public CashStreamVisitor getCashStreamVisitor();
		
		public CashFlow getValue();
	}
}
