package hk.hku.cs.c7802.montecarlo;

import java.util.Random;

public class UniformGenerator implements RandomGenerator{
	Random random = new Random();

	@Override
	public void setSeed(long seed) {
		random.setSeed(seed);
		
	}

	@Override
	public double next() {
		return random.nextDouble();
	}

	@Override
	public void nextPath() {
		
	}

}
