package hk.hku.cs.c7802.montecarlo;

import java.util.Random;

public abstract class NormalGenerator {
	long seed;
	Random random = new Random();
	
	public void setSeed(long seed) {
		this.seed = seed;
		random.setSeed(seed);
	}
	
	/*
	 * Return a uniform random number between (0, 1]
	 */
	protected double nextUniform() {
		return 1 - random.nextDouble(); // because nextDouble returns [0, 1)
	}

	/*
	 * Return a normal random number distributed as N(0, 1)
	 */	
	public abstract double next();
	
	public static class BigNumberAlgorithm extends NormalGenerator{

		@Override
		public double next() {
			double sum = 0;
			for(int i = 0; i < 12; i++)
				sum += nextUniform();
			return sum - 6;
		}		
	}
	
	public static class BoxMuller1 extends NormalGenerator{
		boolean odd = false;
		double yy;
		
		public double next() {
			if(odd) {
				odd = ! odd;
				return yy;
			}
			odd = ! odd;
			double x = nextUniform();
			double y = nextUniform();
			
			double xx = Math.sqrt(-2 * Math.log(x)) * Math.cos(2 * Math.PI * y);
			yy = Math.sqrt(-2 * Math.log(x)) * Math.sin(2 * Math.PI * y);
			return xx;
		}	
		
	}
	
	
	public static class BoxMuller2 extends NormalGenerator{
		boolean odd = false;
		double yy;

		public double next() {
			if (odd) {
				odd = !odd;
				return yy;
			}
			odd = !odd;
			double x = 0, y = 0, r = 0;
			while (!(1 > r && r > 0)) {
				x = nextUniform() * 2 - 1; // (-1, 1]
				y = nextUniform() * 2 - 1; // (-1, 1]
				r = x * x + y * y;
			}
			double xx = x * Math.sqrt(-2 * Math.log(r) / r);
			yy = y * Math.sqrt(-2 * Math.log(r) / r);
			return xx;
		}
	}
}
