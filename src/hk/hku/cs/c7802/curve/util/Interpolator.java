package hk.hku.cs.c7802.curve.util;

import java.util.SortedMap;

public interface Interpolator<K> {
	
	double interpolate(K point, SortedMap<K, Double> datapoints) throws OutOfRangeException;
	
}