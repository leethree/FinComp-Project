package hk.hku.cs.c7802.montecarlo;

public class BasicMonteCarlo {
	public final int M;
	public final int N;
	public BasicMonteCarlo(int M, int N) {
		this.M = M;
		this.N = N;
	}

	public double value(RandomGenerator ng, MonteCarloOption option, double S0, double K, double T, double rateOfYear, double sigma) {
		double deltaT = T / N;
		// rate * DeltaT
		double rateDeltaT = rateOfYear * deltaT;
		// sigma * sqrt(DeltaT)
		double sigmaRootDeltaT = sigma * Math.sqrt(deltaT);
		double sum = 0;
		double sumSq = 0;
		
		for(int i = 0; i < M; i++) {
			double S = S0;
			double Smax = S;
			double Smin = S;
			for(int j = 0; j < N; j++) {
				double rand = ng.next();
				double deltaSOverS = rateDeltaT + rand * sigmaRootDeltaT;				
				S *= (1 + deltaSOverS);				
				if(S > Smax)
					Smax = S;
				if(S < Smin)
					Smin = S;
			}
			double payout = option.payout(Smax, Smin, S);
			sum += payout;
			sumSq += payout * payout;

			if(Antithetic.class.isInstance(ng))
				((Antithetic)ng).nextPath();
		}
		
		this.lastSum = sum;
		this.lastSumSq = sumSq;
		
		return sum / M;
	}
	
	private double lastSum;
	private double lastSumSq;
	private double[] I = new double[]{0.1, 0.05, 0.025, 0.01, 0.005, 0.0005};
	private double[] Z = new double[]{1.282, 1.645, 1.96, 2.326, 2.576, 3.291};
	
	/*
	 * confidentLevel must locate within [0, I.length).
	 * Or else I will move it into the range.
	 */
	public double error(int confidentLevel) {
		if(confidentLevel < 0)
			confidentLevel = 0;
		else if(confidentLevel >= I.length)
			confidentLevel = I.length - 1;
		return estimateError(lastSum, lastSumSq, confidentLevel);
	}

	private double estimateError(double sum, double sumSq, int level) {
		double miu = sum / M;
		double omigaSq = sumSq / M - miu * miu;
		double omiga = Math.sqrt(omigaSq);

//		// Debug
//		System.err.println("V = " + omigaSq );
//		int i = 0;
//		for(double zi:Z) {
//			System.err.println(String.format("%.2f%% conf: %.6f < v < %.6f",
//				100.0 * (1 - I[i]),
//				miu - zi * omiga / Math.sqrt(M),
//				miu + zi * omiga / Math.sqrt(M)));
//			i++;
//		}
		return Z[level] * omiga / Math.sqrt(M);
	}
}
