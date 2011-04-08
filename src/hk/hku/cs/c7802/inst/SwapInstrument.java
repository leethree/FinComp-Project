package hk.hku.cs.c7802.inst;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.cash.CashStream;
import hk.hku.cs.c7802.base.conv.DateRoller;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;

public class SwapInstrument extends InterestRateInstrument {

	public SwapInstrument(SwapBuilder builder) {
		super(builder);
		this.period = builder.period;
		this.maturity = builder.maturity;
		if ((period == null) || (maturity == null))
			throw new IllegalArgumentException("Missing parameter(s) for Swap Instrument.");
		buildCashStream();
	}
	
	@Override
	public CashFlow valueWith(InstrumentEvaluator ev) {
		stream.accept(ev.getCashStreamVisitor());
		return ev.getValue();
	}
	
	public TimeSpan getMaturity() {
		return maturity;
	}
	
	@Override
	public String toString() {
		return super.toString() + " P" + period + " M" + maturity + ".";
	}
	
	private void buildCashStream() {
		stream = new CashStream();
		// the reference day is the next business day
		TimePoint ref = DateRoller.NEXT_BUZ_DAY.roll(timestamp);
		
		// we'll get our money back on the maturity day
		TimePoint payday = DateRoller.MOD_NEXT_BUZ_DAY.roll(ref.plus(maturity));
		stream.add(CashFlow.create(1), payday);
		
		// time between the day when next coupon will pay-out and reference day
		TimeSpan couponInterval = period;
		TimePoint lastCouponDay = ref;
		TimePoint nextCouponDay = DateRoller.MOD_NEXT_BUZ_DAY.roll(ref.plus(couponInterval));
		while (payday.compareTo(nextCouponDay) >= 0) {
			// we get a coupon every several months, depending on coupon period
			stream.add(CashFlow.create(rateType.payOutAfter(rate, nextCouponDay.minus(lastCouponDay))), nextCouponDay);
			couponInterval = couponInterval.plus(period);
			lastCouponDay = nextCouponDay;
			nextCouponDay = DateRoller.MOD_NEXT_BUZ_DAY.roll(ref.plus(couponInterval));
		}
	}
	
	private TimeSpan period;
	private TimeSpan maturity;
	private CashStream stream;
	
	public static class SwapBuilder extends InterestRateInstrumentBuilder{
		
		protected SwapBuilder(){
		}

		public SwapBuilder matruingAfter(TimeSpan maturity) {
			this.maturity = maturity;
			return this;
		}
		
		public SwapBuilder withCouponInterval(TimeSpan period) {
			this.period = period;
			return this;
		}
		
		@Override
		public Instrument build() {
			return new SwapInstrument(this);
		}
		
		private TimeSpan period;
		private TimeSpan maturity;
	}
	
	public static SwapBuilder create() {
		return new SwapBuilder();
	}
}
