package week7;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Assignment1Test {
    
    @Test
    public void testInputParser() throws IOException {
        Job[] jobs = InputParser.parseAssignmentJobsTxt();
        assertEquals(10000, jobs.length);
        assertEquals(8, jobs[0].getWeight());
        assertEquals(50, jobs[0].getLength());
    }
    
    Scheduler nonOptimalScheduler = new NonOptimalGreedyScheduler();
    Scheduler optimalScheduler = new OptimalGreedyScheduler();
    
    @Test
    public void testNonOptimal1() throws IOException {
        Job[] jobs = InputParser.parseResource("/jobs_test1.txt");
        long cost = nonOptimalScheduler.schedule(jobs);
        assertEquals(11336, cost);
    }
    
    @Test
    public void testNonOptimal2() throws IOException {
        Job[] jobs = InputParser.parseResource("/jobs_test2.txt");
        long cost = nonOptimalScheduler.schedule(jobs);
        assertEquals(145924, cost);
    }
    
    @Test
    public void testOptimal1() throws IOException {
        Job[] jobs = InputParser.parseResource("/jobs_test1.txt");
        long cost = optimalScheduler.schedule(jobs);
        assertEquals(10548, cost);
    }
    
    @Test
    public void testOptimal2() throws IOException {
        Job[] jobs = InputParser.parseResource("/jobs_test2.txt");
        long cost = optimalScheduler.schedule(jobs);
        assertEquals(138232, cost);
    }
}