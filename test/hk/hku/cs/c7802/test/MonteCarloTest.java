package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import org.junit.Test;

import hk.hku.cs.c7802.montecarlo.Antithetic;
import hk.hku.cs.c7802.montecarlo.BasicMonteCarlo;
import hk.hku.cs.c7802.montecarlo.CachedRandomGenerator;
import hk.hku.cs.c7802.montecarlo.MonteCarloCallPutPredictor;
import hk.hku.cs.c7802.montecarlo.MonteCarloOption;
import hk.hku.cs.c7802.montecarlo.NormalGenerator;
import hk.hku.cs.c7802.montecarlo.RandomGenerator;

public class MonteCarloTest {
	double S0 = 1.0;
	double K = 2.0; // any number
	double T = 360 / 365.0;
	double r = 0.04;
	double sigma = 0.23;
	double expected = S0 * Math.exp(r * T);
	
	// This is a mock option, it's payout the stock price it self!!
	MonteCarloOption mockOption = new MonteCarloOption() {
		@Override
		public double payout(double Smax, double Smin, double S) {
			return S;
		}};
	
	@Test
	public void testMonteCarloSimulationOnStock() {			
		// This simulation is based on the Excel demo file distributed by Dr R. Ma:
		// http://i.cs.hku.hk/~c7802/CourseMaterials/8-MonteCarlo.xls
			
		BasicMonteCarlo mc = new BasicMonteCarlo(8, 12);
		CachedRandomGenerator crg = new CachedRandomGenerator(new NormalGenerator.BoxMuller2(), mc.numberOfRandomNeeded());
		RandomGenerator rg = new Antithetic(crg);
		rg.setSeed(2);
		double v = mc.value(rg, mockOption, S0, K, T, r, sigma);
		double error0 = mc.error(0);
		double error = mc.error(5);
		assertEquals(0, error0, 0.05);		// 90%
		assertEquals(expected, v, error);	// 99.95%
	}
	
	@Test
	public void testMonteCarloPerformance() {
		int config[][] = new int[3][2];		// 1000x1000, 10000x1000, 10000x10000
		long performance[][] = new long[3][3];	// random_config0, random_config1, random_config2, 
												// non_random_config0, non_random_config1, non_random_config2,
		RandomGenerator rgs[] = new RandomGenerator[3];
		config[0][0] = 500;
		config[0][1] = 500;
		config[1][0] = 1000;
		config[1][1] = 1000;
		config[2][0] = 2000;
		config[2][1] = 2000;
		
		rgs[0] = new Antithetic(new NormalGenerator.BoxMuller2());
		rgs[0].setSeed(2);
		
		rgs[1] = new RandomGenerator() {
			@Override public void setSeed(long seed) { }

			@Override public double next() { return 0; }

			@Override public void nextPath() {	} 
		};
		
		CachedRandomGenerator crg = new CachedRandomGenerator(new NormalGenerator.BoxMuller2(), config[2][0] * config[2][1] / 2); 
		rgs[2] = new Antithetic(crg);
		rgs[2].setSeed(2);
		crg.cacheAll();
		
		double P[] = new double[3];
		for(int k = 0; k < 3; k++) {			
			for(int i = 0; i < 3; i++) {
				BasicMonteCarlo mc = new BasicMonteCarlo(config[i][0], config[i][1]);				
				rgs[k].setSeed(2);
				long start = System.currentTimeMillis();
				double v = mc.value(rgs[k], mockOption, S0, K, T, r, sigma);
				long end = System.currentTimeMillis();
				
				performance[k][i] = end - start;				
			}
			// 3 equations: for each i: 
			//     N[i] * M[i] * x + y = performance[i]
			// To solve:
			//     x = (P_{i+1} - P_i )/ (NM_{i+1} - NM_{i})
			// 
			double x[] = new double[2];
			for(int i = 0; i< 2; i ++) {
				x[i] = ((double)performance[k][i+1] - performance[k][i]) 
					/ (config[i+1][0] * config[i+1][1] - config[i][0] * config[i][1]);
				x[i] *= 1000000; // now x[i] is unit in ns
			}
			P[k] = (x[0] + x[1]) / 2;
		}
		assertTrue(P[2] < P[0]);		// cached is better than non-cached
		assertTrue(P[1] < P[2]);		// mock is of course better than cached
	}
	
	@Test
	public void testMonteCarloSimulationOnBenchmark() {
		double S0 = 89.31;
		double K = 95;
		double T = 1.96;
		double r = 0.03;
		double call = 30;
		
		double sigma = 0.622320802564423;

		BasicMonteCarlo mc = new BasicMonteCarlo(2000, 1000);
		CachedRandomGenerator crg = new CachedRandomGenerator(new NormalGenerator.BoxMuller2(), mc.numberOfRandomNeeded());
		RandomGenerator rg = new Antithetic(crg);
		rg.setSeed(2);
		double v = mc.value(rg, new MonteCarloOption.Call(K), S0, K, T, r, sigma);
		double error0 = mc.error(0);
		double error = mc.error(5);
		
		assertEquals(0, error0 / call, 0.09);
		assertEquals(0, error / call, 0.22);
		assertEquals(call, v, error);
	}
}
