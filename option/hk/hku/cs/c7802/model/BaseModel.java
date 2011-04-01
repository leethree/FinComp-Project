package hk.hku.cs.c7802.model;

import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.option.Option;
import hk.hku.cs.c7802.stock.StockMaketData;

public abstract class BaseModel {

	
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
	
	protected YieldCurve curve;
	protected StockMaketData data;
}
