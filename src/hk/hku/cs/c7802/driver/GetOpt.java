package hk.hku.cs.c7802.driver;

/*
 * This is a idea port of GNU getopt. 
 * It is not copied from GNU. So there's 
 * no license violation.
 * 
 */
public class GetOpt {
	protected final String format;
	protected final String[] args;
	protected int index;
	
	public GetOpt(String format, String[] args) {
		this.format = format;
		this.args = args;
		this.index = 0;
	}
	
	/*
	 * NOTE: This option is not the financial one
	 */
	public static class Option {
		private final String opt;
		private final String arg;
		
		public Option(String opt) {
			this(opt, null);
		}
		
		public Option(String opt, String arg) {
			this.opt = opt;
			this.arg = arg;
		}
		
		public boolean hasArgument() {
			return arg != null;
		}
		
		public String getOption() {
			return opt;
		}
		
		public String getArgument() {
			return arg;
		}
	}
	
	public Option nextOption() {
		if()
	}
	
	public int currentIndex() {
		return index;
	}
	
	public String[] remainingArguments() {
		String[] r = new String[args.length - index];
		for(int j = index; j < args.length; j++) {
			r[j - index] = args[j];
		}
		return r;
	}
}
