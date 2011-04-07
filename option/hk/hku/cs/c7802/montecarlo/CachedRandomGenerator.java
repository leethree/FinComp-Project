package hk.hku.cs.c7802.montecarlo;

public class CachedRandomGenerator implements RandomGenerator{
	long seed;
	RandomGenerator rg;
	int cacheSize;
	double[] cache;
	int head;
	int tail;
	
	public CachedRandomGenerator(RandomGenerator rg, int cacheSize) {
		this.rg = rg;
		this.seed = -1;
		this.cacheSize = cacheSize;
		this.cache = new double[cacheSize];
		this.head = 0;
		this.tail = 0;
	}
	

	@Override
	public void setSeed(long seed) {
		if(seed != this.seed) {			
			cache = new double[cacheSize];
			this.head = 0;
			this.tail = 0;
			this.seed = seed;
			this.rg.setSeed(seed);
			this.cacheAll();
		}
		else {
			this.head = 0;
		}
	}

	@Override
	public double next() {
		if(head < tail) {
			return cache[head++];
		}
		else if(tail < cache.length){
			double r = rg.next();
			cache[tail] = r;
			head++;
			tail++;
			return r;
		}
		else {
			throw new RuntimeException("the cache size is not big enough");
		}
	}
	
	public void cacheAll() {
		for(; tail < cache.length; tail++) {
			cache[tail] = rg.next();
		}
		head = 0;
	}

	@Override
	public void nextPath() {
		rg.nextPath();
	}

}
