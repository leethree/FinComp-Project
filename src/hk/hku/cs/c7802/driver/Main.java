package hk.hku.cs.c7802.driver;

import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimePointFormatException;
import hk.hku.cs.c7802.option.CallPutOption;
import hk.hku.cs.c7802.option.Option;

public class Main {
	
	public static void usage() {
		String cli = String.format("java %s", Main.class.getName());
		System.out.println(
			"Usage: \n" +
			"# Generate Swap Curve: \n" +
			cli + " -y [-s curveSpec.csv] [-i curveDataInput]\n" +
			"# Use Black Scholes or Binomial Tree or Monte-Carlo \n" +
			cli + " -bs|-bt|-mc  \n" +
			"\t -a|-e American/Europeen(default) \n" +
			"\t -S StockPrice \n" +
			"\t -E ExpiryDate \n" +
			"\t -r Risk-Free-Continuous-Compounding-Rate \n" +
			"\t -s \\sigma # Once given, we will use it to value the option\n" +
			"\t -v Value-of-the-option        # once given we will use it to value the sigma\n" +
			"\t option-class-name arg1 arg2 ...\n" +
			" # List all available options\n" +
			cli + " -l"
		);
	}
	
	public static void listAllOptions() {
		System.out.println(
			"\t call Strike\n" +
			"\t put Strike\n" +
			"\t A a b c # if S is within [a, b], payout = |S-c|; else 0\n" +
			"\t B a b c # R=Smax-Smin, then payout = 0.5R, if R>=a, else R if a>R>=b, else 0"
		);	
	}
	private static Option parseOption(String[] args, int i, String optionType) {
		if(args[i].equals("call")) {
			if(args.length - i == 2) {
				// return new Call(double(args[i+1]), European/American);				
				return null;
			}
		}
		else if(args[i].equals("put")) {
			if(args.length - i == 2) {
				// return new Put(double(args[i+1]), European/American);
				return null;
			}
		}
		else if(args[i].equals("A")) {
			if(args.length - i == 4) {
				// return new OptionA(double(args[i+1]), double(args[i+2]), double(args[i+3]), European/American);
				return null;
			}
		}
		else if(args[i].equals("B")) {
			if(args.length - i == 4) {
				// return new OptionB(double(args[i+1]), double(args[i+2]), double(args[i+3]), European/American);
				return null;
			}
		}		
		return null;
	}
	
	public static void main(String args[]) {
		if(args.length > 0) {
			if(args[0].equals("-l")) {
				listAllOptions();
			}
			else if(args[0].equals("-df")) {
				showDateFormat();
			}
			else if(args[0].equals("-y")) {
				String action = args[0];
			}
			else if(args[0].equals("-bs") || args[0].equals("-bt") 
					|| args[0].equals("-mc") ) {
				String action = args[0];
				String optionType = "European";
				Double stockPrice = null;
				TimePoint expiryDate = null;
				Double riskFreeRate = null;
				Double sigma = null;
				Double optionValue = null;
				Option option = null;
				for(int i = 1; i < args.length; i++) {
					try {
						if(args[i].equals("-a")) {
							optionType = "American";
						}
						else if(args[i].equals("-S")) {
							i++;
							stockPrice = Double.parseDouble(args[i]); 
						}
						else if(args[i].equals("-E")) {
							i++;
							expiryDate = TimePoint.parse(args[i]);
						}
						else if(args[i].equals("-r")) {
							i++;
							riskFreeRate = Double.parseDouble(args[i]);
						}
						else if(args[i].equals("-s")) {
							i++;
							sigma = Double.parseDouble(args[i]);
						}
						else if(args[i].equals("-v")) {
							i++;
							optionValue = Double.parseDouble(args[i]);
						}
						else {
							option = parseOption(args, i, optionType);
							break;
						}
					}
					catch(NumberFormatException e) {
						System.err.println(String.format("Wrong number format: '%s' '%s'", args[i-1], args[i]));
					} catch (TimePointFormatException e) {
						System.err.println(String.format("Wrong date format: '%s' '%s'", args[i-1], args[i]));
					}
				}
				if (stockPrice == null || expiryDate == null || riskFreeRate == null
						|| option == null || (sigma == null && optionValue == null)) {
					System.err.println("Wrong format!");
					if(stockPrice == null) System.err.println("Please specify: -S StockPrice!");
					if(expiryDate == null) System.err.println("Please specify: -E ExpiryDate!");
					if(riskFreeRate == null) System.err.println("Please specify: -r RiskFreeRate!");
					if(sigma == null && optionValue ==null) 
						System.err.println("Please specify: -s sigma or -v optionValue!");
					if(option == null) System.err.println("Please specify: option-name arg1 ...!");
				}
			}
			else {
				usage();
			}
		}
		else {
			usage();
		}
	}

	private static void showDateFormat() {
		// TODO Auto-generated method stub
		
	}

}
