package hk.hku.cs.c7802.market;

import hk.hku.cs.c7802.base.time.TimePoint;

import java.util.ArrayList;
import java.util.Collection;

public class MarketDataPool {

	public MarketDataPool() {
		pool = new ArrayList<MarketData>();
	}
	
	public void addEntry(MarketData entry) {
		pool.add(entry);
	}
	
	/**
	 * 
	 * @param time Reference time for curve construction
	 * @return Valid market data entries at the reference time
	 */
	public Collection<MarketData> getEntries(TimePoint ref) {
		Collection<MarketData> ret = new ArrayList<MarketData>();
		for (MarketData entry : pool) {
			TimePoint tp = entry.getTimestamp();
			// if the market data is collected before ref time
			if (ref.minus(tp).getDay() >= 0) {
				// and is still valid
				if (entry.getInst().getValidthru() == null || entry.getInst().getValidthru().compareTo(ref) > 0)
					ret.add(entry);
			} else
				continue;
		}
		return ret;
	}
	
	private Collection<MarketData> pool; 
	
	public void debug() {
		for(MarketData entry: pool) {
			System.err.println(entry);
		}
	}
}
