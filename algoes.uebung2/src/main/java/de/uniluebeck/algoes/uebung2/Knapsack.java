package de.uniluebeck.algoes.uebung2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Knapsack {
    
    int max_weight;
    Integer[] weights;
    Integer[] values; 
    

    private Knapsack() {}

    public static Knapsack fromFile(String filename) throws FileNotFoundException, IOException {
        BufferedReader inFile = new BufferedReader(new FileReader(filename));
        
        Knapsack sack = new Knapsack();
        
        String target = inFile.readLine();
        if (target == null) {
            throw new IOException(String.format("Input file '%s' had invalid format", filename));
        }
        sack.max_weight = Integer.parseInt(target);
        
        ArrayList<Integer> w_tmp = new ArrayList<Integer>();
        ArrayList<Integer> v_tmp = new ArrayList<Integer>();
        
        String line;
        while ((line = inFile.readLine()) != null)   {
            String[] parts = line.split(" ");
            if (parts.length != 2) {
                throw new IOException(String.format("Input file '%s' had invalid format", filename));
            }
            w_tmp.add(Integer.parseInt(parts[0]));
            v_tmp.add(Integer.parseInt(parts[1]));
            
        }
        sack.weights = w_tmp.toArray(new Integer[] {});
        sack.values = v_tmp.toArray(new Integer[] {});
        
        return sack;
    }

}
