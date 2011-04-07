package hk.hku.cs.c7802.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TimeTest.class,
	ConventionTest.class,
	CashTest.class,
	InterestRateTest.class,
	InterpolatorTest.class,
	CurveTest.class,
	CashInstrumentTest.class,
	FRATest.class,
	SwapTest.class,
	NormalGeneratorTest.class,
	MonteCarloTest.class,
})
public class AllTests {

}
