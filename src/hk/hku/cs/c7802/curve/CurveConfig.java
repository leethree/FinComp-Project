package hk.hku.cs.c7802.curve;

import hk.hku.cs.c7802.curve.util.Interpolator;
import hk.hku.cs.c7802.rate.InterestType;

public class CurveConfig {

	public CurveConfig() {
	}
	
	public Interpolator getInterpolator() {
		return interpo;
	}
	
	public InterestType getCurveRateType() {
		return curveRateType;
	}
	
	private Interpolator interpo;
	private InterestType curveRateType;
}
