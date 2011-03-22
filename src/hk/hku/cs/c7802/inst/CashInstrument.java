package hk.hku.cs.c7802.inst;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.cash.CashStream;
import hk.hku.cs.c7802.base.time.TimeSpan;
import hk.hku.cs.c7802.rate.InterestType;

public class CashInstrument implements Instrument {

	public CashInstrument(InterestType rate, TimeSpan maturity) {
		this.rate = rate;
		this.maturity = maturity;
		buildCashStream();
	}

	@Override
	public CashFlow valueWith(InstrumentEvaluator ev) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void buildCashStream() {
		// TODO
		stream = null;
	}

	private InterestType rate;
	private TimeSpan maturity;
	private CashStream stream;
}
