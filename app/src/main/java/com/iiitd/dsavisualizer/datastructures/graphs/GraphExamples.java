package com.iiitd.dsavisualizer.datastructures.graphs;

// Contains Graph Examples for Graph Algorithms
public class GraphExamples {

    public static String example1 = "D 0\n" +
            "W 0\n" +
            "VC 15\n" +
            "VA 0 3 7\n" +
            "VA 1 3 5\n" +
            "VA 2 5 5\n" +
            "VA 3 1 7\n" +
            "VA 4 1 10\n" +
            "VA 5 3 10\n" +
            "VA 6 5 10\n" +
            "VA 7 4 1\n" +
            "VA 8 3 2\n" +
            "VA 9 5 2\n" +
            "VA 10 1 4\n" +
            "VA 11 0 2\n" +
            "VA 12 1 3\n" +
            "VA 13 0 5\n" +
            "VA 14 1 5\n" +
            "E 0 1 1\n" +
            "E 0 5 1\n" +
            "E 0 2 1\n" +
            "E 0 3 1\n" +
            "E 0 6 1\n" +
            "E 1 0 1\n" +
            "E 1 2 1\n" +
            "E 2 0 1\n" +
            "E 2 1 1\n" +
            "E 2 6 1\n" +
            "E 3 0 1\n" +
            "E 3 5 1\n" +
            "E 3 4 1\n" +
            "E 4 3 1\n" +
            "E 4 5 1\n" +
            "E 5 0 1\n" +
            "E 5 3 1\n" +
            "E 5 4 1\n" +
            "E 5 6 1\n" +
            "E 6 5 1\n" +
            "E 6 2 1\n" +
            "E 6 0 1\n" +
            "E 7 8 1\n" +
            "E 7 9 1\n" +
            "E 8 7 1\n" +
            "E 9 7 1\n" +
            "E 10 12 1\n" +
            "E 11 12 1\n" +
            "E 12 11 1\n" +
            "E 12 10 1\n" +
            "E 12 13 1\n" +
            "E 13 12 1\n" +
            "E 13 14 1\n" +
            "E 14 13 1\n";

    public static String example2 = "D 1\n" +
            "W 0\n" +
            "VC 8\n" +
            "VA 0 2 2\n" +
            "VA 1 1 4\n" +
            "VA 2 3 4\n" +
            "VA 3 5 4\n" +
            "VA 4 0 6\n" +
            "VA 5 2 6\n" +
            "VA 6 4 6\n" +
            "VA 7 2 9\n" +
            "E 0 1 1\n" +
            "E 0 2 1\n" +
            "E 1 4 1\n" +
            "E 2 3 1\n" +
            "E 2 5 1\n" +
            "E 4 7 1\n" +
            "E 4 5 1\n" +
            "E 5 0 1\n" +
            "E 6 5 1\n" +
            "E 6 3 1\n" +
            "E 7 6 1\n";

    public static String example3 = "D 1\n" +
            "W 0\n" +
            "VC 7\n" +
            "VA 0 3 5\n" +
            "VA 1 3 3\n" +
            "VA 2 5 3\n" +
            "VA 3 1 5\n" +
            "VA 4 1 8\n" +
            "VA 5 3 8\n" +
            "VA 6 5 8\n" +
            "E 0 1 1\n" +
            "E 0 5 1\n" +
            "E 0 2 1\n" +
            "E 1 2 1\n" +
            "E 3 0 1\n" +
            "E 3 5 1\n" +
            "E 3 4 1\n" +
            "E 4 5 1\n" +
            "E 5 6 1\n" +
            "E 6 2 1\n" +
            "E 6 0 1\n";

    public static String example4 = "D 0\n" +
            "W 1\n" +
            "VC 9\n" +
            "VA 0 3 2\n" +
            "VA 1 1 4\n" +
            "VA 2 1 6\n" +
            "VA 3 1 8\n" +
            "VA 4 3 10\n" +
            "VA 5 5 8\n" +
            "VA 6 5 6\n" +
            "VA 7 5 4\n" +
            "VA 8 3 6\n" +
            "E 0 1 4\n" +
            "E 0 7 8\n" +
            "E 1 0 4\n" +
            "E 1 7 11\n" +
            "E 1 2 8\n" +
            "E 2 1 8\n" +
            "E 2 8 2\n" +
            "E 2 3 7\n" +
            "E 2 5 4\n" +
            "E 3 2 7\n" +
            "E 3 5 14\n" +
            "E 3 4 9\n" +
            "E 4 3 9\n" +
            "E 4 5 10\n" +
            "E 5 2 4\n" +
            "E 5 3 14\n" +
            "E 5 4 10\n" +
            "E 5 6 2\n" +
            "E 6 5 2\n" +
            "E 6 7 1\n" +
            "E 6 8 6\n" +
            "E 7 0 8\n" +
            "E 7 1 11\n" +
            "E 7 6 1\n" +
            "E 7 8 7\n" +
            "E 8 2 2\n" +
            "E 8 6 6\n" +
            "E 8 7 7\n";

    public static String example5 = "D 1\n" +
            "W 1\n" +
            "VC 6\n" +
            "VA 0 3 3\n" +
            "VA 1 1 5\n" +
            "VA 2 4 6\n" +
            "VA 3 1 8\n" +
            "VA 4 4 9\n" +
            "VA 5 2 10\n" +
            "E 0 1 5\n" +
            "E 0 2 -2\n" +
            "E 1 3 1\n" +
            "E 2 1 2\n" +
            "E 2 4 4\n" +
            "E 2 3 4\n" +
            "E 3 5 3\n" +
            "E 3 4 -1\n" +
            "E 4 5 1\n";

    public static String example6 = "D 1\n" +
            "W 1\n" +
            "VC 7\n" +
            "VA 0 3 2\n" +
            "VA 1 1 5\n" +
            "VA 2 3 5\n" +
            "VA 3 5 5\n" +
            "VA 4 1 8\n" +
            "VA 5 5 8\n" +
            "VA 6 2 11\n" +
            "E 0 2 5\n" +
            "E 0 3 5\n" +
            "E 0 1 6\n" +
            "E 1 4 -1\n" +
            "E 1 0 -10\n" +
            "E 2 1 -2\n" +
            "E 2 4 1\n" +
            "E 3 2 -2\n" +
            "E 3 5 -1\n" +
            "E 4 6 3\n" +
            "E 5 6 3\n";

    public static String example7 = "D 0\n" +
            "W 1\n" +
            "VC 7\n" +
            "VA 0 3 3\n" +
            "VA 1 1 5\n" +
            "VA 2 1 8\n" +
            "VA 3 3 10\n" +
            "VA 4 5 8\n" +
            "VA 5 5 5\n" +
            "VA 6 3 6\n" +
            "E 0 1 10\n" +
            "E 0 5 25\n" +
            "E 1 0 10\n" +
            "E 1 2 28\n" +
            "E 2 1 28\n" +
            "E 2 3 16\n" +
            "E 2 6 14\n" +
            "E 3 2 16\n" +
            "E 3 4 12\n" +
            "E 4 6 18\n" +
            "E 4 3 12\n" +
            "E 4 5 22\n" +
            "E 5 4 22\n" +
            "E 5 6 24\n" +
            "E 5 0 25\n" +
            "E 6 2 14\n" +
            "E 6 4 18\n" +
            "E 6 5 24\n";

    public static String example8 = "D 0\n" +
            "W 1\n" +
            "VC 7\n" +
            "VA 0 2 3\n" +
            "VA 1 0 5\n" +
            "VA 2 4 5\n" +
            "VA 3 2 7\n" +
            "VA 4 0 9\n" +
            "VA 5 4 9\n" +
            "VA 6 2 11\n" +
            "E 0 1 1\n" +
            "E 0 2 5\n" +
            "E 1 0 1\n" +
            "E 1 3 8\n" +
            "E 1 4 7\n" +
            "E 1 2 4\n" +
            "E 2 0 5\n" +
            "E 2 3 6\n" +
            "E 2 5 2\n" +
            "E 2 1 4\n" +
            "E 3 1 8\n" +
            "E 3 2 6\n" +
            "E 3 4 11\n" +
            "E 3 5 9\n" +
            "E 4 1 7\n" +
            "E 4 3 11\n" +
            "E 4 5 3\n" +
            "E 4 6 10\n" +
            "E 5 2 2\n" +
            "E 5 3 9\n" +
            "E 5 6 12\n" +
            "E 5 4 3\n" +
            "E 6 5 12\n" +
            "E 6 4 10\n";

    public static String example9 = "D 0\n" +
            "W 1\n" +
            "VC 9\n" +
            "VA 0 3 2\n" +
            "VA 1 1 4\n" +
            "VA 2 5 4\n" +
            "VA 3 3 5\n" +
            "VA 4 1 7\n" +
            "VA 5 5 7\n" +
            "VA 6 1 9\n" +
            "VA 7 5 9\n" +
            "VA 8 3 11\n" +
            "E 0 1 4\n" +
            "E 0 2 8\n" +
            "E 1 0 4\n" +
            "E 1 2 11\n" +
            "E 1 4 8\n" +
            "E 2 0 8\n" +
            "E 2 1 11\n" +
            "E 2 3 7\n" +
            "E 2 5 1\n" +
            "E 3 2 7\n" +
            "E 3 4 2\n" +
            "E 3 5 6\n" +
            "E 4 1 8\n" +
            "E 4 3 2\n" +
            "E 4 6 7\n" +
            "E 4 7 4\n" +
            "E 5 3 6\n" +
            "E 5 2 1\n" +
            "E 5 7 2\n" +
            "E 6 4 7\n" +
            "E 6 7 14\n" +
            "E 6 8 9\n" +
            "E 7 5 2\n" +
            "E 7 6 14\n" +
            "E 7 8 10\n" +
            "E 7 4 4\n" +
            "E 8 7 10\n" +
            "E 8 6 9\n";

}