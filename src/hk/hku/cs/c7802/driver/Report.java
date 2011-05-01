package hk.hku.cs.c7802.driver;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimePointFormatException;
import hk.hku.cs.c7802.montecarlo.Antithetic;
import hk.hku.cs.c7802.montecarlo.BasicMonteCarlo;
import hk.hku.cs.c7802.montecarlo.MonteCarloOption;
import hk.hku.cs.c7802.montecarlo.NormalGenerator;
import hk.hku.cs.c7802.montecarlo.RandomGenerator;
import hk.hku.cs.c7802.montecarlo.UniformGenerator;
import hk.hku.cs.c7802.option.Option;
import hk.hku.cs.c7802.option.OptionAlpha;
import hk.hku.cs.c7802.option.OptionBeta;
import hk.hku.cs.c7802.stock.Stock;

public class Report {
	public TimePoint expiry;
	public TimePoint today;
	public double marketRateAtExpiry = 0.047759538294208204;
	public double sigma = 0.6300846926540035;
	double S0 = 89.31;
	double K = 95;
	double T;
	double r = marketRateAtExpiry;
	double call = 30;

	public void init() throws TimePointFormatException {
		expiry = Main.parseDate("2013-01-22");
		today = Main.parseDate("2011-04-05");
		T = DayBase.ACT365.factor(expiry.minus(today));
	}

	public double[] testMonteCarlo(int M, int N, boolean isAntithetic, int seed) {
		BasicMonteCarlo mc = new BasicMonteCarlo(M, N);
		RandomGenerator crg = new NormalGenerator.BoxMuller2();
		RandomGenerator rg;// new Antithetic(crg);
		if (isAntithetic) {
			rg = new Antithetic(crg);
		} else {
			rg = crg;
		}
		rg.setSeed(seed);

		double v = mc.value(rg, new MonteCarloOption.Call(K), S0, T, r, sigma);
		double error0 = mc.error(0);
		double error5 = mc.error(5);
		return new double[] { v, error0, error5 };
	}
	
	private void runMonteCarlo(int M, int N, boolean isAntithetic, int seed) { 
		double[] ret = testMonteCarlo(M, N, true, seed);
	    double v = ret[0]; double error0 = ret[1]; double error5 = ret[2];
	    System.out.println(String.format("%8d %8d %f %f %f", M, N, v, error0,  error5));
	}

	public void testMNOfMonteCarlo() {
		int MN = 1000 * 1000 * 100;
		int Ms[] = new int[] { 10, 100, 1000, 10 * 1000, 100 * 1000, 1000 * 1000, 1000 * 1000 * 10, 1000 * 1000 * 100 };

		for(int M: Ms) { 
			int N = MN / M; 
			 runMonteCarlo(M, N, true, 2);
		}
	}

	public void testNOfMonteCarlo() {
		System.out.println("# Convergence of N");
		for(int seed: new int[] {2, 8323}) {
			runMonteCarlo(100000, 6, true, seed);
			runMonteCarlo(100000, 10, true, seed);
			runMonteCarlo(100000, 20, true, seed);
			runMonteCarlo(100000, 50, true, seed);
			runMonteCarlo(100000, 100, true, seed);
			System.out.println();
		}
	}

	public void testMOfMonteCarlo() {
		System.out.println("# Variance of M");
		int seed = 2;
		runMonteCarlo(100, 100, true, seed);
		runMonteCarlo(1000, 100, true, seed);
		runMonteCarlo(10000, 100, true, seed);
		runMonteCarlo(100000, 100, true, seed);
		runMonteCarlo(1000000, 100, true, seed);
		// runMonteCarlo(10000000, 100, true, seed);
		System.out.println();

		runMonteCarlo(100, 1000, true, seed);
		runMonteCarlo(1000, 1000, true, seed);
		runMonteCarlo(10000, 1000, true, seed);
		runMonteCarlo(100000, 1000, true, seed);
		runMonteCarlo(1000000, 1000, true, seed);
		System.out.println();
	}
	
	private static double average(double[] x) {
		int n = x.length;
		double sum = 0;
		for(int i = 0; i < n; i++) {
			sum += x[i];
		}
		return sum / n;
	}
	
	private static double variance(double[] x, double avg) {
		int n = x.length;
		double sum = 0;
		
		for(int i = 0; i < n; i++) {
			sum += Math.pow(x[i] - avg, 2);
		}
		
		return sum / (n - 1);
	}
	
	public double[] getAntithetic(int N) {
		RandomGenerator rg = new UniformGenerator();
		rg.setSeed(2);
		
		double r[] = new double[N];
		double rp[] = new double[N];
		double x[] = new double[N];
		double y[] = new double[N];
		
		for(int i = 0; i < N; i++) {
			r[i] = rg.next();
		}
		
		for(int i = 0; i < N; i++) {
			if(i < N / 2) {
				rp[i] = r[i];
			}
			else {
				rp[i] = 1 - r[i - N/2];
			}
			
		}
		
		for(int i = 0; i < N; i++) {
			x[i] = 1 / (1 + r[i]);
		}
		
		for(int i = 0; i < N; i++) {
			y[i] = 1 / (1 + rp[i]);
		}
		
		double v1 = variance(x, 0.69314718);
		double v2 = variance(y, 0.69314718);
		if(N < 8) {
			System.out.println(String.format("N = %d ", N));
			System.out.print("r = ");
			for(int i = 0; i < N; i ++) {
				System.out.print(String.format("%f ", r[i]));
			}
			System.out.println();
			System.out.print("p = ");
			for(int i = 0; i < N; i ++) {
				System.out.print(String.format("%f ", rp[i]));
			}
			System.out.println();
			System.out.print("x = ");
			for(int i = 0; i < N; i ++) {
				System.out.print(String.format("%f ", x[i]));
			}
			System.out.println();
			System.out.print("y = ");			
			for(int i = 0; i < N; i ++) {
				System.out.print(String.format("%f ", y[i]));
			}
			System.out.println();
			System.out.println();
		}
		System.err.println(String.format("%d %f %f %f %f %.1f", N, average(x), v1, average(y), v2, v1 / v2));
		return new double[]{v1, v2};
	}

	public double[] getAntithetic2(int N) {
		double x[] = new double[N];
		double y[] = new double[N / 2];
		Antithetic ant = new Antithetic(new UniformGenerator());//new NormalGenerator.BoxMuller2());
		ant.setSeed(2);

		double sum1 = 0, sqsum1 = 0, sum2 = 0, sqsum2 = 0;
		for (int i = 0; i < N; i++) {
			/*x[i] = ant.next();
			x[i] *= x[i];
			*/
			x[i] = 1 / (1 + ant.next());
			
			sum1 += x[i];
			sqsum1 += x[i] * x[i];
			if (i < N / 2) {
				sum2 += x[i];
				sqsum2 += x[i] * x[i];
			}
		}
		ant.nextPath();
		for (int i = 0; i < N / 2; i++) {
//			y[i] = ant.next();
//			y[i] *= y[i];
			
			x[i] = 1 / (1 + ant.next());
			
			sum2 += y[i];
			sqsum2 += y[i] * y[i];
		}
		double avg1 = sum1 / N;
		double avg2 = sum2 / N;
		double variance1 = (sqsum1 / (N - 1) - sum1 * sum1
				/ ((double) N * (N - 1)));
		double variance2 = (sqsum2 / (N - 1) - sum2 * sum2
				/ ((double) N * (N - 1)));
		System.err.println(String.format("N = %d", N));
		System.err.println(String.format("Average: %f %f", avg1, avg2));
		System.err.println(String.format("Variance: %f %f", variance1,
				variance2));
		return new double[] { variance1, variance2 };
	}
	

	public void reportAntithetic() {
		for (int i : new int[] {
				2, 4, 8, 16, 32, 64, 128, 256,
				512, 1024, 2048, 4096, 8192, 16384,
				32768, 65536, 1048576}) {
			getAntithetic(i);
		}
	}
	
	public void runMonteCarloOptionAB(String prefix, Option option, int M, int N, boolean isAntithetic, int seed) {
		BasicMonteCarlo mc = new BasicMonteCarlo(M, N);
		// RandomGenerator crg = new CachedRandomGenerator(new NormalGenerator.BoxMuller2(), mc.numberOfRandomNeeded());
		RandomGenerator crg = new NormalGenerator.BoxMuller2();
		RandomGenerator rg;
		if(isAntithetic)
			rg = new Antithetic(crg);
		else
			rg = crg;
		rg.setSeed(seed);
				
		double v = mc.value(rg, new MonteCarloOption.Adaptor(option), S0, T, r, sigma);
		System.out.println(String.format("%s: %f %f", prefix, v, mc.error(0)));
	}
	
	public void optionABWithOAntithetic() {
		Stock stock = Stock.getDefault();
		Option optionA = (Option) OptionAlpha.create()
			.european()
			.expiringAt(expiry).dependingOn(stock)
			.withTimestamp(today)
			.withName("OptionA").build();
		Option optionB = (Option) OptionBeta.create()
			.expiringAt(expiry).dependingOn(stock)
			.withTimestamp(today)
			.withName("OptionB").build();
		
		runMonteCarloOptionAB("Option A    with Anithetic", optionA, 100000, 100, true, 2);
		runMonteCarloOptionAB("Option A without Anithetic", optionA, 100000, 100, false, 2);
		runMonteCarloOptionAB("Option B    with Anithetic", optionB, 100000, 100, true, 2);
		runMonteCarloOptionAB("Option B without Anithetic", optionB, 100000, 100, false, 2);
	}

	public static void main(String[] args) throws TimePointFormatException {
		Report report = new Report();
		report.init();
		report.reportAntithetic();
		//report.testNOfMonteCarlo();
		// report.testMOfMonteCarlo();
		// report.testMNOfMonteCarlo();
		// report.optionABWithOAntithetic();
		// report.runMonteCarlo(10000000, 100, true, 2);
	}
}
