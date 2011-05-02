package hk.hku.cs.c7802.curve;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;
import hk.hku.cs.c7802.curve.util.Interpolator;
import hk.hku.cs.c7802.curve.util.LinearInterpolator;
import hk.hku.cs.c7802.curve.util.OutOfRangeException;
import hk.hku.cs.c7802.curve.util.ReverseSolver;
import hk.hku.cs.c7802.inst.InterestRateInstrument;
import hk.hku.cs.c7802.inst.SwapInstrument;
import hk.hku.cs.c7802.market.MarketData;
import hk.hku.cs.c7802.market.MarketDataPool;
import hk.hku.cs.c7802.rate.CompoundRate;

public class CurveEngine {

	private CurveEngine() {
	}
	
	// Disable this because this could make confusion
	/* public YieldCurve buildFrom(MarketDataPool pool) {
		return buildFrom(pool, TimePoint.now());
	} */
	
	public YieldCurve buildFrom(MarketDataPool pool, TimePoint ref) {
		// set curve configuration
		CurveConfig config = new CurveConfig();
		config.setCurveRateType(new CompoundRate(DayBase.ACT365, 4));
		config.setInterpolator(new LinearInterpolator());
		// initialize curve
		curve = new SimpleCurve(ref, config);
		// fetch market data
		data = pool.getEntries(ref);
		if (data.size() > 0) {
			swapinterpo();
			bootstrap();
		}
		// if there's still unsolved instruments
		if (data.size() > 0) {
			System.err.println(data);
			throw new RuntimeException("Not all instruments are properly solved.");
		}
		return curve;
	}

	public void clear() {
		data = null;
		curve = null;
	}
	
	private void bootstrap() {
		ReverseSolver solver = new ReverseSolver(curve);
		boolean stable = false;
		Iterator<MarketData> it = null;
		while (!stable) {
			it = data.iterator();
			stable = true;
			while (it.hasNext()) {
				MarketData entry = it.next();
				if (entry.getInst() instanceof InterestRateInstrument) {
					InterestRateInstrument inst = (InterestRateInstrument) entry.getInst();
					CashFlow value = inst.valueWith(solver);
					// if there's nothing to solve with this instrument
					if (value != null) {
						if (! entry.getPrice().equals(value))
							System.err.println("Warning: data inconsistency is detected, " + inst.getName() 
									+ " is priced " + entry.getPrice() + " but is valued " + value);
						it.remove();
					} else if (solver.solve(entry.getPrice())) {
						curve.addDataPoint(solver.getSolutionTime(), solver.getSolutionDf());
						it.remove();
						stable = false;
					}
				} else {
					it.remove();
					continue;
				}
			}
		}
	}
	
	/**
	 * TODO
	 * This is a terribly implemented function for the course project only.
	 * Rewrite this if any further extension is required.
	 */
	private void swapinterpo() {
		SortedMap<Long, Double> swappoints = new TreeMap<Long, Double>();
		for (MarketData entry : data) {
			if (entry.getInst() instanceof SwapInstrument) {
				SwapInstrument swap = (SwapInstrument) entry.getInst();
				long months = swap.getMaturity().getYear() * 12 + swap.getMaturity().getMonth();
				swappoints.put(months, swap.getRate());
			}
		}
		if (!swappoints.isEmpty()) {
			Interpolator<Long> interpo = new LinearInterpolator();
			for (long month = swappoints.firstKey(); month < swappoints.lastKey(); month = month + 3) {
				if (!swappoints.containsKey(month)) {
					try {
						double newrate = interpo.interpolate(month, swappoints);
						SwapInstrument intswap = (SwapInstrument) SwapInstrument.create()
												.withCouponInterval(new TimeSpan(0, 3, 0))
												.matruingAfter(new TimeSpan(0, (int) month, 0))
												.usingRateType(new CompoundRate(DayBase.ACT365, 4))
												.rate(newrate)
												.withTimestamp(curve.getTimestamp())
												.withName("INTERPO_Swap")
												.build();
						data.add(new MarketData(intswap, CashFlow.create(1.0)));
					} catch (OutOfRangeException e) {
						// This should not happen
					}
				}
			}
		}
	}
	
	private Collection<MarketData> data;
	private SimpleCurve curve; 
	
	public static CurveEngine getEngine() {
		return engine;
	}
	
	private static final CurveEngine engine = new CurveEngine();
}
