package hk.hku.cs.c7802.base.time;

public class TimeSpan {

	public TimeSpan() {
		this(0, 0, 0);
	}
	
	protected TimeSpan(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	public static TimeSpan parse(String str) {
		TimeSpan ret = new TimeSpan();
		// TODO parse
		return ret;
	}
	
	public static final TimeSpan NEXTDAY = new TimeSpan(0, 0, 1);
	public static final TimeSpan LASTDAY = new TimeSpan(0, 0, -1);
	
	private int year;
	private int month;
	private int day;
}
