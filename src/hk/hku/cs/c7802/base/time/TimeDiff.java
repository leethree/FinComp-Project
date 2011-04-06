package hk.hku.cs.c7802.base.time;

public class TimeDiff extends TimeSpan {

	public TimeDiff(long day) {
		super(0, 0, day);
	}
	
	public int getYear() {
		throw new RuntimeException("Jiong, TimeDiff is not TimeSpan");
	}

	public int getMonth() {
		throw new RuntimeException("Jiong, TimeDiff is not TimeSpan");
	}
}
