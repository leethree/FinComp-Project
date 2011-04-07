package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import org.junit.Test;

import hk.hku.cs.c7802.montecarlo.Antithetic;
import hk.hku.cs.c7802.montecarlo.BasicMonteCarlo;
import hk.hku.cs.c7802.montecarlo.MonteCarloOption;
import hk.hku.cs.c7802.montecarlo.NormalGenerator;
import hk.hku.cs.c7802.montecarlo.RandomGenerator;

public class MonteCarloTest {
	@Test
	public void testMonteCarloSimulationOnStock() {
		// This is a mock option, it's payout the stock price it self!!
		MonteCarloOption option = new MonteCarloOption() {
			@Override
			public double payout(double Smax, double Smin, double S) {
				return S;
			}};
			
		// This simulation is based on the Excel demo file distributed by Dr R. Ma:
		// http://i.cs.hku.hk/~c7802/CourseMaterials/8-MonteCarlo.xls
		double S0 = 1.0;
		double K = 2.0; // any number
		double T = 360 / 365.0;
		double r = 0.04;
		double sigma = 0.23;
		BasicMonteCarlo mc = new BasicMonteCarlo(8, 12);
		RandomGenerator rg = new Antithetic(new NormalGenerator.BoxMuller2());
		rg.setSeed(2);
		double expected = S0 * Math.exp(r * T); 
		double v = mc.value(rg, option, S0, K, T, r, sigma);
		double error0 = mc.error(0);	
		double error = mc.error(5);		
		assertEquals(0, error0, 0.05);		// 90%
		assertEquals(expected, v, error);	// 99.95%
	}
	
	@Test
	public void testMonteCarloSimulationOnBenchmark() {
		// TODO this cannot be tested until the Black Scholes is finished.
	}
}
