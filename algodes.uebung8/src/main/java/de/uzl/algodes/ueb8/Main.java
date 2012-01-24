package de.uzl.algodes.ueb8;

import java.io.File;
import java.io.IOException;

import de.uzl.algodes.ueb8.ProblemFile.Problem;

public class Main {

    public static void main(String[] args) throws NumberFormatException, IOException {
        if(args.length < 1) {
            System.err.println("Give problem instance as first parameter");
            System.exit(1);
        }
        ProblemFile pf = new ProblemFile(new File(args[0]));
        for (Problem problem : pf.problems) {
            System.out.println(new FenwickSolver(problem).solve());
        }
    
    }

}
