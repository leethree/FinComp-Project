package hk.hku.cs.c7802.curve.util;

import java.util.SortedMap;

public interface Interpolator {
	
	double interpolate(double point, SortedMap<Double, Double> datapoints);
	
}