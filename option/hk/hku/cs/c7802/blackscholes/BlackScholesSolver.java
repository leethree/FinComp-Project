package hk.hku.cs.c7802.blackscholes;

import hk.hku.cs.c7802.model.util.NewtonsMethod;

public class BlackScholesSolver {
	BlackScholesFunctions bsf;
	
	public BlackScholesSolver(double S0, double K, double T, double r) {
		bsf = new BlackScholesFunctions(S0, K, T, r);
		
	}
	
	public double volatilityFromCall(double c) {
		NewtonsMethod nm = new NewtonsMethod(bsf.new Call(c), bsf.new DerivedCall());
		return nm.solution(0.0001);
	}
	
	public double volatilityFromPut(double p) {
		NewtonsMethod nm = new NewtonsMethod(bsf.new Put(p), bsf.new DerivedPut());
		return nm.solution(0.0001);
	}
}
