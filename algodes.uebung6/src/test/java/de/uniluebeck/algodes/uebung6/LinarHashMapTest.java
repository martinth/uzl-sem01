package de.uniluebeck.algodes.uebung6;

import static org.junit.Assert.*;

import java.awt.LinearGradientPaint;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LinarHashMapTest {
	
	Logger log = Logger.getLogger(this.getClass().getCanonicalName());

	@Test
	public void testInsert() {
		LinarHashMap map = new LinarHashMap(new SimpleHash());
		for(int i=0; i<= 20; i++) {
			map.insert(i);
			log.debug(map);
		}
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

}
