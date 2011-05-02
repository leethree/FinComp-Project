package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;

import org.junit.Test;

import hk.hku.cs.c7802.binomialtree.BasicBinomialTree;
import hk.hku.cs.c7802.binomialtree.BinomialTreeOption;

public class BinomialTreeTest {
	double S0 = 89.31;
	double K = 95;
	double T = 1.96;
	double r = 0.03;
	double sigma = 0.622320802564423;
	double expected = S0 * Math.exp(r * T);
	
	// This is a mock option, it's payout the stock price it self!!
	BinomialTreeOption mockOption = new BinomialTreeOption() {
		@Override
		public double payout(double S) {
			//return S;
			if(S<K){
				return (K-S);
			}
			else{
				return 0;
			}
		}};
	
	@Test
	public void testBinomialTreeSimulationOnStock() {			
		// This simulation is based on the Excel demo file distributed by Dr R. Ma:
		// http://i.cs.hku.hk/~c7802/CourseMaterials/8-MonteCarlo.xls
			
		//BasicBinomialTree mc = new BasicBinomialTree(1000);
		//double v=mc.value(mockOption, S0, K, T, r, sigma, true);
	}
	
	@Test
	public void testBinomialTreePerformance() {  //option style
//		boolean t=isEuropean;
		
//		if(t)
//		{
//			double v=mc.value(mockOption,S0,T,r,sigma,style);
//		}
	
	}
	
	@Test
	public void testBinomialTreeSimulationOnBenchmark() {
		double S0 = 89.31;
		// double K = 95;
		double T = 1.96;
		double r = 0.03;
		double call = 30;
		boolean style=true;
		
		double sigma = 0.622320802564423;

		BasicBinomialTree mc = new BasicBinomialTree(10);
		
		double v=mc.value(mockOption, S0, T, r, sigma, style);
		
		assertEquals(call, v, 0.072);
	}
}
