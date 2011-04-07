package hk.hku.cs.c7802.driver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
	BufferedReader br;
	String[] header = null;
	/*
	 * given head = null, do not ignore the head
	 * given head != null, try to copy the first record to head array,
	 * this is useful when the first row indicates the field names 
	 */
	public CSVParser(String filename) throws FileNotFoundException {
		br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
	}
	public CSVParser(String filename, String[] head) throws IOException {
		this(filename);
		
		if(head != null) {
			String[] firstRecord = nextRecord();
			for(int i = 0; i < head.length && i < firstRecord.length; i ++) {
				head[i] = firstRecord[i];
			}
			this.header = firstRecord;
		}
	}
	
	/*
	 * returns null if no record
	 */
	public String[] nextRecord() throws IOException {
		String line = br.readLine();
		if(line == null)
			return null;
		String[] record = line.split(",");
		if(header != null && record.length != header.length) {
			System.err.println("Warning: header's len != record's len");
		}
		return record;			
	}
	
	public List<String[]> toList() throws IOException {
		ArrayList<String[]> ret = new ArrayList<String[]>();		
		while(true) {
			String[] record = nextRecord();
			if(record == null)
				break;
			ret.add(record);
		}
		return ret;		
	}
}
