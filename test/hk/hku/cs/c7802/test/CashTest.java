package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import java.util.Currency;

import hk.hku.cs.c7802.base.BaseRuntimeException;
import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.cash.CashStream;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimeSpan;

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
		assertTrue(c.isEmpty());
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

	@Test
	public void testCashStream() {
		CashStream stream = new CashStream();
		final TimePoint now = TimePoint.now();
		final TimePoint later = now.plus(new TimeSpan(0, 1, 0));
		stream.add(CashFlow.create(10), now);
		stream.add(CashFlow.create(15), later);
		stream.add(CashFlow.create(-20), now);
		stream.accept(new CashStream.CashStreamVisitor() {
			
			@Override
			public void visit(CashFlow cf, TimePoint tp) {
				if (tp.equals(now))
					assertEquals(CashFlow.create(-10), cf);
				else if (tp.equals(later))
					assertEquals(CashFlow.create(15), cf);
				else
					fail();
			}
			
			@Override
			public void before() {
			}
			
			@Override
			public void after() {
			}
		});
	}
	
}
