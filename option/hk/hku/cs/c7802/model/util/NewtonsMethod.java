package hk.hku.cs.c7802.model.util;

public class NewtonsMethod {
	Function f;
	Function df; 	// the derivative of the f
	
	public NewtonsMethod(Function f, Function df) {
		this.f = f;
		this.df = df;
	}

	public double solution(double error) {
		// FIXME though enough handling CALL(sigma)/PUT(sigma),
		//		 this x0 can not ensure there would be a solution. 
		double x0 = 0.59;
		double x;		
		int count = 0;
		final int MAX = 30;
		while(true) {
			count += 1;
			double y = f.gety(x0);
			double dy = df.gety(x0);
			x = x0 -  y / dy;
			if(Math.abs(x - x0) < error || count > MAX) {
				// FIXME what should I do when count > MAX?
				return x;
			}
			x0 = x;
		}
	}
}
