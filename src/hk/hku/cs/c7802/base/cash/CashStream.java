package hk.hku.cs.c7802.base.cash;

import java.util.ArrayList;
import java.util.List;

import hk.hku.cs.c7802.base.time.TimePoint;

public class CashStream {
	
	public CashStream() {
	}

	public interface CashStreamEvaluater {
		public CashFlow valueOf(CashFlow cf, TimePoint tp);
	}
	
	public CashFlow valueWith(CashStreamEvaluater ev) {
		CashFlow ret = CashFlow.createEmpty();
		for (CashStreamElement ele : list){
			ret.plus(ev.valueOf(ele.cash, ele.time));
		}
		return ret;
	}
	
	private class CashStreamElement {
		public CashFlow cash;
		public TimePoint time;
	}
	
	private List<CashStreamElement> list = new ArrayList<CashStreamElement>();
}
