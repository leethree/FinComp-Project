package hk.hku.cs.c7802.montecarlo;

public class Greek {
	public double delta() {
		// v = mc(S)
		// v' = mc(S + deltaS)
		// return (v' - v) / (deltaS)
		return 0;
	}
	
	public double gamma() {
		// v0 = mc(S)
		// v+ = mc(S+deltaS)
		// v- = mc(S-deltaS)
		// delta+ = (v+ - v0) / deltaS
		// delta- = (v0 - v-) / deltaS
		// return (delta+ - delta-) / (deltaS)
		//        = (v+ + v-) / deltaS^2
		//		  = (v0 + v+) * 4 / deltaS^2
		return 0;
	}
	
	public double vega() {
		// v0 = mc(S, sigma)
		// v1 = mc(S, sigma + delta_sigma)
		// return (v1 - v0) / delta_sigma
		return 0;
	}
	
		
	public double theta() {
		// v0 = mc(S, T)
		// v1 = mc(S, T - delta_T)
		// return (v0 - v1) / delta_T
		return 0;		
	}
}
