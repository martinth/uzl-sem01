package de.uniluebeck.algodes.uebung6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import com.google.common.base.Splitter;

import de.uniluebeck.algodes.uebung6.hashes.Hashfunction;
import de.uniluebeck.algodes.uebung6.hashes.SimpleHash;
import de.uniluebeck.algodes.uebung6.hashes.UniversalHash;

public class App {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		
		
		if(args.length < 2) {
			printError("At least two parameters are required.");
		}
		String hfuncStr = args[0];
		String mapStr = args[1];
		
		Hashfunction hashf = null;
		if(hfuncStr.equals("simple")) {
			hashf = new SimpleHash();
		} else if (hfuncStr.equals("universal")) {
			hashf = new UniversalHash();
		} else {
			printError("hashfunction must be 'simple' or 'universal'");
		}
		
		HashMap map = null;
		if(mapStr.equals("linear")) {
			map = new LinearHashMap(hashf);
		} else if(mapStr.equals("cuckoo")) {
			map = new CuckooHashMap();
		} else {
			printError("hashfunction must be 'linear' or 'cuckoo'");
		}
		
		Iterable<String> data = null;
		if(args.length == 2) {
			InputStreamReader inp = new InputStreamReader(System.in);
		    BufferedReader br = new BufferedReader(inp);
		    StringBuffer buf = new StringBuffer();
		    
		    String read = null;
		    while((read = br.readLine()) != null) {
		    	buf.append(read);
		    }
		    
		    data = Splitter.on(" ").omitEmptyStrings().split(buf.toString());
		} else {
			data = Arrays.asList(Arrays.copyOfRange(args, 2, args.length));
		}
		
		for (String string : data) {
			map.insert(Integer.parseInt(string));
		}

	}
	
	private static void printError(String msg) {
		String usage = "\nUsage: file.jar <hashfunction> <map type> [data]\n" +
				"\n" +
				"hashfunction   either 'simple' or 'universal'\n" +
				"               (for map type 'cuckoo' univeral is enforced)\n" +
				"map type       either 'linear' or 'cuckoo'\n" +
				"data           a list of integers that should be inserted in\n" +
				"               the hashmap. if data is empty, stdin will be read\n";
		
		System.err.println("At least two parameters are required.");
		System.err.println(usage);
		System.exit(1);

	}

}
