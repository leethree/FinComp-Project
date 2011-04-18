package hk.hku.cs.c7802.model;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.curve.util.OutOfRangeException;
import hk.hku.cs.c7802.option.Option;
import hk.hku.cs.c7802.rate.ContinuousRate;
import hk.hku.cs.c7802.stock.StockMaketData;

public abstract class BaseModel {
	private final static ContinuousRate CONTINUOUS_RATE = new ContinuousRate(DayBase.ACT365);
	
	public void setCurve(YieldCurve curve) {
		this.curve = curve;
	}
	
	public void setStockData(StockMaketData data) {
		this.data = data;
	}
	
	protected double getCurrentStockPrice(Option option) {
		return data.getPrice(option.getStock());
	}
	
	protected double getVolatility(Option option) {
		return data.getVolatilities(option.getStock());
	}
	
	protected double getContinuousRate(TimePoint date) {
		double df = getDiscountFactor(date);
		return CONTINUOUS_RATE.fromDisFactor(df, date.minus(curve.getTimestamp()));
	}

	protected double getDiscountFactor(TimePoint date) {
		try {
			return curve.disFactorAt(date);	
		} catch (OutOfRangeException e) {
			throw new IllegalArgumentException("The expiry date of the option is out of the range of the yieldcurve");
		}
	}
	
	protected YieldCurve curve;
	protected StockMaketData data;
}
