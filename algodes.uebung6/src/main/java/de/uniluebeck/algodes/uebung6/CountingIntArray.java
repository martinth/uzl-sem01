package de.uniluebeck.algodes.uebung6;

import java.util.Arrays;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.google.common.collect.Iterators;

class CountingIntArray implements Iterable<Integer> {
	
	private Integer[] array;
	private int entries = 0;
	private String name;
	
	public CountingIntArray(String name, int size) {
		this.array = new Integer[size];
		this.name = name;
	}
	
	public int length() {
		return this.array.length;
	}
	
	public int entries() {
		return this.entries;
	}
	
	public float loadFactor() {
		return ((float)entries) / array.length; 
	}
	
	public String toString() {
		return name;
	}
	
	public void set(int idx, Integer val) {
		if(val == null && array[idx] == null) return;
		
		if(val == null && array[idx] != null) {
			array[idx] = null;
			entries--;
			assert(entries >= 0);
		}
		
		if(val != null && array[idx] == null) {
			array[idx] = val;
			entries++;
		}
		
		if(val != null && array[idx] != null) {
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
