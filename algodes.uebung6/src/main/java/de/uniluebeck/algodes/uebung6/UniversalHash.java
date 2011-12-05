package de.uniluebeck.algodes.uebung6;

import java.math.BigInteger;
import java.util.Random;

import org.apache.log4j.Logger;

import com.google.common.base.Objects;

/**
 * Universal hash function. Implements H_p
 * @author martin
 *
 */
public class UniversalHash implements Hashfunction {
	
	Logger log = Logger.getLogger(this.getClass().getCanonicalName());
	
	/**
	 * a large prime number used in the current family
	 */
	private long p;
	
	/* bases for this universal hash function */
	private long a;
	private long b;
	
	private final Random random = new Random();
	
	private UniversalHash(long familyBase) {
		this.p = familyBase;
		calcFamilyBases();
		log.info("Created new "+ this +" based on given base "+ familyBase);
	}

	public UniversalHash() {
		/* create a prime number with Long.SIZE bitlength for use
		 * as p for the current family. We use a long, since p must be larger
		 * than the maximum key */
		p = BigInteger.probablePrime(Long.SIZE-1, random).longValue() % Long.MAX_VALUE;
		/* calc a and b based on p */
		calcFamilyBases();
		
		log.info("Created new "+ this);
	}

	/**
	 * Chooses a random a and b for this instance with 0 < a,b < p-1.
	 */
	private void calcFamilyBases() {
		a = Math.abs((1 + random.nextLong()) % p-1);
		assert(a > 0);
		assert(a < p-1);
		
		b = Math.abs((1 + random.nextLong()) % p-1);
		assert(b > 0);
		assert(b < p-1);
	}
	
	public int hash(int key, int size) {
		/* calculate the hash bases on the values of a,b and p:
		 * 		h_a,b(x) = ((ax+b) mod p) mod r
		 * cast to int is save, since typeof(size) = int, so 
		 * the modulo n % size is < Integer.MAX_INT */
		return (int) ((a*key+b) % p) % size;
	}

	public Hashfunction createNext() {
		return new UniversalHash(p);
	}
	
	public String toString() {
		return Objects.toStringHelper(this)
				.add("p", p)
				.add("a", a)
				.add("b", b)
				.toString();
	}


}
