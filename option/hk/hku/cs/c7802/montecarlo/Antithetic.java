package hk.hku.cs.c7802.montecarlo;

import java.util.ArrayList;
import java.util.Iterator;

public class Antithetic extends NormalGenerator{
	private NormalGenerator ng;
	private ArrayList<Double> cache;
	public Antithetic(NormalGenerator ng) {
		this.ng = ng;
		cache = new ArrayList<Double>();
	}
	
	@Override
	public void setSeed(long seed) {
		cache.clear();
		odd = false;
		ite = null;
		ng.setSeed(seed);
	}
	
	@Override
	public double next() {
		if(odd) {
			return - ite.next();
		}
		else {
			double ret = ng.next();
			cache.add(ret);
			return ret;
		}
	}
	
	private boolean odd = false;
	private Iterator<Double> ite;
	public void nextPath() {
		odd = !odd;
		if(odd) 
			ite = cache.iterator();
		else {
			ite = null;
			cache.clear();
		}
			
	}
}
