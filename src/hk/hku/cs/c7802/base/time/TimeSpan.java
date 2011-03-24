package hk.hku.cs.c7802.base.time;

public class TimeSpan {

	public TimeSpan() {
		this(0, 0, 0);
	}
	
	public TimeSpan(int year, int month, long day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public long getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	@Override
	public String toString() {
		String ret = "";
		ret += year != 0 ? year + "yr" : "";
		ret += month != 0 ? month + "m" : "";
		ret += day != 0 ? day + "d" : "";
		return ret;
	}
	
	public static final TimeSpan NEXTDAY = new TimeSpan(0, 0, 1);
	public static final TimeSpan LASTDAY = new TimeSpan(0, 0, -1);
	
	private int year;
	private int month;
	private long day;
}
