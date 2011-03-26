package hk.hku.cs.c7802.inst;

public abstract class Option {
	/*
	 * This function please return like:
	 * hk.hku.cs.c7802.inst.EuropeanOption Strike ExpiryDate
	 * It is for user to identify how to input in command line.  
	 */
	public abstract String cliArguments(); 
}
