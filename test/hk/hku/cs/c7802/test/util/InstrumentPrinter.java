package hk.hku.cs.c7802.test.util;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.cash.CashStream.CashStreamVisitor;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.inst.InterestRateInstrument.InstrumentEvaluator;

public class InstrumentPrinter implements InstrumentEvaluator {

	@Override
	public CashStreamVisitor getCashStreamVisitor() {
		return new CashStreamVisitor() {
			
			@Override
			public void visit(CashFlow cf, TimePoint tp) {
				System.out.println(cf.toString() + "\t:\t" + tp.toCompleteString());
			}
			
			@Override
			public void before() {
				System.out.println("============ cash stream begins ===========");
			}
			
			@Override
			public void after() {
				System.out.println("============ cash stream ends ===========");
			}
		};
	}

	@Override
	public CashFlow getValue() {
		return CashFlow.createEmpty();
	}

}
