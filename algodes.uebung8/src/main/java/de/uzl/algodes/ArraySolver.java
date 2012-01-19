package de.uzl.algodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;

import de.uzl.algodes.ProblemFile.Problem;

public class ArraySolver {
    private Problem problem;

    public ArraySolver(Problem problem) {
        this.problem = problem;
    }
    
    public String solve() {
        
        LinkedList<Integer> data = new LinkedList<Integer>();
        
        for(int i = 1; i <= problem.numItems; i++) {
            data.add(i);
        }
       
        ArrayList<Integer> output = new ArrayList<Integer>();
        
        int size = data.size();
        for(Integer request : problem.requests) {
            for(int i = 0; i < size; i++) {
                if(data.get(i).equals(request)) {
                    output.add(i);
                    data.remove(i);
                    data.add(0, request);
                    break;
                }
            }
        }
        return Joiner.on(' ').join(output);
    }
    
    class Node {
        
        public int value;
        public int position;
        public List<Node> neighboors;
        
        public Node(int value, int position) {
            this.value = value;
            this.position = position;
            this.neighboors = new LinkedList<Node>();
        }
        
    }
}
