package hk.hku.cs.c7802.binomialtree;

public class BasicBinomialTree {
	public final int stepnum;
	Combination comb;
	
	public BasicBinomialTree(int stepnum) {
		this.stepnum=stepnum;
	}	
	
	public double value( BinomialTreeOption option, double S0, double K, double T, double rateOfYear, double sigma,boolean style) {
		double deltat=T/stepnum;	
		double u=Math.pow(Math.E, sigma*Math.sqrt(deltat));
		double d=Math.pow(Math.E, -sigma*Math.sqrt(deltat));
		double p=(Math.pow(Math.E,rateOfYear*deltat)-d)/(u-d);			
		
		if(style){
			
			comb=new Combination();

			double sum = 0;

			for(int i=0;i<(stepnum+1);i++){
				int sig=comb.combin(stepnum, i);
				double pr=Math.pow(p, stepnum-i)*Math.pow(1-p, i);
				double s=S0*Math.pow(u, stepnum-i)*Math.pow(d,i);			
				double payout=option.payout(s);
				double tp=sig*pr*payout;
				sum += tp;
			}		
		
			double temp=sum*Math.pow(Math.E, -rateOfYear*T);		
		
			return temp;
		}
		else{
		

			double [] prc=new double[stepnum+1];
			double [] tpc=new double[stepnum];
		
			for(int i=0;i<(stepnum+1);i++){
				double ts=S0*Math.pow(u, stepnum-i)*Math.pow(d,i);
				double tpayout=option.payout(ts);
				prc[i]=tpayout;			
			}
		
			for(int i=stepnum-1;i>=0;i--){
				for(int j=0;j<=i;j++){
					double tst=S0*Math.pow(u, i-j)*Math.pow(d,j);
					double tpayoutt=option.payout(tst);
					double tpp=Math.pow(Math.E,-rateOfYear*deltat)*(p*prc[j]+(1-p)*prc[j+1]);
					if(tpayoutt>tpp){
						tpc[j]=tpayoutt;
					}
					else{
						tpc[j]=tpp;
					}
				}
			
				for(int j=0;j<=i;j++){
					prc[j]=tpc[j];
				}
			}
		
			return prc[0];		
		}		
	}
}
 
