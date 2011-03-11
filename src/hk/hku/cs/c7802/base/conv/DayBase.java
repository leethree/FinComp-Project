package hk.hku.cs.c7802.base.conv;

import hk.hku.cs.c7802.base.time.TimeDiff;

/**
 * Day count convention
 * @author leethree
 *
 */
public interface DayBase {
	
	public static final DayBase ACT365 = new Actual365();

	public double factor(TimeDiff df);
	
	static class Actual365 implements DayBase {

		@Override
		public double factor(TimeDiff df) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}
