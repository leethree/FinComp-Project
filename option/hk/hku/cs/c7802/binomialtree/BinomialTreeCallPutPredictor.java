package hk.hku.cs.c7802.binomialtree;

public class BinomialTreeCallPutPredictor {
	int stepnum=1000;
	BasicBinomialTree bbt;
	double S0;
	double K;
	double T;
	double r;
	double sigma;
	boolean style;
	
	public BinomialTreeCallPutPredictor(double S0, double K, double T, double r, double sigma,boolean style) {
		this.S0 = S0;
		this.K = K;
		this.T = T;
		this.r = r;
		this.sigma = sigma;
		this.style=style;
		bbt = new BasicBinomialTree(stepnum);
	}
	
	public double call() {
		BinomialTreeOption option = new BinomialTreeOption.Call(K);
		return bbt.value(option, S0, K, T, r, sigma,style);
	}
	
	public double put() {
		BinomialTreeOption option = new BinomialTreeOption.Put(K);
		return bbt.value( option, S0, K, T, r, sigma,style);
	}
}
 
