package hk.hku.cs.c7802.base.cash;

import java.util.SortedMap;
import java.util.TreeMap;

import hk.hku.cs.c7802.base.time.TimePoint;

public class CashStream {
	
	public CashStream() {
		map = new TreeMap<TimePoint, CashFlow>();
	}

	public void add(CashFlow cf, TimePoint tp) {
		CashFlow old = addNonEmpty(cf, tp);
		// If key already exists
		if (old != null) {
			addNonEmpty(old.plus(cf), tp);
		}
	}
	
	public void accept(CashStreamVisitor visitor) {
		visitor.before();
		for (TimePoint tp : map.keySet()){
			visitor.visit(map.get(tp), tp);
		}
		visitor.after();
	}

	public interface CashStreamVisitor {
		
		public void before();
		
		public void visit(CashFlow cf, TimePoint tp);
		
		public void after();
	}
	
	private CashFlow addNonEmpty(CashFlow cf, TimePoint tp) {
		if (!cf.isEmpty())
			return map.put(tp, cf);
		return null;
	}
	
	private SortedMap<TimePoint, CashFlow> map;
}
