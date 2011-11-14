package de.uniluebeck.algoes.uebung2;

public class KnapsackSolver {

    private Knapsack sack;

    public KnapsackSolver(Knapsack instance) {
        this.sack = instance;
    }

    public long solve() {
        if (sack.weights.length < 10) {
            return sackSolverRecursive(sack.weights.length-1, sack.max_weight);
        } else {
            return sackSolverDynamic(sack.weights.length-1, sack.max_weight);
        }
    }
    
    private long sackSolverRecursive(int idx, int max_weight) {
        if (idx == 0) {
            return 0;
        }
        if (max_weight < sack.weights[idx]) {
            return sackSolverRecursive(idx - 1, max_weight);
        } else {
            return Math.max(
                sackSolverRecursive(idx-1, max_weight),
                sack.values[idx] + sackSolverRecursive(idx-1, max_weight-sack.weights[idx]));
        }
    }
    
    private long sackSolverDynamic(int idx, int max_weight) {
        
        long[][] table = new long[max_weight+1][idx+1];
        
        for (int k = 0; k <= idx; k++) {
            for (int i=0; i<= max_weight; i++) {
                if (k==0) {
                    table[i][k] = 0;
                } else if (i < sack.weights[k]) {
                    table[i][k] = table[i][k-1];
                } else {
                    table[i][k] = Math.max(
                        table[i][k-1],
                        sack.values[k] + table[i-sack.weights[k]][k-1]
                    );
                }
            }
        }
        
        return table[max_weight][idx];
        
    }
    
    private long sackSolverDynamicSum(int idx, int max_weight) {
        int valueSum = 0;
        for (int v : sack.values) valueSum += v;
        
        long[][] table = new long[valueSum+1][idx+1];
        
        for (int k = 0; k < sack.weights.length; k++) {
            for (int i = 0; i <= valueSum; i++) {
                if (k == 0) {
                    if (max_weight == 0) {
                        table[i][k] = 0;
                    } else {
                        table[i][k] = Integer.MAX_VALUE;
                    }
                } else if (sack.values[k] > i) {
                    table[i][k] = table[i][k-1];
                } else {
                    table[i][k] = Math.min(
                        table[i][k-1],
                        sack.weights[k] + table[i-sack.values[k]][k-1]
                    );
                }
            }
            
        }
        
        
        for(int k = 0; k < table[0].length; k++) {
            for (int i = 0; i < table.length; i++) {
                System.out.printf("%d | ", table[i][k]);
            }
            System.out.println();
        }
        
        return 0;
    }
}
