package hk.hku.cs.c7802.market;

import java.util.ArrayList;
import java.util.Collection;

public class MarketDataPool {

	public MarketDataPool() {
	}
	
	public void addEntry(MarketData entry) {
		pool.add(entry);
	}
	
	private Collection<MarketData> pool = new ArrayList<MarketData>(); 
}
