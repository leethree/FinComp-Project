package hk.hku.cs.c7802.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	// basic libraries
	TimeTest.class,
	ConventionTest.class,
	CashTest.class,
	InterestRateTest.class,
	InterpolatorTest.class,
	CurveTest.class,
	// instruments
	CashInstrumentTest.class,
	FRATest.class,
	SwapTest.class,
	// curve engine
	CurveEngineTest.class,
	// options
	NormalGeneratorTest.class,
	MonteCarloTest.class,
	BlackScholesTest.class,
	NewtonsMethodTest.class,
	// driver
	DriverTest.class
})
public class AllTests {

}
