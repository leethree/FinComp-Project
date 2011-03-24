package hk.hku.cs.c7802.inst;

import hk.hku.cs.c7802.base.cash.CashFlow;
import hk.hku.cs.c7802.base.cash.CashStream.CashStreamVisitor;
import hk.hku.cs.c7802.base.time.TimePoint;

public abstract class Instrument {

	protected Instrument(InstrumentBuilder builder) {
		this.name = builder.name;
		this.timestamp = builder.timestamp;
		this.validthru = builder.validthru;
		if ((name == null) || (timestamp == null))
			throw new IllegalArgumentException("Missing metadata for Instrument.");
	}
	
	public abstract CashFlow valueWith(InstrumentEvaluator ev);
	
	public interface InstrumentEvaluator {
		
		public CashStreamVisitor getCashStreamVisitor();
		
		public CashFlow getValue();
	}
	
	public TimePoint getTimestamp() {
		return timestamp;
	}
	
	public TimePoint getValidthru() {
		return validthru;
	}
	
	@Override
	public String toString() {
		return name + "@" + timestamp.toString();
	}
	
	public static abstract class InstrumentBuilder {

		protected InstrumentBuilder() {
		}
		
		public InstrumentBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public InstrumentBuilder withTimestamp(TimePoint timestamp) {
			this.timestamp = timestamp;
			return this;
		}
		
		public InstrumentBuilder withValidthru(TimePoint validthru) {
			this.validthru = validthru;
			return this;
		}
		
		public abstract Instrument build();
		
		private String name;
		private TimePoint timestamp;
		private TimePoint validthru = null;
	}
	
	protected String name;
	protected TimePoint timestamp;
	protected TimePoint validthru;
}
