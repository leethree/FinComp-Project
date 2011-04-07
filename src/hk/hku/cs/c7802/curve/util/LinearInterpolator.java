package hk.hku.cs.c7802.curve.util;

import java.util.SortedMap;

public class LinearInterpolator implements Interpolator<Long> {

	@Override
	public double interpolate(Long point, SortedMap<Long, Double> datapoints) throws OutOfRangeException{
		if(datapoints.isEmpty())
			throw new OutOfRangeException(0, 0);
		if (datapoints.firstKey() >= point || datapoints.lastKey() < point)
			throw new OutOfRangeException(datapoints.firstKey(), datapoints.lastKey());
		long key1 = datapoints.tailMap(point).firstKey();
		double value1 = datapoints.get(key1);
		long key2 = datapoints.headMap(point).lastKey();
		double value2 = datapoints.get(key2);
		return (point - key1) * (value2 - value1) / (key2 - key1) + value1;
	}

}
