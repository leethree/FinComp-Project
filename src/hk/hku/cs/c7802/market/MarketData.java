package hk.hku.cs.c7802.market;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.inst.Instrument;

public class MarketData{

	public MarketData(Instrument inst, CashFlow price) {
		this.inst = inst;
		this.price = price;
	}
	
	public Instrument getInst() {
		return inst;
	}
	
	public CashFlow getPrice() {
		return price;
	}
	
	public TimePoint getTimestamp() {
		return inst.getTimestamp();
	}
	
	protected Instrument inst;
	protected CashFlow price;
}
