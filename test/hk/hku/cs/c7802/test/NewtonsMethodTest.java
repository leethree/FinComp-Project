package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import org.junit.Test;

import hk.hku.cs.c7802.model.util.Function;
import hk.hku.cs.c7802.model.util.NewtonsMethod;

public class NewtonsMethodTest {
	@Test
	public void testByQuadratic() {
		// x^x - 4 x - 32 = 0;
		Function f = new Function() {
			@Override
			public double gety(double x) {
				return x * x - 4 * x - 32;
			}			
		};
		Function df = new Function() {
			@Override
			public double gety(double x) {
				return 2 * x - 4;
			}
		};
		
		NewtonsMethod nm = new NewtonsMethod(f, df);
		double error = 0.00001;
		double x0 = nm.solution(error);
		if(x0 > 6)
			assertEquals(8, x0, error);
		else
			assertEquals(-4, x0, error);
	}	
}
