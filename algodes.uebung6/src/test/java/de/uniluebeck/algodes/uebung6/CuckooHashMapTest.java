package de.uniluebeck.algodes.uebung6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.Test;

import de.uniluebeck.algodes.uebung6.hashes.UniversalHash;

public class CuckooHashMapTest {

	@Test
	public void testInsert() {
		
	    int limit = 20;

		
		ArrayList<Integer> eles = new ArrayList<Integer>();
		for(int i=0; i<=limit; i++) eles.add(i);
		Collections.shuffle(eles);
	    
	    
		HashMap map = new CuckooHashMap();
		
		for (Integer key : eles) {
			map.insert(key);
		}
	}

}
