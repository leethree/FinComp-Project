package hk.hku.cs.c7802.curve;

import hk.hku.cs.c7802.base.time.TimeDiff;
import hk.hku.cs.c7802.curve.util.Interpolator;
import hk.hku.cs.c7802.rate.InterestRate;

public class CurveConfig {

	public CurveConfig() {
	}
	
	public Interpolator getInterpolator() {
		return interpo;
	}
	
	public CurveRateFactory getRateFac() {
		return rateFac;
	}
	
	private Interpolator interpo;
	private CurveRateFactory rateFac;
	
	public interface CurveRateFactory {
		
		public InterestRate fromDisFactor(double df, TimeDiff diff);
	}
}
