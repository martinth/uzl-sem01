package de.uniluebeck.algodes.uebung6;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import de.uniluebeck.algodes.uebung6.hashes.Hashfunction;
import de.uniluebeck.algodes.uebung6.hashes.UniversalHash;
import de.uniluebeck.algodes.uebung6.util.CountingIntArray;

/**
 * Implements a HashMap using the cuckoo hashmap algorithm.
 * 
 * @author martin
 * 
 */
public class CuckooHashMap implements HashMap {

	private final Logger log = Logger.getLogger(this.getClass()
			.getCanonicalName());

	/** epsilon for use in calculating the max iterations in insert() */
	private static final int COUNT_EPSILON = 1;

	/** default size for backing arrays */
	private static int DEFAULT_SIZE = 10;
	private static double a_min = 0.0625; // 1/16
	private static double a_opt = 0.125; // 1/8
	private static double a_max = 0.25; // 1/4

	/** backing arrays */
	private CountingIntArray array1;
	private CountingIntArray array2;

	/** hash functions */
	Hashfunction hash1;
	Hashfunction hash2;

	public CuckooHashMap() {
		this(DEFAULT_SIZE);
	}

	/**
	 * @param initalSize
	 *            inital size of backing array
	 */
	public CuckooHashMap(int initalSize) {
		this.hash1 = new UniversalHash();
		this.hash2 = new UniversalHash();
		this.array1 = new CountingIntArray("H1", initalSize);
		this.array2 = new CountingIntArray("H2", initalSize);
	}

	/**
	 * Create a new CuckooHashMap based on another one. The hashfunctions of
	 * this new CuckooHashMap will be derived from the ones in map
	 * 
	 * @param map the other HashMap to derive the new one from
	 * @param initalSize the desired size of the backing array
	 */
	private CuckooHashMap(CuckooHashMap map, int initalSize) {
		this.array1 = new CountingIntArray("H1", initalSize);
		this.array2 = new CountingIntArray("H2", initalSize);
		this.hash1 = map.hash1.createNext();
		this.hash2 = map.hash2.createNext();

		/* insert all elements from the old map into this one */
		for (Integer entry : Iterables.concat(map.array1, map.array2)) {
			if (entry != null) {
				this.insert(entry);
			}
		}
	}

	public void insert(int key) {

		/*
		 * if key is already in map, don't insert it. this operation takes
		 * constant time, so this doesn't hurt
		 */
		if (search(key))
			return;

		/* check if a free position for this key is in one of the array */
		ArrayPosWrapper arrayPos = this.searchPlace(key);
		/* if there is a free position, insert the key there */
		if (arrayPos != null) {
			arrayPos.set(key);
			log.info("Key " + key + " was inserted in " + arrayPos.array
					+ " at pos " + arrayPos.pos);
		}
		/* if there is no free space, we use the cuckoo operation to make place */
		else {
			/* maximum cuckoo operations */
			int countDown = (int) (Math.log(array1.getLength()) / Math
					.log(1 + COUNT_EPSILON));

			/* we start with on the first array */
			CountingIntArray curArray = array1;
			Integer swappedOut = key;

			/*
			 * the cuckoo operation inserts the current key in swappedOut until
			 * it has found a free place or the countdown reaches 0
			 */
			do {
				swappedOut = this.swap(swappedOut, curArray);
				curArray = curArray == array1 ? array2 : array1;
				countDown -= 1;
			} while (swappedOut != null && countDown > 0);

			/* if swappedOut is still not null, we have to rehash */
			if (swappedOut != null) {
				log.warn("Could not insert in "+countDown+" steps. Rehash!");
				rehash();
			}
		}
		log.debug(this);

		if (Math.max(array1.getLoadFactor(), array2.getLoadFactor()) > a_max) {
			rehash();
		}
	}

	public void delete(int key) {

		/* searchKey(key) returns where the key was found */
		ArrayPosWrapper arrayPos = this.searchKey(key);
		if (arrayPos != null) {
			arrayPos.set(null);
			log.debug("Key " + key + " was deleted in " + arrayPos.array
					+ " at pos " + arrayPos.pos);

			log.debug(this);
			if (Math.max(array1.getLoadFactor(), array2.getLoadFactor()) < a_min) {
				rehash();
			}
		}
		/*
		 * if the key was in none of the two arrays, it is not in this HashMap,
		 * so we do nothing
		 */
	}

	public boolean search(int key) {
		ArrayPosWrapper found = this.searchKey(key);
		if (found != null) {
			// log.debug("Key "+key+" was found in "+found.array+
			// " at pos "+found.pos);
			return true;
		} else {
			// log.debug("Key "+key+" was not found");
			return false;
		}
	}

	/*
	 * Do the rehash (yeah!) This is implemented be creating a new inner
	 * CuckooHashMap based on this one. See
	 * CuckooHashMap#CuckooHashMap(CuckooHashMap, int)
	 */
	private void rehash() {
		int maxEntries = Math.max(array1.getEntrycount(), array2.getEntrycount());
		int newSize = (int) Math.floor(maxEntries / a_opt);
		log.info("Rehash is needed. Optimal size is " + newSize
				+ " (current max: " + maxEntries + ")");

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

	/**
	 * Swaps out a given key from the given array and returns the value, that
	 * was in the position where the the key was inserted.
	 * 
	 * @param key the key to insert into the given array
	 * @param array the array to insert the key into
	 * @return the value that was in that position where the key was inserted,
	 *         may be null
	 */
	private Integer swap(int key, CountingIntArray array) {
		if (array == this.array1) {
			int pos = hash1.hash(key, array1.getLength());
			Integer swappedOut = array1.get(pos);
			array1.set(pos, key);
			log.debug(String.format("Swapped %d out of %s for %d @ pos %d",
					swappedOut, array1, key, pos));
			return swappedOut;
		} else {
			int pos = hash2.hash(key, array2.getLength());
			Integer swappedOut = array2.get(pos);
			array2.set(pos, key);
			log.debug(String.format("Swapped %d out of %s for %d @ pos %d",
					swappedOut, array2, key, pos));
			return swappedOut;
		}
	}

	/**
	 * Search a given key in both internal arrays. Returns the position and the
	 * array where the key was found, wrapped in an {@link ArrayPosWrapper}.
	 * 
	 * @param key the key to search
	 * @return ArrayPosWrapper where the key was found, or null if the key was
	 *         not in this HashMap
	 */
	private ArrayPosWrapper searchKey(int key) {
		// check if key in array1
		int pos1 = this.hash1.hash(key, array1.getLength());
		if (array1.get(pos1) != null && array1.get(pos1) == key) {
			return new ArrayPosWrapper(array1, pos1);
		}

		// if not array1, maybe it is in array2
		int pos2 = hash2.hash(key, array2.getLength());
		if (array2.get(pos2) != null && array2.get(pos2) == key) {
			return new ArrayPosWrapper(array2, pos2);
		}

		// the key is in non the arrays, so it is not stored
		return null;
	}

	/**
	 * Searches a place for the given key in the two internal arrays. Returns
	 * the position and array where the key can be inserted, wrapped in an
	 * {@link ArrayPosWrapper}. If no possible position is found, null is
	 * returned.
	 * 
	 * @param key the key for which a place should be searched
	 * @return ArrayPosWrapper where to put the key, or null if no possible
	 *         position is found
	 */
	private ArrayPosWrapper searchPlace(int key) {
		// check if the key has a place in array1
		int pos1 = this.hash1.hash(key, array1.getLength());
		if (array1.get(pos1) == null) {
			return new ArrayPosWrapper(array1, pos1);
		}

		// if not array1, maybe it fits in array2
		int pos2 = hash2.hash(key, array2.getLength());
		if (array2.get(pos2) == null) {
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

	/**
	 * Helperclass to wrap an array and a position in this array.
	 * 
	 * @author martin
	 * 
	 */
	class ArrayPosWrapper {

		CountingIntArray array;
		int pos;

		ArrayPosWrapper(CountingIntArray array, int pos) {
			this.array = array;
			this.pos = pos;
		}

		/**
		 * Helper method to set a value in the current array directly
		 * 
		 * @param val  value to set
		 */
		public void set(Integer val) {
			this.array.set(this.pos, val);
		}
	}

}
