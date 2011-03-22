package hk.hku.cs.c7802.market;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.inst.Instrument;

public class MarketData {

	public MarketData(Instrument inst, CashFlow price, TimePoint time) {
		this.inst = inst;
		this.price = price;
		this.time = time;
	}
	
	public Instrument getInst() {
		return inst;
	}
	
	public CashFlow getPrice() {
		return price;
	}
	
	public TimePoint getTime() {
		return time;
	}
	
	private Instrument inst;
	private CashFlow price;
	private TimePoint time;
}
