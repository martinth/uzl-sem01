package de.uniluebeck.algodes.uebung6;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

public class LinarHashMap implements HashMap {
	
	/** default size for backing array */
	private static int DEFAULT_SIZE = 10;
	private static double a_min = 0.125; // 1/8
	private static double a_opt = 0.25;  // 1/4
	private static double a_max = 0.5;	 // 1/2
	
	/** backing array */
	Entry[] array = new Entry[DEFAULT_SIZE];
	
	Hashfunction hashfunc;
	
	public LinarHashMap(Hashfunction hashfunc) {
		this.hashfunc = hashfunc;
	}

	public void insert(int key) {
		// calc position
		int desiredPos = hashfunc.hash(key, array.length);
		
		// if position is empty store the key there
		if(array[desiredPos] == null) {
			array[desiredPos] = new Entry(key);
		}
		// the desired postion is not empty
		else {
			boolean foundFreePos = false;
			/* loop over array position until we find a free one. if
			 * there should always be a free position! */
			for(int offset = 1; offset < array.length; offset++) {
				int tryPos = (desiredPos + offset) % array.length;
				if(array[tryPos] == null) {
					array[tryPos] = new Entry(key);
					foundFreePos = true;
					return;
				}
			}
			//FIXME wenn er keine position findet muss was passieren
			//auch wenn das eigentlich nicht passieren dürfte
		}
	}

	public void delete(int key) {
		// TODO Auto-generated method stub

	}

	public boolean search(int key) {
		// TODO Auto-generated method stub
		return false;
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
