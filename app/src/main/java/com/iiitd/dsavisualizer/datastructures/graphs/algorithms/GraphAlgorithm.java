package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import static com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType.*;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphTree;

public class GraphAlgorithm {

    public GraphAlgorithmType graphAlgorithmType;
    public Graph graph;
    public GraphSequence graphSequence;
    public GraphTree graphTree;

    // returns a NULL instance
    public static GraphAlgorithm getInstance(){
        GraphAlgorithm graphAlgorithm = new GraphAlgorithm();
        graphAlgorithm.graphAlgorithmType = NULL;
        graphAlgorithm.graphSequence = null;
        graphAlgorithm.graphTree = null;

        return graphAlgorithm;
    }

    public void reset(){
       this.graphAlgorithmType = NULL;
       this.graphSequence = null;
       this.graphTree = null;
    }

    public static GraphAlgorithm getInstance(Graph graph, GraphAlgorithmType graphAlgorithmType, int vertexNumber){
        GraphAlgorithm graphAlgorithm = new GraphAlgorithm();
        graphAlgorithm.graphAlgorithmType = graphAlgorithmType;
        graphAlgorithm.graphSequence = null;
        graphAlgorithm.graphTree = null;

        switch (graphAlgorithmType){
            case BFS: {
                BFS bfs = new BFS(graph, BFS);
                graphAlgorithm.graphSequence = bfs.run(vertexNumber);
                graphAlgorithm.graphTree = bfs.graphTree;
            }
                break;
            case BFS_CC: {
                BFS bfs = new BFS(graph, BFS_CC);
                graphAlgorithm.graphSequence = bfs.connectedComponents();
            }
                break;
            case DFS: {
                DFS dfs = new DFS(graph, DFS);
                graphAlgorithm.graphSequence = dfs.dfs(vertexNumber);
                graphAlgorithm.graphTree = dfs.graphTree;
            }
                break;
            case DFS_CC: {
                DFS dfs = new DFS(graph, DFS_CC);
                graphAlgorithm.graphSequence = dfs.dfsCC();
            }
                break;
            case DIJKSTRA:{
                Dijkstra dijkstra = new Dijkstra(graph);
                graphAlgorithm.graphSequence = dijkstra.dijkstra(vertexNumber);
            }
                break;
            case BELLMAN_FORD:{
                BellmanFord bellmanFord = new BellmanFord(graph);
                graphAlgorithm.graphSequence = bellmanFord.bellmanford();
            }
                break;
            case KRUSKALS: {
                Kruskals kruskals = new Kruskals(graph);
                graphAlgorithm.graphSequence = kruskals.kruskals();
            }
                break;
            case PRIMS:{
                Prims prims = new Prims(graph);
                graphAlgorithm.graphSequence = prims.prims();
            }
                break;
        }

        return graphAlgorithm;
    }

}
