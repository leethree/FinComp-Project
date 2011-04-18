package hk.hku.cs.c7802.binomialtree;

import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.option.Option;
import hk.hku.cs.c7802.option.Option.StockPricer;

public interface BinomialTreeOption {
	public double payout(double S);
	
	public static class Call implements BinomialTreeOption{
		double K;
		public Call(double K) {
			this.K = K;
		}
		public double payout(double S) {
			if (S > K) 
				return S - K;
			else
				return 0;
		}
	}
	public static class Put implements BinomialTreeOption{
		double K;
		public Put(double K) {
			this.K = K;
		}
		public double payout(double S) {
			if (S < K) 
				return K - S;
			else
				return 0;
		}
	}
	
	public static class Adaptor implements BinomialTreeOption {
		Option o;
		public Adaptor(Option o) {
			this.o = o;
		}

		@Override
		public double payout(final double S) {
			return o.payout(new StockPricer(){

				@Override
				public double priceAt(TimePoint time) {
					return S;
				}

				@Override
				public double maxBefore(TimePoint time) {
					throw new RuntimeException("Unsupported function");
				}

				@Override
				public double minBefore(TimePoint time) {
					throw new RuntimeException("Unsupported function");
				}}).getAmount();

		}
	}
}
 
