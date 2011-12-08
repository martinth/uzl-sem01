package de.uniluebeck.algodes.uebung6.hashes;

/**
 * Simple hash function. Implements h(x) = x mod r.
 * 
 * @author martin
 * 
 */
public class SimpleHash implements Hashfunction {
	
	public int hash(int key, int size) {
		int hash = key % size;
		return hash;
	}

	public Hashfunction createNext() {
		return this;
	}

}
