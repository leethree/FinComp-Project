package hk.hku.cs.c7802.test;

import static org.junit.Assert.*;
import hk.hku.cs.c7802.driver.CSVParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;

public class DriverTest {
	private CSVParser createParser(String s, String[] header) throws IOException {
		Reader is = new StringReader(s);
		CSVParser csv = new CSVParser(is, header);
		return csv;
	}
	
	@Test
	public void stripTest() {
		assertEquals("abc", CSVParser.strip(" \t  abc    \t \n"));
	}
	
	@Test
	public void testCSVParserEmptyFile() throws IOException {
		CSVParser csv = createParser("  \n\n  ", null);
		String[] record = csv.nextRecord();
		assertArrayEquals(null, record);		
	}
	
	@Test
	public void testCSVParserToList() throws IOException {
		String[] header = new String[3];
		CSVParser csv = createParser(
				"Apple,Banana,Cat\n" + 
				"Coca,Cola\n" + "Higher,Faster,Stronger", 
				header);
		List<String[]> l = csv.toList();
		assertArrayEquals(new String[]{"Apple", "Banana", "Cat"}, header);	
		assertArrayEquals(new String[]{"Coca", "Cola"}, l.get(0));		
		assertArrayEquals(new String[]{"Higher", "Faster", "Stronger"}, l.get(1));		
		assertEquals(2, l.size());		
	}
	
	@Test
	public void testRunUsage() throws IOException, InterruptedException {
		String cmdline[] = new String[]{"java", "-cp", "bin", "hk.hku.cs.c7802.driver.Main"};
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(cmdline);
		pb.redirectErrorStream(false);
		Process p = pb.start();
		
		BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while(true) {
			String x = stdout.readLine();
			if(x == null)
				break;
			// System.out.println(x);
		}
		
		int exitStatus = p.waitFor();
		assertEquals(0, exitStatus);		
	}
	
	@Test
	public void testRunCurve() throws IOException, InterruptedException {
		String cmdline[] = new String[]{"java", "-cp", "bin", "hk.hku.cs.c7802.driver.Main",
				"-y", "-s", "curveSpec.csv", "-i", "curveDataInput.csv"};
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(cmdline);
		pb.redirectErrorStream(false);
		Process p = pb.start();
		
		BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while(true) {
			String x = stdout.readLine();
			if(x == null)
				break;
			// System.out.println(x);
		}
		
		int exitStatus = p.waitFor();
		assertEquals(0, exitStatus);		
	}
}
