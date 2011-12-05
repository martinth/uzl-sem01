package de.uniluebeck.algodes.uebung6;

/**
 * A hash function for use in {@link HashMap}.
 * 
 * @author martin
 * 
 */
public interface Hashfunction {
	
	/**
	 * Hashes a given value. 
	 * @param key the key to be hashed
	 * @param size the size of the hashmap
	 * @return the hashed key
	 */
	int hash(int key, int size);
	
	/**
	 * Creates a new Hashfunction that is based on the current instance.
	 * @return a new hashfunction
	 */
	Hashfunction createNext ();
}