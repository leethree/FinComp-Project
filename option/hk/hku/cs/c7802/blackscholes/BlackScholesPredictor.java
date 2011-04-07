package hk.hku.cs.c7802.blackscholes;

public class BlackScholesPredictor {
	BlackScholesFunctions bsf;
	double sigma;
	
	public BlackScholesPredictor(double S, double K, double T, double r, double sigma) {
		bsf = new BlackScholesFunctions(S, K, T, r);
		this.sigma = sigma;
	}
	
	public double call() {
		return bsf.call(sigma);
	}
	
	public double put() {
		return bsf.put(sigma);
	}
}
