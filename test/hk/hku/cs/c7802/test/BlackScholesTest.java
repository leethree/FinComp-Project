package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import org.junit.Test;

import hk.hku.cs.c7802.blackscholes.BlackScholesFunctions;
import hk.hku.cs.c7802.blackscholes.BlackScholesPredictor;
import hk.hku.cs.c7802.blackscholes.BlackScholesSolver;
import hk.hku.cs.c7802.montecarlo.Antithetic;
import hk.hku.cs.c7802.montecarlo.BasicMonteCarlo;
import hk.hku.cs.c7802.montecarlo.CachedRandomGenerator;
import hk.hku.cs.c7802.montecarlo.MonteCarloOption;
import hk.hku.cs.c7802.montecarlo.NormalGenerator;
import hk.hku.cs.c7802.montecarlo.RandomGenerator;

public class BlackScholesTest {
	BasicMonteCarlo mc = new BasicMonteCarlo(1000, 1000);
	RandomGenerator rg = new Antithetic(new CachedRandomGenerator(new NormalGenerator.BoxMuller2(), mc.numberOfRandomNeeded()));

	public static class MonteCarloCall implements MonteCarloOption {
		double K;
		public MonteCarloCall(double K) {
			this.K = K;
		}
		@Override
		public double payout(double Smax, double Smin, double S) {
			if(S > K)
				return S - K;
			else
				return 0;
		}
	};

	
	public void testCall(double S0, double K, double T, double r, double sigma, 
			double expected, double errorBS, double errorMC) {
		
		MonteCarloOption option = new MonteCarloCall(K);
		
		BlackScholesPredictor bs = new BlackScholesPredictor(S0, K, T, r, sigma);
		double v1 = bs.call();		

		rg.setSeed(2);
		double v2 = mc.value(rg, option, S0, K, T, r, sigma);
		
		System.err.println("1E" + (int) Math.log(Math.abs(v1 * Math.exp(r * T) - v2)));
		if(expected > 0) {
			assertEquals(expected, v1 * Math.exp(r * T), errorBS);
			assertEquals(expected, v2, errorMC);			
		}
		else {
			assertEquals(v2, v1 * Math.exp(r * T), errorMC + errorBS);
		}
	}
	
	@Test
	public void testBlackScholeForCallPut() {
		double S0 = 1.0;
		final double K = 1.0;
		double T = 365 / 365.0;
		double r = 0.03;
		// double expected = S0 * Math.exp(r * T);
		double sigma = 0.23;
		
		BlackScholesPredictor bss = new BlackScholesPredictor(S0, K, T, r, sigma);
		assertEquals(0.1057418856563756,  bss.call(), 0.000001);
		assertEquals(0.07618741920488376, bss.put(), 0.000001);
	}
	
	@Test
	public void testBlackScholesAndMonteCarlo() {
		double S0 = 1.0;
		double K = 1.0;
		double T = 365 / 365.0;
		double r = 0.03;
		double expected = S0 * Math.exp(r * T);
		
		double sigma;
		
		sigma = 0.0;
		testCall(S0, K, T, r, sigma, expected - 1, 0.0001, 0.001);
		
		K = 1.03;
		
		sigma = 0.1;
		testCall(S0, K, T, r, sigma, -1, 0.0001, 0.001);

		sigma = 0.2;
		testCall(S0, K, T, r, sigma, -1, 0.0001, 0.001);

		sigma = 0.3;
		testCall(S0, K, T, r, sigma, -1, 0.0001, 0.001);		
	}
	
	@Test
	public void testBlackScholes() {
		double S0 = 1.0;
		double K = 1.0;
		double T = 365 / 365.0;
		double r = 0.03;
		
		double inputSigma = 0.23;
		double call = new BlackScholesPredictor(S0, K, T, r, inputSigma).call();
		double sigma = new BlackScholesSolver(S0, K, T, r).volatilityFromCall(call);
		assertEquals(inputSigma, sigma, 0.0001);
	}
	
	public void plotBlackScholes() {
		double S0 = 1.0;
		double K = 1.0;
		double T = 365 / 365.0;
		double r = 0.03;
		
		BlackScholesFunctions bsf = new BlackScholesFunctions(S0, K, T, r);
		for(double sigma = 0.01; sigma < 0.5; sigma += 0.01) {
			System.out.println(bsf.call(sigma));
		}
	}
	
	@Test
	public void testPorject2() {
		double S0 = 89.31;
		double K = 95;
		double T = 1.96;
		double r = 0.03;	// Unknown...
		double call = 30;
		
		double sigma = new BlackScholesSolver(S0, K, T, r).volatilityFromCall(call);
		System.err.println("Sigma = " + sigma);
		double evaluatedCall = new BlackScholesPredictor(S0, K, T, r, sigma).call();
		assertEquals(call, evaluatedCall, 0.0001);
	}
}
