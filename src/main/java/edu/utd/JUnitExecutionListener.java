package edu.utd;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class JUnitExecutionListener extends RunListener {

    // When test suite started to run
    public void testRunStarted(Description description) throws Exception {
        System.out.println("Test Run Started\n");
        CoverageCollection.testSuite = new HashMap<String, HashMap<String, LinkedHashSet<Integer>>>();
    }

    // When test suite finished running
    public void testRunFinished(Result result) throws Exception {
        System.out.println("Test Run Finished\n");
        try {
            FileWriter fw = new FileWriter("stmt-cov.txt",true);
            StringBuffer sb = new StringBuffer();
            for(String testName : CoverageCollection.testSuite.keySet()) {
                // write [Test] + Name
                sb.append(testName + "\n");
                // write className + line
                HashMap<String, LinkedHashSet<Integer>> testCase = CoverageCollection.testSuite.get(testName);

                for(String className : testCase.keySet()){
                    for(Integer i : testCase.get(className)){
                        sb.append(className + ":" + i + "\n");
                    }
                }
            }
            fw.write(sb.toString());
            fw.close();
        } catch (IOException ex) {
            System.err.println("Couldn't log this");
        }
    }

    // When a single test started to run
    public void testStarted(Description description) throws Exception {
        CoverageCollection.testName = "[TEST] " + description.getClassName() + ":" + description.getMethodName();
        CoverageCollection.testCase = new HashMap<String, LinkedHashSet<Integer>>();
        System.out.println(CoverageCollection.testName + " Started\n");
    }

    //When a single test finished running
    public void testFinished(Description description) throws Exception {
        CoverageCollection.testSuite.put(CoverageCollection.testName, CoverageCollection.testCase);
        System.out.println(CoverageCollection.testName + " Finished\n");
    }

//    public void testFailure(Failure failure) throws Exception {
//        System.out.println("Failed: " + failure.getDescription().getMethodName());
//    }
//
//    public void testAssumptionFailure(Failure failure) {
//        System.out.println("Failed: " + failure.getDescription().getMethodName());
//    }
//
//    public void testIgnored(Description description) throws Exception {
//        System.out.println("Ignored: " + description.getMethodName());
//    }
}