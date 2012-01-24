package de.uzl.algodes.ueb8;

import java.util.ArrayList;
import com.google.common.base.Joiner;

import de.uzl.algodes.ueb8.ProblemFile.Problem;

/**
 * Magically solve the given problem by using a fenwick tree and a (reversed)
 * index array. Completely copied from the example solution C5.cc at
 * http://2011.nwerc.eu/results/nwerc2011-solutions.zip 
 */
public class FenwickSolver {
    
    private final static int MAX_N = 100005*2;
    private Problem problem;

    public FenwickSolver(Problem problem) {
        this.problem = problem;
    }
    
    public String solve() {
        
        ArrayList<Integer> output = new ArrayList<Integer>();
        FenwickTree tree = new FenwickTree(MAX_N);
        int pos[] = new int [MAX_N];
        
        for(int i = 1; i <= problem.numItems; i++) {
            tree.add(i, 1);
            pos[problem.numItems - i + 1] = i;
        }
        
        for(int i = 0; i < problem.requests.size(); i++) {
            int request = problem.requests.get(i);
            output.add(problem.numItems - tree.cumulative(pos[request]));
            tree.add(pos[request], -1);
            pos[request] = i + problem.numItems + 1;
            tree.add(pos[request], 1);
        }

        return Joiner.on(' ').join(output);
    }

}