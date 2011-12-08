package de.uniluebeck.algodes.uebung6;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import de.uniluebeck.algodes.uebung6.hashes.Hashfunction;
import de.uniluebeck.algodes.uebung6.hashes.UniversalHash;

public class CuckooHashMap implements HashMap {
	
    private final Logger log = Logger.getLogger(this.getClass().getCanonicalName());
	
    private static final int COUNT_EPSILON = 1;
    
    /** epsilon for floating point comparsion of load factor */
	private static final double LOAD_EPSILON = 0.00001;
    /** default size for backing array */
	private static int DEFAULT_SIZE = 10;
	private static double a_min = 0.0625; // 1/16
	private static double a_opt = 0.125;  // 1/8
	private static double a_max = 0.25;	 // 1/4
	
	/** backing arrays */
	private CountingIntArray array1;
	private CountingIntArray array2;
	
	Hashfunction hash1;
	Hashfunction hash2;
	
	/**
	 * @param hashfunc the {@link Hashfunction} to be used
	 */
	public CuckooHashMap() {
        this(DEFAULT_SIZE);
    }
	
	/**
	 * @param hashfunc the {@link Hashfunction} to be used
	 * @param initalSize inital size of backing array
	 */
	public CuckooHashMap(int initalSize) {
		this.hash1 = new UniversalHash();
		this.hash2 = new UniversalHash();
		this.array1 = new CountingIntArray("H1", initalSize);
		this.array2 = new CountingIntArray("H2", initalSize);
	}
	
	private CuckooHashMap(CuckooHashMap map, int initalSize) {
	    this.array1 = new CountingIntArray("H1", initalSize); 
	    this.array2 = new CountingIntArray("H2", initalSize); 
        this.hash1 = map.hash1.createNext();
        this.hash2 = map.hash2.createNext();
        
	    for (Integer entry : Iterables.concat(map.array1, map.array2)) {
            if(entry != null) {
                this.insert(entry);
            }
        }
	}

	public void insert(int key) {
		if(search(key)) return;
		
		ArrayPosWrapper arrayPos = this.searchPlace(key);
		if(arrayPos != null) {
			arrayPos.set(key);
			log.debug("Key "+key+" was inserted in "+arrayPos.array+ " at pos "+arrayPos.pos);
		} else {
			int countDown = (int) (Math.log(array1.length()) / Math.log(1+COUNT_EPSILON));
			
			CountingIntArray curArray = array1; 
			
			Integer swappedOut = key;
			do {
				swappedOut = this.swap(swappedOut, curArray);
				curArray = curArray == array1 ? array2 : array1;
				countDown -= 1;
			} while (swappedOut != null && countDown > 0);
			
			if(swappedOut != null) {
				log.error("Need to rehash!");
			}
		}
		log.debug(this);
		
		if(array1.loadFactor() > a_max || array2.loadFactor() > a_max) {
		    rehash();
		}
	}
	
	
	

	private void rehash() {
		int maxEntries = Math.max(array1.entries(), array2.entries());
		int newSize = (int) Math.floor(maxEntries / a_opt);
        log.info("Rehash is needed. Optimal size is " + newSize + " (current max: "+maxEntries+")");

        /* temporarly raise loglevel to avoid recursive log messages */
        Level oldLevel = log.getLevel();
        log.setLevel(Level.WARN);
        
        // create new map based on this and copy values to this instance
        CuckooHashMap tmpMap = new CuckooHashMap(this, newSize);
        this.array1 = tmpMap.array1;
        this.array2 = tmpMap.array2;
        this.hash1 = tmpMap.hash1;
        this.hash2 = tmpMap.hash2;
        
        // reset loglevel
        log.setLevel(oldLevel);
        log.debug("State of HahsMap: " + this);
		
	}

	private Integer swap(int key, CountingIntArray array) {
		if(array == this.array1) {
			int pos = hash1.hash(key, array1.length());
			Integer swappedOut = array1.get(pos);
			array1.set(pos, key);
			log.debug(String.format("Swapped %d out of %s for %d @ pos %d", swappedOut, array1, key, pos));
			return swappedOut;
		} else {
			int pos = hash2.hash(key, array2.length());
			Integer swappedOut = array2.get(pos);
			array2.set(pos, key);
			log.debug(String.format("Swapped %d out of %s for %d @ pos %d", swappedOut, array2, key, pos));
			return swappedOut;
		}		
	}

	public void delete(int key) {
		
		ArrayPosWrapper arrayPos = this.searchKey(key);
		if(arrayPos != null) {
			arrayPos.set(null);
			log.debug("Key "+key+" was deleted in "+arrayPos.array+ " at pos "+arrayPos.pos);
		}

		/* if the key was in none of the two arrays, it is not 
		 * in this HashMap, so we do nothing, which is okay */
	}

	public boolean search(int key) {
		ArrayPosWrapper found = this.searchKey(key);
		if(found != null) {
			//log.debug("Key "+key+" was found in "+found.array+ " at pos "+found.pos);
			return true;
		} else {
			//log.debug("Key "+key+" was not found");
			return false;
		}	
	}
	
	private ArrayPosWrapper searchKey(int key) {
		// check if key in array1
		int pos1 = this.hash1.hash(key, array1.length());
		if(array1.get(pos1) != null && array1.get(pos1) == key) {
			return new ArrayPosWrapper(array1, pos1);
		}
		
		// if not array1, maybe it is in array2
		int pos2 = hash2.hash(key, array2.length());
		if(array2.get(pos2)!= null && array2.get(pos2) == key) {
			return new ArrayPosWrapper(array2, pos2);
		}
		
		// the key is in non the arrays, so it is not stored
		return null;
	}
	
	private ArrayPosWrapper searchPlace(int key) {
		// check if the key has a place in array1
		int pos1 = this.hash1.hash(key, array1.length());
		if(array1.get(pos1) == null) {
			return new ArrayPosWrapper(array1, pos1);
		}
		
		// if not array1, maybe it fits in array2
		int pos2 = hash2.hash(key, array2.length());
		if(array2.get(pos2) == null) {
			return new ArrayPosWrapper(array2, pos2);
		}
		
		// there is not place :(
		return null;
	}
	
	public String toString() {
		return Objects.toStringHelper(this)
				.add("H1", Joiner.on(',').useForNull("n").join(array1))
				.add("H2", Joiner.on(',').useForNull("n").join(array2))
				.toString();
	}
	
	class ArrayPosWrapper {
		
		CountingIntArray array;
		int pos;

		ArrayPosWrapper(CountingIntArray array1, int pos) {
			this.array = array1;
			this.pos = pos;
		}
		
		public void set(Integer val) {
			this.array.set(this.pos, val);
		}
	}

}
