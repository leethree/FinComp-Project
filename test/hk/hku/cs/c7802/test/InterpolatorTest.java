package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import hk.hku.cs.c7802.curve.util.Interpolator;
import hk.hku.cs.c7802.curve.util.LinearInterpolator;
import hk.hku.cs.c7802.curve.util.OutOfRangeException;

import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

public class InterpolatorTest {

	@Before
	public void setUp() throws Exception {
		data = new TreeMap<Long, Double>();
		data.put(0L, 2.0);
		data.put(5L, 15.0);
		data.put(17L, 21.0);
		data.put(10L, 28.0);
		interpo = new LinearInterpolator();
	}

	@Test
	public void testInterpolate() {
		try {
			assertEquals(22.8, interpo.interpolate(8L, data), 0.000001);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = OutOfRangeException.class)
	public void testInterpoEx() throws OutOfRangeException {
		interpo.interpolate(-5L, data);
	}
	
	private SortedMap<Long, Double> data;
	private Interpolator<Long> interpo;
}
