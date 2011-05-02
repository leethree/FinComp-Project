package hk.hku.cs.c7802.inst;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.cash.CashStream;
import hk.hku.cs.c7802.base.conv.DateRoller;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;

public class FRAInstrument extends InterestRateInstrument {

	public FRAInstrument(FRABuilder builder) {
		super(builder);
		this.effective = builder.effective;
		this.termination = builder.termination;
		if ((effective == null) || (termination == null))
			throw new IllegalArgumentException("Missing parameter(s) for FRA Instrument.");
		buildCashStream();
	}

	@Override
	public CashFlow valueWith(InstrumentEvaluator ev) {
		stream.accept(ev.getCashStreamVisitor());
		return ev.getValue();
	}

	@Override
	public String toString() {
		return super.toString() + " E" + effective + " M" + termination + ".";
	}
	
	private void buildCashStream() {
		stream = new CashStream();
		// the reference day is the next business day if not today
		TimePoint ref = DateRoller.NEXT_BUZ_DAY.roll(timestamp);
		
		// we will pay $1 on the effective day
		TimePoint effday = DateRoller.MOD_NEXT_BUZ_DAY.roll(ref.plus(effective));
		
		// workaround for T/N
		// effective day should not be earlier than reference day 
		if (effday.minus(ref).getDay() <= 0)
			effday = DateRoller.NEXT_BUZ_DAY.roll(ref.plus(effective));
		stream.add(CashFlow.create(- 1), effday);
		
		// we will get $1+interest on the termination day
		TimePoint termday = DateRoller.MOD_NEXT_BUZ_DAY.roll(ref.plus(termination));
		
		// FIXME temporary workaround for T/N
		// termination day should not be earlier than effective day 
		if (termday.minus(effday).getDay() <= 0)
			termday = DateRoller.NEXT_BUZ_DAY.roll(effday.plus(TimeSpan.NEXTDAY));
		stream.add(CashFlow.create(1 / rateType.disFactorAfter(rate, termday.minus(effday))), termday);
	}
	
	private TimeSpan effective;
	private TimeSpan termination;
	private CashStream stream;
	
	public static class FRABuilder extends InterestRateInstrumentBuilder{
		
		protected FRABuilder(){
		}
		
		public FRABuilder effectiveAfter(TimeSpan efftime) {
			this.effective = efftime;
			return this;
		}
		
		public FRABuilder terminatingAfter(TimeSpan termtime) {
			this.termination = termtime;
			return this;
		}
		
		@Override
		public FRAInstrument build() {
			return new FRAInstrument(this);
		}
		
		private TimeSpan effective;
		private TimeSpan termination;
	}
	
	public static FRABuilder create() {
		return new FRABuilder();
	}
}
