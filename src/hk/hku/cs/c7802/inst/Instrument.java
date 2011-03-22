package hk.hku.cs.c7802.inst;

import hk.hku.cs.c7802.base.cash.CashFlow;

public interface Instrument {

	public CashFlow valueWith(InstrumentEvaluator ev);
	
	public interface InstrumentEvaluator {
		
	}
}
