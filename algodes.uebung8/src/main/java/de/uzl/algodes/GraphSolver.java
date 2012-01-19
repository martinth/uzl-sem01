package de.uzl.algodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;

import de.uzl.algodes.ProblemFile.Problem;

public class GraphSolver {
    private Problem problem;

    public GraphSolver(Problem problem) {
        this.problem = problem;
    }
    
    public String solve() {
        
        Map<Integer, Node> intToNode = new HashMap<Integer, Node>();
        Node root = null;
        
        for(int i = 1; i <= problem.numItems; i++) {
            // create new node with inital position and value
            Node newNode = new Node(i, i-1);
            // the new node has connections to all nodes with lower values
            newNode.neighboors.addAll(intToNode.values());
            // store node in map for easier access
            intToNode.put(i, newNode);
        }
        root = intToNode.get(1);
        
        ArrayList<Integer> output = new ArrayList<Integer>();
        
        for(Integer request : problem.requests) {
            Node node = intToNode.get(request);
            output.add(node.position);
            node.position = 0;
            root = node;
            for(Node neighboor : node.neighboors) {
                neighboor.position += 1;
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
