package hk.hku.cs.c7802.base.cash;

import java.util.Currency;
import java.util.Locale;

/**
 * A cash flow is just a certain amount of money
 *
 */
public abstract class CashFlow {
	
	protected CashFlow() {
	}

	/**
	 * Positive -> income
	 * @return
	 */
	public abstract double getAmount();
	
	public abstract Currency getCurrency();
	
	/**
	 * 
	 * @param c
	 * @return Combined cash flow
	 */
	public abstract CashFlow plus(CashFlow c);
	
	/**
	 * Scale the cash flow according to a factor
	 * @param s
	 * @return
	 */
	public abstract CashFlow multiply(double s);
	
	public final boolean isEmpty() {
		return this == createEmpty();
	}
	
	public static void setDefaultCurrency(Currency cur) {
		defaultCur = cur;
	}
	
	public static CashFlow create(double amount) {
		return create(defaultCur, amount);
	}
	
	public static CashFlow create(Currency cur, double amount) {
		if (amount != 0)
			return new FixedCashFlow(cur, amount);
		else
			return createEmpty();
	}
	
	public static CashFlow createEmpty() {
		return EmptyCashFlow.getInstance();
	}
	
	@Override
	public abstract boolean equals(Object arg0);
	
	protected static Currency defaultCur = Currency.getInstance(Locale.getDefault());
}
