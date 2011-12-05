package de.uniluebeck.algodes.uebung6;

import org.apache.log4j.Logger;

/**
 * Simple hash function. Implements h(x) = x mod r.
 * 
 * @author martin
 * 
 */
public class SimpleHash implements Hashfunction {
	
	Logger log = Logger.getLogger(this.getClass().getCanonicalName());

	public int hash(int key, int size) {
		int hash = key % size;
		log.debug("Hash for "+key+": "+hash);
		return hash;
	}

	public Hashfunction createNext() {
		return this;
	}

}
