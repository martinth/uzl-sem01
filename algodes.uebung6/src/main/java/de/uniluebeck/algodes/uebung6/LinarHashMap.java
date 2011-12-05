package de.uniluebeck.algodes.uebung6;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;

public class LinarHashMap implements HashMap {
    
    private final Logger log = Logger.getLogger(this.getClass().getCanonicalName());
	
    /** epsilon for floating point comparsion of load factor */
	private static final double LOAD_EPSILON = 0.00001;
    /** default size for backing array */
	private static int DEFAULT_SIZE = 10;
	private static double a_min = 0.125; // 1/8
	private static double a_opt = 0.25;  // 1/4
	private static double a_max = 0.5;	 // 1/2
	
	/** backing array */
	private Entry[] array;
	/** entries in array */
	private int entries = 0;
	
	Hashfunction hashfunc;
	
	/**
	 * @param hashfunc the {@link Hashfunction} to be used
	 */
	public LinarHashMap(Hashfunction hashfunc) {
        this(hashfunc, DEFAULT_SIZE);
    }
	
	/**
	 * @param hashfunc the {@link Hashfunction} to be used
	 * @param initalSize inital size of backing array
	 */
	public LinarHashMap(Hashfunction hashfunc, int initalSize) {
		this.hashfunc = hashfunc;
		this.array = new Entry[initalSize];
	}
	
	/**
	 * Create a new map based on another map. Is used by rehash.
     * @param hashfunc the {@link Hashfunction} to be used
     * @param initalSize inital size of backing array
     */
	private LinarHashMap(LinarHashMap map, int initalSize) {
	    this.entries = 0;
        this.array = new Entry[initalSize];
        this.hashfunc = map.hashfunc.createNext();
        
	    for (Entry entry : map.array) {
            if(entry != null && !entry.isMark()) {
                this.insert(entry.getValue());
            }
        }
	}
	
	public void insert(int key)  {
	    // calc position
        int desiredPos = hashfunc.hash(key, array.length);
        
        // will hold the index where the key was inserted
        int insertedAt = -1;
        
        /* loop over array position until we find a free one. we start
         * with offset 0, so the first try is the desired position returned
         * by the hash function.
         * there should always be a free position! */
        for(int offset = 0; offset < array.length; offset++) {
            int tryPos = (desiredPos + offset) % array.length;
            
            // if position is empty store the key there
            if(array[tryPos] == null) {
                array[tryPos] = new Entry(key);
                entries += 1;
                insertedAt = tryPos;
                break;
            }
            
            // do nothing because the key is already in there
            if(array[tryPos] != null && array[tryPos].getValue() == key) {
                log.debug("Key "+key+" is already in map at position "+tryPos);
                return;
            } 
            
        }
        
        assert(insertedAt != -1);
        log.info("Inserted key "+key+" at position "+insertedAt);
        
        float loadFactor = getLoadFactor();
        log.debug("Current load factor is " + loadFactor);
        if(loadFactor > a_max || Math.abs(loadFactor - a_max) < LOAD_EPSILON) {
		    rehash();
		}
	}
	
	/**
	 * @return current load factor
	 */
	float getLoadFactor() {
	    return ((float)entries) / array.length;
	}
	
	/**
	 * @return number of entries in this map
	 */
	int getEntryCount() {
	    return this.entries;
	}

	/**
	 * do a rehash of all entries
	 */
	private void rehash() {
        int newSize = (int) Math.floor(entries / a_opt);
        log.info("Rehash is needed. Optimal size for new Hashmap is " + newSize + " (current: "+array.length+")");

        /* temporarly raise loglevel to avoid recursive log messages */
        Level oldLevel = log.getLevel();
        log.setLevel(Level.WARN);
        
        // create new map based on this and copy values to this instance
        LinarHashMap tmpMap = new LinarHashMap(this, newSize);
        this.array = tmpMap.array;
        this.entries = tmpMap.entries;
        this.hashfunc = tmpMap.hashfunc;
        
        // reset loglevel
        log.setLevel(oldLevel);
    }

    public void delete(int key) {
        
        // search position in array (-1 if not found) and delete key
		int posInArray = searchPos(key);
		
		if(posInArray != -1) {
		    this.array[posInArray].setMark(true);
		    entries -= 1;
		}

		log.info("Deleted key "+key+" at position "+posInArray);
        
        float loadFactor = getLoadFactor();
        log.debug("Current load factor is " + loadFactor);
        if(loadFactor < a_min || Math.abs(loadFactor - a_min) < LOAD_EPSILON) {
            rehash();
        }
	}
    
    /**
     * Search the index of a given key within the map.
     * @param key the key to be searched
     * @return the index of the key or -1 if not found
     */
    private int searchPos(int key) {
        int desiredPos = hashfunc.hash(key, array.length);
        
        // will hold the actual position of the 
        int actualPos = -1;

        /* loop over array beginning at hash position */
        for(int offset = 0; offset < array.length; offset++) {
            int idx = (desiredPos + offset) % array.length;
            
            // if there is a null, then the values is not in this map
            if(array[idx] == null) {
                break;
            }
            // else it may be this one, if not, the loop will look at the next position
            else {
                if(array[idx].getValue() == key) {
                    actualPos = idx;
                }
            }
        }
        return actualPos;
    }

	public boolean search(int key) {
	    return searchPos(key) != -1;
	}
	
	public String toString() {
		return Objects.toStringHelper(this).addValue(Joiner.on(',').useForNull("n").join(array)).toString();
	}
	
	/**
	 * Represents an entry in this hashmap
	 * @author martin
	 *
	 */
	class Entry {
		
		/** if an entry was marked as deleted*/
		private boolean isMark;
		
		/** value within this entry */
		private int value;
		
		Entry(int value) {
			this.value = value;
			this.isMark = false;
		}
		

		public boolean isMark() {
			return isMark;
		}
		public void setMark(boolean isMark) {
			this.isMark = isMark;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
			this.isMark = false;
		}
		
		public String toString() {
			if(this.isMark()) {
				return "m";
			} else {
				return ((Integer) value).toString();
			}
		}

	}

}
