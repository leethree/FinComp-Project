package hk.hku.cs.c7802.base;

import java.util.ArrayList;
import java.util.List;

import hk.hku.cs.c7802.base.time.TimePoint;

public class CashStream {
	
	public CashStream() {
		// TODO Auto-generated constructor stub
	}

	public interface CashStreamEvaluater {
		public CashFlow valueOf(CashFlow cf, TimePoint tp);
	}
	
	public CashFlow valueWith(CashStreamEvaluater ev) {
		CashFlow ret = CashFlow.EMPTY;
		for (CashStreamElement ele : list){
			ret.combine(ev.valueOf(ele.cash, ele.time));
		}
		return ret;
	}
	
	private class CashStreamElement {
		public CashFlow cash;
		public TimePoint time;
	}
	private List<CashStreamElement> list = new ArrayList<CashStreamElement>();
}
