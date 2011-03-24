package hk.hku.cs.c7802.inst;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.cash.CashStream;
import hk.hku.cs.c7802.base.conv.DateRoller;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;
import hk.hku.cs.c7802.rate.InterestType;

public class CashInstrument extends Instrument {

	private CashInstrument(CashInstrumentBuilder builder) {
		super(builder);
		this.rate = builder.rate;
		this.rateType = builder.rateType;
		this.maturity = builder.maturity;
		if ((rateType == null) || (maturity == null))
			throw new IllegalArgumentException("Missing parameter(s) for Cash Instrument.");
		buildCashStream();
	}

	@Override
	public CashFlow valueWith(InstrumentEvaluator ev) {
		stream.accept(ev.getCashStreamVisitor());
		return ev.getValue();
	}

	@Override
	public String toString() {
		return super.toString() + " [" + rate + ", " + maturity + "]";
	}
	
	public static class CashInstrumentBuilder extends InstrumentBuilder{
		
		protected CashInstrumentBuilder(){
		}
		
		public CashInstrumentBuilder rate(double rate) {
			this.rate = rate;
			return this;
		}
		
		public CashInstrumentBuilder usingRateType(InterestType rateType) {
			this.rateType = rateType;
			return this;
		}
		
		public CashInstrumentBuilder maturingAfter(TimeSpan maturity) {
			this.maturity = maturity;
			return this;
		}
		
		@Override
		public CashInstrument build() {
			return new CashInstrument(this);
		}
		
		private double rate = 0;
		private InterestType rateType;
		private TimeSpan maturity;
	}
	
	public static CashInstrumentBuilder create() {
		return new CashInstrumentBuilder();
	}
	
	private void buildCashStream() {
		stream = new CashStream();
		// suppose we save $1 on the next business day, i.e., the reference day
		TimePoint ref = DateRoller.NEXT_BUZ_DAY.roll(timestamp);

		// we'll get $1+interest on the pay-out day
		TimePoint payday = DateRoller.MOD_NEXT_BUZ_DAY.roll(ref.plus(maturity));
		stream.add(CashFlow.create(1 / rateType.disFactorAfter(rate, payday.minus(ref))), payday);
	}
	
	private double rate;
	private InterestType rateType;
	private TimeSpan maturity;
	private CashStream stream;
}
