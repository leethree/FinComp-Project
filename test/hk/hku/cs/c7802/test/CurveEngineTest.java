package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;
import hk.hku.cs.c7802.curve.CurveEngine;
import hk.hku.cs.c7802.curve.SimpleCurve;
import hk.hku.cs.c7802.curve.util.SimpleEvaluator;
import hk.hku.cs.c7802.inst.CashInstrument;
import hk.hku.cs.c7802.inst.FRAInstrument;
import hk.hku.cs.c7802.inst.SwapInstrument;
import hk.hku.cs.c7802.test.util.TestDataSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CurveEngineTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		engine = CurveEngine.getEngine();
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testBuild() throws Exception {
		SimpleCurve curve = (SimpleCurve) engine.buildFrom(data.pool, TestDataSet.getDefaultTime());
		//curve.dump(System.out);
		SimpleEvaluator seva = new SimpleEvaluator(curve);
		for (CashInstrument cash : data.cashes)
			assertEquals(1.0, cash.valueWith(seva).getAmount(), 0.0000001);
		for (FRAInstrument fra : data.fras)
			assertEquals(0.0, fra.valueWith(seva).getAmount(), 0.0000001);
		for (SwapInstrument swap : data.swaps)
			assertEquals(1.0, swap.valueWith(seva).getAmount(), 0.0000001);
	}

	private TestDataSet data = new TestDataSet();
	private static CurveEngine engine;
}
