package de.uniluebeck.algodes.uebung6;

/**
 * Simple hash function. Implements h(x) = x mod r.
 * 
 * @author martin
 * 
 */
public class SimpleHash implements Hashfunction {

	public int hash(int key, int size) {
		return key % size;
	}

	public Hashfunction createNext() {
		return this;
	}

}
