package hk.hku.cs.c7802.stock;

import java.util.HashMap;
import java.util.Map;

public class StockMaketData {

	public StockMaketData() {
		prices = new HashMap<Stock, Double>();
		volatilities = new HashMap<Stock, Double>();
	}
	
	public void putPrice(Stock s, double price) {
		prices.put(s, price);
	}
	
	public double getPrice(Stock s) {
		return prices.get(s);
	}
	
	public void putVolatility(Stock s, double volatility) {
		volatilities.put(s, volatility);
	}
	
	public double getVolatilities(Stock s) {
		return volatilities.get(s);
	}
	
	private Map<Stock, Double> prices;
	private Map<Stock, Double> volatilities;
}
