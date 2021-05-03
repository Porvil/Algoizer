package com.iiitd.dsavisualizer.datastructures.graphs;

// Contains Graph algorithm's time/space complexities
public class GraphAlgorithmStats {

    public String algorithm;
    public String time;
    public String space;

    @Override
    public String toString() {
        return "GraphAlgorithmStats{" +
                "algorithm='" + algorithm + '\'' +
                ", time='" + time + '\'' +
                ", space='" + space + '\'' +
                '}';
    }

    public static GraphAlgorithmStats getInstance(GraphAlgorithmType graphAlgorithmType) {
        GraphAlgorithmStats graphAlgorithmStats = new GraphAlgorithmStats();
        switch (graphAlgorithmType){
            case BFS:
            case BFS_CC:
                graphAlgorithmStats.algorithm = "BFS";
                graphAlgorithmStats.time = "V + E";
                graphAlgorithmStats.space = "V";
                break;
            case DFS:
            case DFS_CC:
                graphAlgorithmStats.algorithm = "DFS";
                graphAlgorithmStats.time = "V + E";
                graphAlgorithmStats.space = "V";
                break;
            case DIJKSTRA:
                graphAlgorithmStats.algorithm = "Dijkstra";
                graphAlgorithmStats.time = "(V + E) * log(V)";
                graphAlgorithmStats.space = "V";
                break;
            case BELLMAN_FORD:
                graphAlgorithmStats.algorithm = "Bellman Ford";
                graphAlgorithmStats.time = "V * E";
                graphAlgorithmStats.space = "V";
                break;
            case KRUSKALS:
                graphAlgorithmStats.algorithm = "Kruskals";
                graphAlgorithmStats.time = "E * log(V)";
                graphAlgorithmStats.space = "V";
                break;
            case PRIMS:
                graphAlgorithmStats.algorithm = "Prims";
                graphAlgorithmStats.time = "E * log(V)";
                graphAlgorithmStats.space = "V";
                break;
        }

        return graphAlgorithmStats;
    }

}