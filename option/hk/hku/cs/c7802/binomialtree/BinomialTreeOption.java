package hk.hku.cs.c7802.binomialtree;

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
}
 
