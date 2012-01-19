package de.uzl.algodes;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import de.uzl.algodes.ProblemFile.Problem;

public class SolverTest {
    
    @Test
    public void testSmall() throws NumberFormatException, IOException {
        
        
        ProblemFile pf = new ProblemFile(new File("src/test/resources/sample-short.txt"));
        
        FileInputStream stream = new FileInputStream("src/test/resources/output-short.txt");
        BufferedReader solution = new BufferedReader(new InputStreamReader(stream));
        
        
        for (Problem problem : pf.problems) {
            String mySolution = new ArraySolver(problem).solve();
            
            assertEquals(solution.readLine(), mySolution);
      
        }
    }

    @Test
    public void testLarge() throws NumberFormatException, IOException {
        ProblemFile pf = new ProblemFile(new File("src/test/resources/sample-large.txt"));
        
        FileInputStream stream = new FileInputStream("src/test/resources/output-large.txt");
        BufferedReader solution = new BufferedReader(new InputStreamReader(stream));
        
        
        for (Problem problem : pf.problems) {
            String mySolution = new ArraySolver(problem).solve();
            
            assertEquals(solution.readLine(), mySolution);
      
        }
    }

}
