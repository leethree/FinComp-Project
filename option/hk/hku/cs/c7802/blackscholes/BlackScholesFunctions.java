package hk.hku.cs.c7802.blackscholes;

import hk.hku.cs.c7802.model.util.Function;

public class BlackScholesFunctions {
	double S0, K, T, r;
	public BlackScholesFunctions(double S0, double K, double T, double r) {
		this.S0 = S0;
		this.K = K;
		this.T = T;
		this.r = r;			
	}
	
	// TODO Dear reviewer, would you please have a check these numbers
	final static double beta  = 0.2316419;
	final static double a1 = 0.319381530;
	final static double a2 = -0.356563782;
	final static double a3 = 1.781477937;
	final static double a4 = -1.821255978;
	final static double a5 = 1.330274429;
	public static double N(double x) {
		boolean b = x < 0;
		x = Math.abs(x);
		double Nprime = dN(x); 
		double k = 1 / (1 + beta * x);
		double ret = 1 - Nprime * (a1 * k + a2 * k * k + a3 * Math.pow(k, 3) + a4 * Math.pow(k, 4) + a5 * Math.pow(k, 5));
		if(b)
			return 1 - ret;
		else
			return ret;
	}
	public static double dN(double x) {
		return Math.exp(- x * x / 2)  / Math.sqrt(2 * Math.PI);
	}
	
	public double d1Numerator(double sigma) {
		return Math.log(S0 / K) + (r + sigma * sigma / 2) * T;
	}
	
	public double dd2(double sigma) {
		return - d1Numerator(sigma) / (sigma * sigma * Math.sqrt(T));
	}
	
	public double dd1(double sigma) {
		return Math.sqrt(T) + dd2(sigma);
	}
	
	public double[] dcore(double sigma) {
		double dd2_ = dd2(sigma);
		double dd1_ = Math.sqrt(T) + dd2_;
		return new double[] {dd1_, dd2_};
	}

	public double[] core(double sigma) {
		double d1Dominator = sigma * Math.sqrt(T);
		double d1 = d1Numerator(sigma) / d1Dominator;
		double d2 = d1 - d1Dominator;
		return new double[] {d1, d2};
	}
	
	public double call(double sigma) {
		double[] d = core(sigma);		
		double c = S0 * N(d[0]) - K * Math.exp(-r * T) * N(d[1]);
		if(c > 0)
			return c;
		else
			return 0;
	}
	
	public double dcall(double sigma) {
		double[] d = core(sigma);
		double[] dd = dcore(sigma);
		return S0 * dN(d[0]) * dd[0] - K * Math.exp(-r * T) * dN(d[1]) * dd[1];
	}
	
	public double put(double sigma) {
		double[] d = core(sigma);		
		double p = K * Math.exp(-r * T) * N(-d[1]) - S0 * N(-d[0]);
		if(p > 0)
			return p;
		else
			return 0;
	}
	
	public double dput(double sigma) {
		double[] d = core(sigma);
		double[] dd = dcore(sigma);
		return K * Math.exp(-r * T) * dN(-d[1]) * dd[1] - S0 * dN(-d[0]) * dd[0];
	}
	
	
	public class Call implements Function{
		double c;
		public Call(double c) {
			this.c = c;
		}
		@Override
		public double gety(double x) {
			return call(x) - c;
		} 
	}
	
	public class DerivedCall implements Function{
		@Override
		public double gety(double x) {
			return dcall(x);
		} 
	}
	
	public class Put implements Function{
		double p;
		public Put(double p) {
			this.p = p;
		}		
		@Override
		public double gety(double x) {
			return put(x) - p;
		} 
	}
	
	public class DerivedPut implements Function{
		@Override
		public double gety(double x) {
			return dput(x);
		} 
	}
}
