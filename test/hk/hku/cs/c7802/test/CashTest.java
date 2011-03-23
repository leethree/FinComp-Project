package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import java.util.Currency;

import hk.hku.cs.c7802.base.BaseRuntimeException;
import hk.hku.cs.c7802.base.cash.CashFlow;

import org.junit.Test;

public class CashTest {

	@Test
	public void testPlus() {
		CashFlow c = CashFlow.createEmpty();
		c = c.plus(CashFlow.create(50));
		c = c.plus(c);
		assertEquals(CashFlow.create(100), c);
		c = c.plus(CashFlow.create(-100));
		assertEquals(CashFlow.create(0), c);
	}
	
	@Test
	public void testMultiply() {
		CashFlow c = CashFlow.create(50);
		c = c.multiply(2);
		assertEquals(CashFlow.create(100), c);
	}
	
	@Test(expected=BaseRuntimeException.class)
	public void testPlusEx() {
		CashFlow c = CashFlow.createForeign(Currency.getInstance("HKD"), 50);
		CashFlow d = CashFlow.createForeign(Currency.getInstance("USD"), 50);
		d.plus(c);
	}
	
}
