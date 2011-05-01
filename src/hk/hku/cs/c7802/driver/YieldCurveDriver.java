package hk.hku.cs.c7802.driver;

import hk.hku.cs.c7802.base.conv.DayBase;
import hk.hku.cs.c7802.base.time.TimePoint;
import hk.hku.cs.c7802.base.time.TimePointFormatException;
import hk.hku.cs.c7802.base.time.TimeSpan;
import hk.hku.cs.c7802.curve.CurveConfig;
import hk.hku.cs.c7802.curve.CurveEngine;
import hk.hku.cs.c7802.curve.YieldCurve;
import hk.hku.cs.c7802.curve.util.LinearInterpolator;
import hk.hku.cs.c7802.curve.util.OutOfRangeException;
import hk.hku.cs.c7802.market.MarketData;
import hk.hku.cs.c7802.market.MarketDataPool;
import hk.hku.cs.c7802.rate.CompoundRate;
import hk.hku.cs.c7802.rate.ContinuousRate;
import hk.hku.cs.c7802.rate.InterestType;
import hk.hku.cs.c7802.rate.SimpleRate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YieldCurveDriver {
	public static TimePoint ref = null;
	
	public static String[] getTypeRecord(List<String[]> curveSpec, String ID) {
		for(String[] r: curveSpec) {
			if(r.length > 2 && r[2] != null && r[2].equals(ID)) {
				return r;
			}
		}
		return null;
	}
	
	public static YieldCurve yieldCurve(String curveSpecFilename, String curveDataInputFilename) {
		List<String[]> curveData;
		List<String[]> curveSpec;
		try {
			String[] curveSpecHead = new String[3];
			String[] curveDataHead = new String[2];
			curveSpec = new CSVParser(curveSpecFilename, curveSpecHead).toList();
			curveData = new CSVParser(curveDataInputFilename, curveDataHead).toList();
			if(!(curveSpecHead[0].equals("InstrumentType") &&
				curveSpecHead[1].equals("subType") &&
				curveSpecHead[2].equals("ID") &&
				curveDataHead[0].equals("ID") &&
				curveDataHead[1].equals("Rate"))) {
				System.err.println("Warning: the scheme of the curveSpec/curveData csv is not standard");
			}
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		CurveConfig config = new CurveConfig();
		config.setCurveRateType(new CompoundRate(DayBase.ACT365, 4));
		config.setInterpolator(new LinearInterpolator());
		// s.addDataPoint(new TimeDiff(50), 0.92);
		// s.addDataPoint(new TimeDiff(1), 0.999);
		
		MarketDataPool pool = new MarketDataPool();
		for(String[] record: curveData) {
			String id = record[0];	// ID
			double rate = Double.parseDouble(record[1]);
			String[] typeRecord = getTypeRecord(curveSpec, id);
			if(typeRecord != null) {
				// Put the record into the market data pool
				String type = typeRecord[0];
				String subType = typeRecord[1];
				
				MarketData marketData = parseFixRateInstrument(type, subType, rate);				
				// System.err.println(String.format("DEBUG: %s %s %f %s", typeRecord[0], typeRecord[1], rate, marketData.toString()));
				pool.addEntry(marketData);
			}
			else {
				System.err.println("Error at record, invalid ID");
			}			
		}	
		
		System.err.println("ref = " + ref);
		YieldCurve curve = CurveEngine.getEngine().buildFrom(pool, ref);
		return curve;
	}
	
	private static void drawCurve(YieldCurve curve) {
		drawCurvePlainText(curve);
	}
	
	private static void drawCurvePlainText(YieldCurve curve) {
		if(curve == null)
			throw new NullPointerException();

		TimePoint time = ref;
		TimeSpan NEXTWEEK = new TimeSpan(0, 0, 7);
		ContinuousRate cr = new ContinuousRate(DayBase.ACT365);
		while(true) {
			double df = 0;
			try {
				df = curve.disFactorAt(time);
			} catch (OutOfRangeException e) {
				break;
			}
			System.out.println(time + "," + cr.fromDisFactor(df, time.minus(ref)));
			time = time.plus(NEXTWEEK);
		}
	}

	private static FixRateParser FIX_RATE_PARSER;
	
	private static MarketData parseFixRateInstrument(String type,
			String subType, double rate) {
		return FIX_RATE_PARSER.parse(type, subType, rate);
	}

	public static void main(String args[]) throws TimePointFormatException {
		String action = args[0];
		assert("-y".equals(action));
		
		String curveSpec = null;
		String curveData = null;
		List<TimePoint> dates = new ArrayList<TimePoint>();
		for(int i = 1; i < args.length; i++) {
			if(args[i].equals("-t")) {
				ref = Main.parseDate(args[i+1]);
				i++;
			}
			else if(args[i].equals("-s")) {	// e.g. -s curveSpec.csv
				curveSpec = args[i+1];
				i++;
			}
			else if(args[i].equals("-i")) { // e.g. -s curveDataInput.csv
				curveData = args[i+1];
				i++;
			}
			else {
				try {
					TimePoint date = TimePoint.parse(args[i] +  " 17:18:42 GMT+08:00");
					dates.add(date);
				} catch (TimePointFormatException e) {
					System.err.println("Date format invalid: " + args[i]);
					usage();
					return;
				}
			}
		}
		
		if(ref == null) {
			ref = TimePoint.now();
		}
		FIX_RATE_PARSER = new FixRateParser(ref);
		
		if(curveSpec != null && curveData != null) {
			YieldCurve curve = yieldCurve(curveSpec, curveData);
			if(curve != null) {
				if(dates.size() == 0)  {
					drawCurve(curve);			
				}				
				else {	
					InterestType rate = new SimpleRate(DayBase.ACT365);
					for(TimePoint date: dates) {
						
						try {
							double df = curve.disFactorAt(date);
							double r = rate.fromDisFactor(df, date.minus(ref));
							System.out.println(r);
						} catch (OutOfRangeException e) {
							System.out.println("OutOfRange");
						}
					}
				}
			}
		}
		else {
			usage();
		}
	}
	
	public static void usage() {
		System.out.println("# Generate Swap Curve: \n" +
				Main.CLI + " -y [-t ReferenceDate] [-s curveSpec.csv] [-i curveDataInput] [DATES_TO_QUERY ...] \n");
	}
}
