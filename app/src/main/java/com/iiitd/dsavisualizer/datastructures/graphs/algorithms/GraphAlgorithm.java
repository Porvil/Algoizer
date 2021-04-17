package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import android.content.Context;
import android.view.View;

import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import static com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType.*;

import com.iiitd.dsavisualizer.datastructures.graphs.GraphDSPopUp;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphTree;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphTreePopUp;
import com.iiitd.dsavisualizer.utility.UtilUI;

public class GraphAlgorithm {
    public GraphAlgorithmType graphAlgorithmType;
    public Graph graph;
    public GraphSequence graphSequence;
    public GraphTree graphTree;
    public GraphTreePopUp graphTreePopUp;
    public GraphDSPopUp graphTreeDSPopUp;

    public static GraphAlgorithm graphAlgorithm;

    // returns a NULL instance
    public static GraphAlgorithm getInstance(Context context, View parent){
        if(graphAlgorithm == null) {
            graphAlgorithm = new GraphAlgorithm();
            graphAlgorithm.graphAlgorithmType = NULL;
            graphAlgorithm.graphSequence = null;
            graphAlgorithm.graphTree = null;

            int treePopUpWidth = (int) UtilUI.dpToPx(context, 200);
            int treeDSPopUpWidth = (int) UtilUI.dpToPx(context, 150);

            graphAlgorithm.graphTreePopUp = new GraphTreePopUp(context, treePopUpWidth, parent.getHeight(), parent);
            graphAlgorithm.graphTreeDSPopUp = new GraphDSPopUp(context, treeDSPopUpWidth, parent.getHeight(), parent);
        }

        return graphAlgorithm;
    }

    public void reset(){
       this.graphAlgorithmType = NULL;
       this.graphSequence = null;
       this.graphTree = null;

        if(this.graphTreeDSPopUp != null){
            this.graphTreeDSPopUp.dismiss();
        }

        if(this.graphTreePopUp != null){
            this.graphTreePopUp.dismiss();
        }

    }

    public boolean runAlgo(Graph graph, GraphAlgorithmType graphAlgorithmType, int vertexNumber){
        this.graphAlgorithmType = graphAlgorithmType;
        this.graphSequence = null;
        this.graphTree = null;

        if(this.graphTreeDSPopUp != null){
            this.graphTreeDSPopUp.dismiss();
        }

        if(this.graphTreePopUp != null){
            this.graphTreePopUp.dismiss();
        }

        switch (graphAlgorithmType){
            case BFS: {
                BFS bfs = new BFS(graph, BFS);
                this.graphSequence = bfs.bfs(vertexNumber);
                this.graphTree = bfs.graphTree;

                this.graphTreePopUp.create("BFS Tree", graphAlgorithm.graphTree);
                this.graphTreePopUp.show();
                this.graphTreeDSPopUp.create("QUEUE", GraphAlgorithmType.BFS);
                if(graphSequence.graphAnimationStates.size() > 0) {
                    graphTreeDSPopUp.update(graphSequence.graphAnimationStates.get(0).graphAnimationStateExtra);
                }
                graphTreeDSPopUp.show();
            }
                break;
            case BFS_CC: {
                BFS bfs = new BFS(graph, BFS_CC);
                this.graphSequence = bfs.bfsCC();
            }
                break;
            case DFS: {
                DFS dfs = new DFS(graph, DFS);
                this.graphSequence = dfs.dfs(vertexNumber);
                this.graphTree = dfs.graphTree;

                this.graphTreePopUp.create("DFS Tree", graphAlgorithm.graphTree);
                this.graphTreePopUp.show();
                this.graphTreeDSPopUp.create("STACK", GraphAlgorithmType.DFS);
                if(graphSequence.graphAnimationStates.size() > 0) {
                    graphTreeDSPopUp.update(graphSequence.graphAnimationStates.get(0).graphAnimationStateExtra);
                }
                this.graphTreeDSPopUp.show();
            }
                break;
            case DFS_CC: {
                DFS dfs = new DFS(graph, DFS_CC);
                this.graphSequence = dfs.dfsCC();
            }
                break;
            case DIJKSTRA:{
                Dijkstra dijkstra = new Dijkstra(graph);
                this.graphSequence = dijkstra.dijkstra(vertexNumber);
            }
                break;
            case BELLMAN_FORD:{
                BellmanFord bellmanFord = new BellmanFord(graph);
                this.graphSequence = bellmanFord.bellmanford();
            }
                break;
            case KRUSKALS: {
                Kruskals kruskals = new Kruskals(graph);
                this.graphSequence = kruskals.kruskals();
                this.graphTreeDSPopUp.create("EDGES", KRUSKALS);
                if(graphSequence.graphAnimationStates.size() > 0) {
                    graphTreeDSPopUp.update(graphSequence.graphAnimationStates.get(0).graphAnimationStateExtra);
                }
                graphTreeDSPopUp.show();
            }
                break;
            case PRIMS:{
                Prims prims = new Prims(graph);
                this.graphSequence = prims.prims();
            }
                break;
        }


        return true;
    }

}
