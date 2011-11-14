package de.uniluebeck.algoes.uebung2;

import java.io.FileNotFoundException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Give problem instacces as parameters");
            System.exit(1);
        }
        for (String problemFile : args) {
            try {
                Knapsack instance = Knapsack.fromFile(problemFile);
                KnapsackSolver solver = new KnapsackSolver(instance);
                System.out.printf("%s: %d\n", problemFile, solver.solve());
            } catch (FileNotFoundException e) {
                System.err.printf("File '%s' not found\n", problemFile);
                System.exit(1);
            } catch (IOException e) {
                System.err.println(e);
                System.exit(1);
            }
        }
        
    }
}
