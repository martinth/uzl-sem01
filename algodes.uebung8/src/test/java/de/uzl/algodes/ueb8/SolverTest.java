package de.uzl.algodes.ueb8;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import de.uzl.algodes.ueb8.FenwickSolver;
import de.uzl.algodes.ueb8.ProblemFile;
import de.uzl.algodes.ueb8.ProblemFile.Problem;

public class SolverTest {
    
    /**
     * Test with small problem instance
     */
    @Test
    public void testSmall() throws NumberFormatException, IOException {
        ProblemFile pf = new ProblemFile(new File("src/test/resources/sample-short.txt"));
        
        FileInputStream stream = new FileInputStream("src/test/resources/output-short.txt");
        BufferedReader solution = new BufferedReader(new InputStreamReader(stream));
        
        for (Problem problem : pf.problems) {
            String mySolution = new FenwickSolver(problem).solve();
            assertEquals(solution.readLine(), mySolution);
        }
    }

    /**
     * Test with large problem instance
     */
    @Test
    public void testLarge() throws NumberFormatException, IOException {
        ProblemFile pf = new ProblemFile(new File("src/test/resources/sample-large.txt"));
        
        FileInputStream stream = new FileInputStream("src/test/resources/output-large.txt");
        BufferedReader solution = new BufferedReader(new InputStreamReader(stream));
        
        for (Problem problem : pf.problems) {
            String mySolution = new FenwickSolver(problem).solve();
            assertEquals(solution.readLine(), mySolution);
        }
    }
}
