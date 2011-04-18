package hk.hku.cs.c7802.curve.util;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.cash.CashStream.CashStreamVisitor;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.inst.InterestRateInstrument.InstrumentEvaluator;

public class ReverseSolver implements InstrumentEvaluator {

	public ReverseSolver(YieldCurve curve) {
		this.curve = curve;
		this.eva = new CashStreamRecorder();
	}
	
	@Override
	public CashStreamVisitor getCashStreamVisitor() {
		return eva;
	}

	@Override
	public CashFlow getValue() {
		return null;
	}
	
	/**
	 * 
	 * @param value Actual value of the instrument
	 * @return true if solved successfully
	 */
	public boolean solve(CashFlow value) {
		if (eva.pendingFlow == null || eva.failed)
			return false;
		CashFlow pendingValue = value.minus(eva.presentValue);
		if (pendingValue.getCurrency() != eva.pendingFlow.getCurrency())
			return false;
		df = pendingValue.getAmount() / eva.pendingFlow.getAmount();
		return true;
	}
	
	public double getSolutionDf() {
		return df;
	}
	
	public TimePoint getSolutionTime() {
		return eva.pendingTime;
	}
	
	private double df;
	private YieldCurve curve;
	private CashStreamRecorder eva;
	
	class CashStreamRecorder implements CashStreamVisitor{

		private CashFlow presentValue;
		private CashFlow pendingFlow;
		private TimePoint pendingTime;
		private boolean failed;
		
		@Override
		public void before() {
			presentValue = CashFlow.createEmpty();
			pendingFlow = null;
			pendingTime = null;
			failed = false;
		}

		@Override
		public void visit(CashFlow cf, TimePoint tp) {
			if (presentValue == null)
				return; // do nothing if the evaluation already fails
			try {
				// Present Value += Future Value * Discount Factor
				presentValue = presentValue.plus(cf.multiply(curve.disFactorAt(tp)));
			} catch (OutOfRangeException e) {
				if (pendingFlow == null) {
					// record the current point
					pendingFlow = cf;
					pendingTime = tp;
				} else {
					// we cannot solve more than one pending cash flow
					failed = true;
				}
			}
		}

		@Override
		public void after() {
			// TODO why empty?
		}
	}
}
