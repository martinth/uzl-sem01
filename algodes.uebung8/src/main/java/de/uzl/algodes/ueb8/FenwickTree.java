package de.uzl.algodes.ueb8;

/**
 * Implements a basic Fenwick tree. Based on example solution C5.cc from
 * http://2011.nwerc.eu/results/nwerc2011-solutions.zip 
 * @author Martin Thurau
 *
 */
public class FenwickTree {
    
    private int[] bitArray;

    public FenwickTree(int size) {
        this.bitArray = new int[size];
    }
    
    public void add(int idx, int diff) {
        while (idx < bitArray.length) {
            bitArray[idx] += diff;
            idx += idx & (-idx); // strip off least significant bit, only works because of two-complement 
        }
    }
    
    public int cumulative(int idx) {
        int ret = 0;

        while (idx != 0) {
            ret += bitArray[idx];
            idx -= idx & (-idx);
        }

        return ret;
    }

}
