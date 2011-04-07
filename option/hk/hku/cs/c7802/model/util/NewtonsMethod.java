package hk.hku.cs.c7802.model.util;

public class NewtonsMethod {
	Function f;
	Function df;
	public NewtonsMethod(Function f, Function df) {
		this.f = f;
		this.df = df;
	}

	public double solution(double error) {
		double x0 = 0.59;
		double x;		
		int count = 0;
		final int MAX = 30;
		while(true) {
			count += 1;
			System.err.println(x0);
			double y = f.gety(x0);
			double dy = df.gety(x0);
			x = x0 -  y / dy;
			if(Math.abs(x - x0) < error || count > MAX) {
				System.err.println(count);
				return x;
			}
			x0 = x;
		}
	}
}
