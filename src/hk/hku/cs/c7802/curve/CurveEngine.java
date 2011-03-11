package hk.hku.cs.c7802.curve;

import hk.hku.cs.c7802.market.MarketDataPool;

public class CurveEngine {

	private CurveEngine() {
	}
	
	public YieldCurve buildFrom(MarketDataPool pool) {
		// TODO
		return null;
	}
	
	public static CurveEngine getEngine() {
		return engine;
	}
	
	private static final CurveEngine engine = new CurveEngine();
}
