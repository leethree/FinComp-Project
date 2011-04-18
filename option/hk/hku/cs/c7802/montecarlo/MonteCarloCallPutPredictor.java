package hk.hku.cs.c7802.montecarlo;

public class MonteCarloCallPutPredictor {
	int M = 1000;
	int N = 1000;
	BasicMonteCarlo bmc;
	double S0;
	double K;
	double T;
	double r;
	double sigma;
	RandomGenerator rg;
	long seed;
	
	public MonteCarloCallPutPredictor(double S0, double K, double T, double r, double sigma) {
		this.S0 = S0;
		this.K = K;
		this.T = T;
		this.r = r;
		this.sigma = sigma;
		bmc = new BasicMonteCarlo(10000, 1000);
		rg = 
			new Antithetic(
				new CachedRandomGenerator(
					new NormalGenerator.BoxMuller2(), 
					bmc.numberOfRandomNeeded()));
		seed = System.currentTimeMillis();
		rg.setSeed(seed);
	}
	
	public double call() {
		rg.setSeed(seed);
		MonteCarloOption option = new MonteCarloOption.Call(K);
		return bmc.value(rg, option, S0, T, r, sigma);
	}
	
	public double put() {
		rg.setSeed(seed);
		MonteCarloOption option = new MonteCarloOption.Put(K);
		return bmc.value(rg, option, S0, T, r, sigma);
	}
}
