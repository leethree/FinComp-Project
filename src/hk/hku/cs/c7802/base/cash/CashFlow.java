package hk.hku.cs.c7802.base.cash;

import java.util.Currency;

public abstract class CashFlow {
	
	protected CashFlow() {
	}

	public abstract double getAmount();
	
	public abstract Currency getCurrency();
	
	public abstract CashFlow plus(CashFlow c);
	
	public abstract CashFlow multiply(double s);
	
	public static void setDefaultCurrency(Currency cur) {
		defaultCur = cur;
	}
	
	public static CashFlow create(double amount) {
		if (amount != 0)
			return new FixedCashFlow(defaultCur, amount);
		else
			return createEmpty();
	}
	
	public static CashFlow createForeign(Currency cur, double amount) {
		return new FixedCashFlow(cur, amount);
	}
	
	public static CashFlow createEmpty() {
		return EmptyCashFlow.getInstance();
	}
	
	@Override
	public abstract boolean equals(Object arg0);
	
	protected static Currency defaultCur = Currency.getInstance("HKD");
}
