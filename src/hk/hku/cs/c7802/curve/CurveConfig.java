package hk.hku.cs.c7802.curve;

import hk.hku.cs.c7802.curve.util.Interpolator;
import hk.hku.cs.c7802.rate.InterestType;

public class CurveConfig {

	public CurveConfig() {
	}
	
	public Interpolator<Long> getInterpolator() {
		return interpo;
	}
	
	public void setInterpolator(Interpolator<Long> interpo) {
		this.interpo = interpo;
	}
	
	public InterestType getCurveRateType() {
		return curveRateType;
	}
	
	public void setCurveRateType(InterestType curveRateType) {
		this.curveRateType = curveRateType;
	}
	
	private Interpolator<Long> interpo;
	private InterestType curveRateType;
}
