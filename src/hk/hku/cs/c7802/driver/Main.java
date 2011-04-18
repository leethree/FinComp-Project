package hk.hku.cs.c7802.driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimePointFormatException;
import hk.hku.cs.c7802.base.time.TimeSpan;
import hk.hku.cs.c7802.curve.CurveConfig;
import hk.hku.cs.c7802.curve.CurveEngine;
import hk.hku.cs.c7802.curve.PerfectCurve;
import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.curve.util.LinearInterpolator;
import hk.hku.cs.c7802.curve.util.OutOfRangeException;
import hk.hku.cs.c7802.market.MarketData;
import hk.hku.cs.c7802.market.MarketDataPool;
import hk.hku.cs.c7802.model.BaseModel;
import hk.hku.cs.c7802.model.BinomialModel;
import hk.hku.cs.c7802.model.BlackScholesModel;
import hk.hku.cs.c7802.model.MonteCarloModel;
import hk.hku.cs.c7802.option.CallPutOption;
import hk.hku.cs.c7802.option.Option;
import hk.hku.cs.c7802.option.Option.OptionBuilder;
import hk.hku.cs.c7802.option.OptionAlpha;
import hk.hku.cs.c7802.option.OptionBeta;
import hk.hku.cs.c7802.option.OptionEvaluator;
import hk.hku.cs.c7802.option.ImpliedVolatilityEvaluator;
import hk.hku.cs.c7802.option.VanillaOption.VanillaOptionBuilder;
import hk.hku.cs.c7802.rate.CompoundRate;
import hk.hku.cs.c7802.stock.Stock;
import hk.hku.cs.c7802.stock.StockMaketData;

public class Main {
	
	public static final String CLI = String.format("java %s", Main.class.getName());
	public static TimePoint ref = TimePoint.now();

	public static void usage() {
		String cli = String.format("java %s", Main.class.getName());
		System.out.println("Usage:");
		YieldCurveDriver.usage();
		System.out.println(
			"# Use Black Scholes or Binomial Tree or Monte-Carlo \n" +
			cli + " -bs|-bt|-mc  \n" +
			"\t -a|-e American/Europeen(default) \n" +
			"\t -S StockPrice \n" +
			"\t -E ExpiryDate \n" +
			"\t -r Risk-Free-Continuous-Compounding-Rate \n" +
			"\t -s \\sigma # Once given, we will use it to value the option\n" +
			"\t -v Value-of-the-option        # once given we will use it to value the sigma\n" +
			"\t option-class-name arg1 arg2 ...");
		System.out.println(
			" # List all available options\n" +
			cli + " -l" +
			" # Show available data format\n" +
			cli + " -df"
		);
	}
	
	public static void listAllOptions() {
		System.out.println(
			"\t call Strike\n" +
			"\t put Strike\n" +
			// "\t A a b c # if S is within [a, b], payout = |S-c|; else 0\n" +
			// FIXME OptionAlpha should be more flexible			
			"\t A # if S is within [75, 125], payout = |S-100|; else 0\n" +
			// "\t B a b c # R=Smax-Smin, then payout = 0.5R, if R>=a, else R if a>R>=b, else 0"
			// FIXME OptionBeta should be more flexible
			"\t B # R=Smax-Smin, then payout = 0.5R, if R>=50, else R if 50>R>=20, else 0"
		);	
	}
	private static OptionBuilder parseOption(String[] args, int i, String optionType) {
		if(args[i].equals("call")) {
			if(args.length - i == 2) {
				return (OptionBuilder) CallPutOption.createCall()
					.withStrike(Double.parseDouble(args[i+1]))
					.withName(optionType + "-Call");
			}
		}
		else if(args[i].equals("put")) {
			if(args.length - i == 2) {
				return (OptionBuilder) CallPutOption.createPut()
					.withStrike(Double.parseDouble(args[i+1]))
					.withName(optionType + "-Put");			
			}
		}
		else if(args[i].equals("A")) {
			// FIXME OptionAlpha should be more flexible			
			if(args.length - i == 1) {
				return (OptionBuilder) OptionAlpha.create()
					.withName("OptionA");
			}
		}
		else if(args[i].equals("B")) {
			// FIXME OptionBeta should be more flexible
			if(args.length - i == 1) {
				return (OptionBuilder) OptionBeta.create()
					.withName("OptionB");
			}
		}		
		return null;
	}
	
	private static TimePoint parseDate(String arg) throws TimePointFormatException {
		try {
			return TimePoint.parse(arg + " 17:18:42 GMT+08:00");
		} catch (TimePointFormatException e) {
			return TimePoint.parse(arg);
		}
	
	}

	private static void handleOptionEvaluation(String args[]) {
		String action = args[0];
		assert("-bs".equals(action) || "-bt".equals(action) || "-mc".equals(action));
		BaseModel baseModel = null;
		if(action.equals("-bs")) {
			baseModel = new BlackScholesModel();
		}
		else if(action.equals("-bt")) {
			baseModel = new BinomialModel();
		}
		else if(action.equals("-mc")) {
			baseModel = new MonteCarloModel();
		}
		else {
			System.err.println("Impossible");
			return;
		}
		

		String optionType = "European";
		Double stockPrice = null;
		TimePoint expiryDate = null;
		Double riskFreeRate = null;
		Double sigma = null;
		Double optionValue = null;
		OptionBuilder optionBuilder = null;
		String name = "unnamed";
		for(int i = 1; i < args.length; i++) {
			try {
				if(args[i].equals("-a")) {
					optionType = "American";
				}
				else if(args[i].equals("-e")) {
					optionType = "European";
				}
				else if(args[i].equals("-S")) {
					i++;
					stockPrice = Double.parseDouble(args[i]); 
				}
				else if(args[i].equals("-E")) {
					i++;
					expiryDate = parseDate(args[i]);
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
					name = "";
					for(int j = i; j < args.length; j++)
						name = optionType + name + ":" + args[j];
					optionBuilder = parseOption(args, i, optionType);
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
				|| optionBuilder == null || (sigma == null && optionValue == null)) {
			System.err.println("Wrong format!");
			if(stockPrice == null) System.err.println("Please specify: -S StockPrice!");
			if(expiryDate == null) System.err.println("Please specify: -E ExpiryDate!");
			if(riskFreeRate == null) System.err.println("Please specify: -r RiskFreeRate!");
			if(sigma == null && optionValue ==null) 
				System.err.println("Please specify: -s sigma or -v optionValue!");
			if(optionBuilder == null) System.err.println("Please specify: option-name arg1 ...!");
		}
		
		if(VanillaOptionBuilder.class.isInstance(optionBuilder)) {
			if(optionType.equals("American")) {
				optionBuilder = ((VanillaOptionBuilder)optionBuilder).american();
			}
			else if(optionType.equals("European")) {
				optionBuilder = ((VanillaOptionBuilder)optionBuilder).european();
			}
			else {
				System.err.println("Warning: Unknown type of option");
			}
		}		
		
		Stock stock = Stock.getDefault();
		StockMaketData smd = new StockMaketData();
		smd.putPrice(stock, stockPrice);
		
		YieldCurve curve = new PerfectCurve(TimePoint.now(), riskFreeRate);
		baseModel.setCurve(curve);
		baseModel.setStockData(smd);
		
		optionBuilder.expiringAt(expiryDate).dependingOn(stock);
		optionBuilder.withName(name).withTimestamp(ref);
		Option option = (Option) optionBuilder.build();
		
		if(sigma != null) {
			smd.putVolatility(stock, sigma);
			OptionEvaluator optionEvaluator = (OptionEvaluator) baseModel;			
			optionValue = optionEvaluator.evaluate(option).getAmount();
			System.out.println("optionValue = " + optionValue);
		}
		else {	// volatility != null			
			assert(ImpliedVolatilityEvaluator.class.isInstance(baseModel));
			sigma = ((ImpliedVolatilityEvaluator)baseModel).implyVolatility(option, CashFlow.create(optionValue));
			System.out.println("sigma = " + sigma);
		}
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
				YieldCurveDriver.main(args);
			}
			else if(args[0].equals("-bs") || args[0].equals("-bt") 
					|| args[0].equals("-mc") ) {
				handleOptionEvaluation(args);
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
		System.out.println("2011-03-21 17:18:42 GMT+08:00");
		System.out.println("2011-03-21 17:18:42 GMT+8");
		System.out.println("2011-03-21 17:18:42 PST");
	}

}
