package de.uzl.algodes.ueb8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class ProblemFile {
    
    public List<Problem> problems = new ArrayList<Problem>();


    public ProblemFile(File inputFile) throws IOException, NumberFormatException {
        FileInputStream stream = new FileInputStream(inputFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        int numTestCases = Integer.parseInt(br.readLine());
        for(int i = 0; i < numTestCases; i++) {
            
            String[] line = Iterables.toArray(Splitter.on(' ').split(br.readLine()), String.class);
            int numItems = Integer.parseInt(line[0]);
            
            // split next line on spaces and map parseInt onto each element
            Iterable<Integer> requests = Iterables.transform(Splitter.on(' ').omitEmptyStrings().split(br.readLine()), new Function<String, Integer>() {
                public Integer apply(String s) {
                    return Integer.parseInt(s);
                }
            });
            this.problems.add(new Problem(numItems, requests));
        }
    }
    
    
    class Problem {
        public List<Integer> requests = new ArrayList<Integer>();
        public int numItems;
        
        Problem(int numItems, Iterable<Integer> req) {
            this.numItems = numItems;
            for (Integer i : req) {
                requests.add(i);
            }
        }
        
        public String toString() {
            return Objects.toStringHelper(this)
                .addValue(Joiner.on(',').join(requests).toString())
                .toString();
        }
    }
}
