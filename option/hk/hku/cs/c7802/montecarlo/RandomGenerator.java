package hk.hku.cs.c7802.montecarlo;

public interface RandomGenerator {
	public void setSeed(long seed);
	
	public double next();
	
	// For some generator like Antithetic, this is meaningful
	// For others, just do nothing. 
	public void nextPath(); 
}
