package de.uniluebeck.algodes.uebung6;

import static org.junit.Assert.*;

import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;

import de.uniluebeck.algodes.uebung6.hashes.SimpleHash;
import de.uniluebeck.algodes.uebung6.hashes.UniversalHash;

public class LinarHashMapTest {
	
	@Test
	public void testInsert() {
	    
	    int test_limit = 20;
	    Random r = new Random();
	    
		LinearHashMap map = new LinearHashMap(new UniversalHash());
		for(int i=0; i<= test_limit; i++) {
			map.insert(Math.abs(r.nextInt(10000)));
		}
	}

	@Test
	public void testDelete() {
		int[] data = new int[] {1,5,63,98,4,5,64,7,42,23};
		
		LinearHashMap map = new LinearHashMap(new SimpleHash(), 2);
		
		for (int i : data) {
            map.insert(i);
        }
		
		map.delete(5);
		map.delete(63);
		map.delete(23);
		map.delete(4);
		map.delete(1);
		map.delete(7);
	}

}
