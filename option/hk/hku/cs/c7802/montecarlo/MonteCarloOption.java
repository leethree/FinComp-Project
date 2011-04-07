package hk.hku.cs.c7802.montecarlo;

public interface MonteCarloOption {
	// MonteCarloOption can only be European,
	// the Smax, Smin, S here are all future value at the expiry day;
	// the return of payout is also future value of the execution day.
	public double payout(double Smax, double Smin, double S);
	
	public static class Call implements MonteCarloOption{
		double K;
		public Call(double K) {
			this.K = K;
		}
		public double payout(double Smax, double Smin, double S) {
			if (S > K) 
				return S - K;
			else
				return 0;
		}
	}
	public static class Put implements MonteCarloOption{
		double K;
		public Put(double K) {
			this.K = K;
		}
		public double payout(double Smax, double Smin, double S) {
			if (S < K) 
				return K - S;
			else
				return 0;
		}
	}
}