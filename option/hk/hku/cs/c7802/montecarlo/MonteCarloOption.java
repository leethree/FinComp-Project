package hk.hku.cs.c7802.montecarlo;

import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.option.Option.StockPricer;
import hk.hku.cs.c7802.option.VanillaOption;

public interface MonteCarloOption {
	// MonteCarloOption can only be European,
	// the Smax, Smin, S here are all future value at the expiry day;
	// the return of payout is also future value of the execution day.
	public double payout(double Smax, double Smin, double S);
	
	public static class Vanilla implements MonteCarloOption{
		VanillaOption to;
		
		public Vanilla(VanillaOption traditionOption) {
			this.to = traditionOption;
		}

		@Override
		public double payout(final double Smax, final double Smin, final double S) {
			return to.payout(new StockPricer() {

				@Override
				public double priceAt(TimePoint time) {
					return S;
				}

				@Override
				public double maxBefore(TimePoint time) {
					return Smax;
				}

				@Override
				public double minBefore(TimePoint time) {
					return Smin;
				}}).getAmount();
		}
		
	}
	
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