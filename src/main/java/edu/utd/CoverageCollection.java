package edu.utd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CoverageCollection {
    public static HashMap<String, HashMap<String, Set>> testCases;
    public static HashMap<String, Set> testCase;

    public void visitLine(String className, int line){
        if(testCase == null || className == null) return;
        Set<Integer> set = testCase.get(className);
        if(set == null) {
            set = new HashSet<Integer>();
        }
        set.add(line);
        testCase.put(className, set);
    }

}
