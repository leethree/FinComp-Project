package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.montecarlo.Antithetic;
import hk.hku.cs.c7802.montecarlo.BasicMonteCarlo;
import hk.hku.cs.c7802.montecarlo.MonteCarloOption;
import hk.hku.cs.c7802.montecarlo.NormalGenerator;
import hk.hku.cs.c7802.option.Option;

public class MonteCarloTest {
	@Test
	public void testMonteCarloSimulationOnStock() {
		MonteCarloOption option = new MonteCarloOption() {

			@Override
			public double payout(double Smax, double Smin, double S) {
				return S;
			}};
		double S0 = 1.0;
		double K = 2.0; // any number
		double T = 6.4;
		double r = 0.09;
		double sigma = 0.20;
		BasicMonteCarlo mc = new BasicMonteCarlo(100000, 1000);
		NormalGenerator bm = new NormalGenerator.BoxMuller2();
		NormalGenerator an_bm = new Antithetic(new NormalGenerator.BoxMuller2());
		NormalGenerator[] ngs = new NormalGenerator[]{bm, an_bm};
		bm.setSeed(1025);
		an_bm.setSeed(1025);
		ArrayList<Double> result = new ArrayList<Double>();
		for(NormalGenerator ng: ngs) {
			double v = mc.value(ng, option, S0, K, T, r, sigma);
			System.err.println(v);
			System.err.println(S0 * Math.pow(1+r, T) + " (expected)");
			System.err.println();
			result.add(v);
		}
		double expected = S0 * Math.pow(1+r, T); 
		for(double v: result) {
			assertEquals(expected, v, 0.01);
		}
	}
}
