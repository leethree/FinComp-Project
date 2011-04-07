package hk.hku.cs.c7802.montecarlo;

public interface RandomGenerator {
	public void setSeed(long seed);
	public double next();
}
