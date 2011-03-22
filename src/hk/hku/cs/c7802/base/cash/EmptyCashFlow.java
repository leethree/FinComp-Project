package hk.hku.cs.c7802.base.cash;

import java.util.Currency;

class EmptyCashFlow extends CashFlow {

	private EmptyCashFlow() {
	}
	
	@Override
	public double getAmount() {
		return 0;
	}

	@Override
	public Currency getCurrency() {
		return null;
	}

	@Override
	public CashFlow plus(CashFlow c) {
		return c;
	}

	@Override
	public CashFlow multiply(double s) {
		return EMPTY;
	}
	
	public static CashFlow getInstance() {
		return EMPTY;
	}
	
	@Override
	public String toString() {
		return "0";
	}
	
	private static final CashFlow EMPTY = new EmptyCashFlow();

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof EmptyCashFlow)
			return true;
		else
			return false;
	}

}