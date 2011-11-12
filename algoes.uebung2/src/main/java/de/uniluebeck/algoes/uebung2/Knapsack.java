package de.uniluebeck.algoes.uebung2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Knapsack {
    
    private int size;
    private ArrayList<Integer> weights = new ArrayList<Integer>();
    private ArrayList<Integer> values = new ArrayList<Integer>();

    private Knapsack() {}

    public static Knapsack fromFile(String filename) throws FileNotFoundException, IOException {
        BufferedReader inFile = new BufferedReader(new FileReader(filename));
        
        Knapsack sack = new Knapsack();
        
        String target = inFile.readLine();
        if (target == null) {
            throw new IOException(String.format("Input file '%s' had invalid format", filename));
        }
        sack.size = Integer.parseInt(target);
        
        String line;
        while ((line = inFile.readLine()) != null)   {
            String[] parts = line.split(" ");
            if (parts.length != 2) {
                throw new IOException(String.format("Input file '%s' had invalid format", filename));
            }
            sack.weights.add(Integer.parseInt(parts[0]));
            sack.values.add(Integer.parseInt(parts[1]));
            
        }
        return sack;
    }

}
