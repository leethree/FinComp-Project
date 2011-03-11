package hk.hku.cs.c7802.base;

import java.util.Currency;

public abstract class CashFlow {
	
	public enum Type {FIXED, VARYING, UNDEFINED};
	
	protected CashFlow() {
	}

	public abstract double getAmount();
	
	public abstract Currency getCurrency();
	
	public abstract Type getType();
	
	public CashFlow combine(CashFlow c) {
		if (this == EMPTY)
			return c;
		// only fixed cash flow could be combined
		if (getType() == Type.FIXED && c.getType() == Type.FIXED) {
			if (getCurrency() == c.getCurrency())
				return create(getAmount() + c.getAmount());
			// TODO error
			return null;
		}
		else {
			// TODO error
			return null;
		}
	}
	
	public CashFlow scale(double s) {
		if (this == EMPTY)
			return this;
		else
			return create(getAmount() * s);
	}
	
	public static void setDefaultCurrency(Currency cur) {
		defaultCur = cur;
	}
	
	public static CashFlow create(double amount) {
		return new SimpleCashFlow(defaultCur, amount);
	}
	
	public static CashFlow createForeign(Currency cur, double amount) {
		return new SimpleCashFlow(cur, amount);
	}
	
	public static final CashFlow EMPTY = new EmptyCashFlow();
	
	static class SimpleCashFlow extends CashFlow{
	
		public SimpleCashFlow(Currency cur, double amount) {
			this.cur = cur;
			this.amount = amount; 
		}
		@Override
		public double getAmount() {
			return amount;
		}
		@Override
		public Currency getCurrency() {
			return cur;
		}
		
		@Override
		public Type getType() {
			return Type.FIXED;
		}

		private Currency cur;
		private double amount;
	}
	
	static class EmptyCashFlow extends CashFlow {

		@Override
		public double getAmount() {
			return 0;
		}

		@Override
		public Currency getCurrency() {
			return null;
		}

		@Override
		public Type getType() {
			return Type.UNDEFINED;
		}
		
	}
	
	protected static Currency defaultCur = Currency.getInstance("HKD");
}
