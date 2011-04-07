package hk.hku.cs.c7802.base.time;

public class TimeSpan{

	public TimeSpan() {
		this(0, 0, 0);
	}
	
	public TimeSpan(int year, int month, long day) {
		this.year = year;
		this.year += month / 12;
		this.month = month % 12;
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
	
	public TimeSpan plus(TimeSpan other) {
		return new TimeSpan(this.year + other.year, this.month + other.month, this.day + other.day);
	}
	
	@Override
	public String toString() {
		String ret = "";
		ret += year != 0 ? year + "y" : "";
		ret += month != 0 ? month + "m" : "";
		ret += day != 0 ? day + "d" : "";
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeSpan other = (TimeSpan) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	public static final TimeSpan NEXTDAY = new TimeSpan(0, 0, 1);
	public static final TimeSpan LASTDAY = new TimeSpan(0, 0, -1);
	
	private int year;
	private int month;
	private long day;
}
