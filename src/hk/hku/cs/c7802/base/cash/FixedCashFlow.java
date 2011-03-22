package hk.hku.cs.c7802.base.cash;

import hk.hku.cs.c7802.base.BaseRuntimeException;

import java.util.Currency;

class FixedCashFlow extends CashFlow{

	public FixedCashFlow(Currency cur, double amount) {
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
	public CashFlow plus(CashFlow c) {
		if (c instanceof FixedCashFlow) {
			if (getCurrency() == c.getCurrency())
				return create(amount + c.getAmount());
			throw new BaseRuntimeException("Incompatible currency type: " + c.getCurrency().getCurrencyCode());
		} else if (c instanceof EmptyCashFlow)
			return this;
		throw new BaseRuntimeException("Unsupported CashFlow type.");
	}
	
	@Override
	public CashFlow multiply(double s) {
		return create(amount * s);
	}
	
	@Override
	public String toString() {
		return cur.getSymbol() + amount;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FixedCashFlow other = (FixedCashFlow) obj;
		if (Double.doubleToLongBits(amount) != Double
				.doubleToLongBits(other.amount))
			return false;
		if (cur == null) {
			if (other.cur != null)
				return false;
		} else if (!cur.equals(other.cur))
			return false;
		return true;
	}

	private Currency cur;
	private double amount;
}