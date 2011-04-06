package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;
import hk.hku.cs.c7802.montecarlo.NormalGenerator;

import org.junit.Before;
import org.junit.Test;


public class NormalGeneratorTest {
	NormalGenerator[] ngs;
	
	@Before
	public void setUp() throws Exception {
		ngs = new NormalGenerator[3];
		ngs[0] = new NormalGenerator.BigNumberAlgorithm();
		ngs[1] = new NormalGenerator.BoxMuller1();
		ngs[2] = new NormalGenerator.BoxMuller2();
	}
	
	@Test
	public void testSetSeed() {
		for(NormalGenerator ng: ngs) {
			ng.setSeed(1024);
			double x = ng.next();
			double y = ng.next();
			ng.setSeed(1024);
			double xx = ng.next();
			double yy = ng.next();
			assertEquals(x, xx, 1e-8);
			assertEquals(y, yy, 1e-8);
		}
	}
	
	@Test
	public void testNormalDistribution() {
		for(NormalGenerator ng: ngs) {
			for(long seed: new long[]{2, 4, 8, 16}) {
				ng.setSeed(seed);
				
				double sum = 0;
				int N = 100000;
				double sqSum = 0;
				long start = System.currentTimeMillis();
				for(int i = 0; i < N; i++) {
					double r = ng.next();; 
					sum += r;
					sqSum += r * r;
				}
				long end = System.currentTimeMillis();
				double E = sum / N;
				double variance = (sqSum - 2 * sum * E + E * E) / (N - 1);
				/*
				System.err.println("Algorithm: " + ng.toString());
				System.err.println(String.format("Average = %.6f", E));
				System.err.println(String.format("Stdev = %.6f", Math.sqrt(variance)));
				System.err.println("Time used = " + (end - start) + " ms");
				System.err.println();
				*/
				
				assertEquals(0, E, 0.006);
				assertEquals(1, variance, 0.01);
			}
		}
	}
}
