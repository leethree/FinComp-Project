package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import hk.hku.cs.c7802.montecarlo.Antithetic;
import hk.hku.cs.c7802.montecarlo.NormalGenerator;

import org.junit.Before;
import org.junit.Test;


public class NormalGeneratorTest {
	NormalGenerator[] ngs;
	
	@Before
	public void setUp() throws Exception {
		ngs = new NormalGenerator[6];
		ngs[0] = new NormalGenerator.BigNumberAlgorithm();
		ngs[1] = new NormalGenerator.BoxMuller1();
		ngs[2] = new NormalGenerator.BoxMuller2();
		ngs[3] = new Antithetic(ngs[0]);
		ngs[4] = new Antithetic(ngs[1]);
		ngs[5] = new Antithetic(ngs[2]);
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
		ArrayList<Double> Es = new ArrayList<Double>();
		ArrayList<Double> Variances= new ArrayList<Double>();
		for(NormalGenerator ng: ngs) {
			for(long seed: new long[]{2}) { // , 4, 8, 16}) {
				ng.setSeed(seed);
				
				double sum = 0;
				int M = 1000;
				int N = 1000;
				double sqSum = 0;
				long start = System.currentTimeMillis();
				for(int j = 0; j < M; j++) {
					for(int i = 0; i < N; i++) {
						double r = ng.next();; 
						sum += r;
						sqSum += r * r;
					}
					if(Antithetic.class.isInstance(ng))
						((Antithetic)ng).nextPath();
				}
				long end = System.currentTimeMillis();
				N = M * N;
				double E = sum / N;
				double variance = (sqSum - 2 * sum * E + N * E * E) / (N - 1);
				
				System.err.println("Algorithm: " + ng.toString());
				System.err.println(String.format("Average = %.6f", E));
				System.err.println(String.format("Stdev = %.6f", Math.sqrt(variance)));
				System.err.println("Time used = " + (end - start) + " ms");
				System.err.println();				

				Es.add(E);
				Variances.add(variance);
				
			}
		}
		for(int i = 0; i < Es.size(); i++) {
			assertEquals(0, Es.get(i), 0.006);
			assertEquals(1, Variances.get(i), 0.01);
		}	
	}
}
