package hk.hku.cs.c7802.stock;

import java.util.Currency;
import java.util.Locale;

public abstract class Stock {

	public Stock(String name) {
		this.name = name;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	protected String name;
	protected Currency currency;
	
	public static Stock getDefault() {
		return simpStock;
	}
	
	static class SimpleStock extends Stock {
		private SimpleStock() {
			super(".HSI");
			currency = Currency.getInstance(Locale.getDefault());
		}
	}
	
	private static Stock simpStock = new SimpleStock();
}
