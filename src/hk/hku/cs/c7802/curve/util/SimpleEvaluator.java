package hk.hku.cs.c7802.curve.util;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.cash.CashStream.CashStreamVisitor;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.inst.InterestRateInstrument.InstrumentEvaluator;

public class SimpleEvaluator implements InstrumentEvaluator {

	public SimpleEvaluator(YieldCurve curve) {
		this.curve = curve;
		this.eva = new CashStreamEvaluator();
	}
	
	@Override
	public CashStreamVisitor getCashStreamVisitor() {
		return eva;
	}

	@Override
	public CashFlow getValue() {
		return eva.presentValue;
	}
	
	private YieldCurve curve;
	private CashStreamEvaluator eva;
	
	class CashStreamEvaluator implements CashStreamVisitor{

		private CashFlow presentValue;
		
		@Override
		public void before() {
			presentValue = CashFlow.createEmpty();
		}

		@Override
		public void visit(CashFlow cf, TimePoint tp) {
			if (presentValue == null)
				return; // do nothing if the evaluation already fails
			try {
				// Present Value += Future Value * Discount Factor
				presentValue = presentValue.plus(cf.multiply(curve.disFactorAt(tp)));
			} catch (OutOfRangeException e) {
				// evaluation fails
				presentValue = null;
			}
		}

		@Override
		public void after() {
		}
		
	}
}
