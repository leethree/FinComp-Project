package hk.hku.cs.c7802.curve;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.curve.util.LinearInterpolator;
import hk.hku.cs.c7802.market.MarketDataPool;
import hk.hku.cs.c7802.rate.CompoundRate;

public class CurveEngine {

	private CurveEngine() {
	}
	
	public YieldCurve buildFrom(MarketDataPool pool) {
		return buildFrom(pool, TimePoint.now());
	}
	
	public YieldCurve buildFrom(MarketDataPool pool, TimePoint ref) {
		// TODO not yet implemented
		CurveConfig config = new CurveConfig();
		config.setCurveRateType(new CompoundRate(DayBase.ACT365, 4));
		config.setInterpolator(new LinearInterpolator());
		return null;
	}
	
	public static CurveEngine getEngine() {
		return engine;
	}
	
	private static final CurveEngine engine = new CurveEngine();
}
