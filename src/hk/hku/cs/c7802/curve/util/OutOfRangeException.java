package hk.hku.cs.c7802.curve.util;

public class OutOfRangeException extends Exception {

	public OutOfRangeException(long min, long max) {
		super("Interpolation failed, the point is out of range. Current data range is " + min + " to " + max);
	}
	
	private static final long serialVersionUID = 558865218134807170L;

}
