package de.uniluebeck.algodes.uebung6.util;

import java.util.Arrays;

/**
 * Wrapper class for an Integer array that knows how many elements are stored in
 * the integer array. As "element" counts everything that is not null.
 * 
 * @author martin
 * 
 */
public class CountingIntArray implements Iterable<Integer> {

	private Integer[] array;
	private int entries = 0;
	private String name;

	public CountingIntArray(String name, int size) {
		this.array = new Integer[size];
		this.name = name;
	}

	/**
	 * @return length of the internal array
	 */
	public int getLength() {
		return this.array.length;
	}

	/**
	 * @return get the number of entries in the internal array
	 */
	public int getEntrycount() {
		return this.entries;
	}

	/**
	 * @return get load factore
	 */
	public float getLoadFactor() {
		return ((float) entries) / array.length;
	}

	public String toString() {
		return name;
	}

	/**
	 * Set the value at the given index with the given value.
	 * @param idx
	 * @param val
	 */
	public void set(int idx, Integer val) {
		if (val == null && array[idx] == null)
			return;

		if (val == null && array[idx] != null) {
			array[idx] = null;
			entries--;
			assert (entries >= 0);
		}

		if (val != null && array[idx] == null) {
			array[idx] = val;
			entries++;
		}

		if (val != null && array[idx] != null) {
			array[idx] = val;
		}
	}

	public Integer get(int idx) {
		return array[idx];
	}

	public java.util.Iterator<Integer> iterator() {
		return Arrays.asList(array).iterator();
	}

}
