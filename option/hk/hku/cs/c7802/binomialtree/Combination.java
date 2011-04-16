package hk.hku.cs.c7802.binomialtree;

public class Combination {

	private int factorial(int n){
		if(n<=1){
			return 1;
		}
		else{
			return factorial(n-1)*n;
		}		
	}
	
	public int combin(int n,int m){
		if(m>n){
			return 0;
		}
		else{
			int ts=factorial(n);
			int t1=factorial(m);
			int t2=factorial(n-m);
			int tp=ts/(t1*t2);
			return tp;
		}
	}
}
 
