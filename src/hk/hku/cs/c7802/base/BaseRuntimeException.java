package hk.hku.cs.c7802.base;

public class BaseRuntimeException extends RuntimeException {

	public BaseRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BaseRuntimeException(String arg0) {
		super(arg0);
	}

	private static final long serialVersionUID = 7205056775092769093L;

}
