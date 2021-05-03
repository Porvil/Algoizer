package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.EdgePro;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateExtra;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphTree;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexCLRS;

import static com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateType.NONE;
import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.*;
import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// BFS
public class BFS {
    Graph graph;
    GraphSequence graphSequence;
    public GraphTree graphTree;
    HashMap<Integer, VertexCLRS> map;
    HashMap<Integer, Vertex> verticesState;
    LinkedList<Integer> queue ;
    ArrayList<Edge> edges;
    int currentID;

    public BFS(Graph graph, GraphAlgorithmType graphAlgorithmType){
        this.graph = graph;
        this.graphSequence = new GraphSequence(graphAlgorithmType);
        this.graphTree = new GraphTree(graph.directed, graph.weighted);
        this.map = new HashMap<>();
        this.verticesState = new HashMap<>();
        this.edges = new ArrayList<>();
        this.queue = new LinkedList<>();
        this.currentID = 0;
    }

    public GraphSequence bfs(int source) {
        int size = graph.noOfVertices;

        if (size < 1)
            return graphSequence;

        // Add all vertices
        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            VertexCLRS vertexCLRS = VertexCLRS.bfsVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
            verticesState.put(entry.getKey(), new Vertex(entry.getValue(), NONE));
        }

        // Add all edges
        for(Edge edge : graph.getAllEdges()) {
            edges.add(new Edge(edge, NONE));
        }

        // Start Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("bfs(" + source + ")")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addQueues(queue)));

        Vertex vertex = verticesState.get(source);
        VertexCLRS startVertexCLRS = map.get(source);
        vertex.setToHighlight();
        queue.add(source);

        startVertexCLRS.color = GRAY;
        startVertexCLRS.bfsDist = 0;
        startVertexCLRS.parent = -1;

        // Push to queue Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("source vertex(" + source + ") added to queue")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addQueues(queue)));

        while (queue.size() != 0) {
            int u = queue.pop();
            VertexCLRS srcVertexCLRS = map.get(u);
            Vertex srcVertex = verticesState.get(u);
            srcVertex.setToHighlight();

            // Visit Node
            graphSequence.addGraphAnimationState(
                    GraphAnimationState.create()
                            .setInfo("vertex (" + u + ") popped from queue, check all edges")
                            .setVerticesState(verticesState)
                            .addEdges(edges)
                            .addGraphAnimationStateExtra(
                                    GraphAnimationStateExtra.create()
                                            .addQueues(queue)));

            for(Edge curEdge : graph.edgeListMap.get(u)) {
                int v = curEdge.des;

                Vertex desVertex = verticesState.get(curEdge.des);
                VertexCLRS endVertexCLRS = map.get(curEdge.des);
                Edge edge = edges.get(edges.indexOf(curEdge));
                GraphAnimationStateType edgeGAST = edge.graphAnimationStateType;
                GraphAnimationStateType desVertexGAST = desVertex.graphAnimationStateType;
                edge.setToHighlight();
                desVertex.setToHighlight();

                if (endVertexCLRS.color == WHITE) {
                    // TREE EDGE
                    graphTree.addEdge(new EdgePro(edge, TREE));

                    endVertexCLRS.color = GRAY;
                    endVertexCLRS.bfsDist = srcVertexCLRS.bfsDist + 1;
                    endVertexCLRS.parent = u;

                    queue.add(v);

                    graphSequence.addGraphAnimationState(
                            GraphAnimationState.create()
                                    .setInfo("vertex (" + v + ") not visited" + "\n" + "added to queue")
                                    .setVerticesState(verticesState)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(
                                            GraphAnimationStateExtra.create()
                                                    .addQueues(queue)));

                    edge.setToDone();
                    desVertex.setToDone();
                }
                else{
                    graphSequence.addGraphAnimationState(
                            GraphAnimationState.create()
                                    .setInfo("vertex (" + v + ") already visited, continue")
                                    .setVerticesState(verticesState)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(
                                            GraphAnimationStateExtra.create()
                                                    .addQueues(queue)));

                    edge.setGAST(edgeGAST);
                    desVertex.setGAST(desVertexGAST);
                }

                if (!(map.get(v).bfsDist == map.get(u).bfsDist + 1)){
                    int src = u;
                    int des = v;

                    while (map.get(src).bfsDist > 0 && map.get(src).bfsDist > map.get(des).bfsDist) {
                        src = map.get(src).parent;
                    }
                    while (map.get(des).bfsDist > 0 && map.get(des).bfsDist > map.get(src).bfsDist) {
                        des = map.get(des).parent;
                    }

                    // BACK EDGE
                    if (src == des) {
                        graphTree.addEdge(new EdgePro(edge, BACK));
                    }
                    // CROSS EDGE
                    else {
                        graphTree.addEdge(new EdgePro(edge, CROSS));
                    }
                }
            }

            srcVertexCLRS.color = BLACK;
            srcVertex.setToDone();

            graphSequence.addGraphAnimationState(
                    GraphAnimationState.create()
                            .setInfo("vertex (" + u + ") all edges visited")
                            .setVerticesState(verticesState)
                            .addEdges(edges)
                            .addGraphAnimationStateExtra(
                                    GraphAnimationStateExtra.create()
                                            .addQueues(queue)));
        }

        // End Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("bfs() completed")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addQueues(queue)));

        // ALL DONE
        for(Map.Entry<Integer, VertexCLRS> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

        // --------------------- START OF GRAPH TREE ---------------------
        List<Map.Entry<Integer, VertexCLRS> > list = new LinkedList<>(map.entrySet());

        int maxRows = 0;
        for(Map.Entry<Integer, VertexCLRS> entry : list){
            maxRows = entry.getValue().bfsDist != Integer.MAX_VALUE ? Math.max(maxRows, entry.getValue().bfsDist) : maxRows;
        }

        ArrayList<Integer>[] bfsLayers = new ArrayList[maxRows+1];

        for(int i=0;i<maxRows+1;i++){
            bfsLayers[i] = new ArrayList<>();
        }

        for(Map.Entry<Integer, VertexCLRS> entry : list){
            if(entry.getValue().bfsDist != Integer.MAX_VALUE && entry.getValue().bfsDist >= 0) {
                bfsLayers[entry.getValue().bfsDist].add(entry.getKey());
            }
        }

        int maxCols = 0;
        for(ArrayList<Integer> arrayList : bfsLayers){
            maxCols = Math.max(maxCols, arrayList.size());
        }

        int[][] coordinates = new int[maxRows+1][maxCols];

        for(int row=0;row<bfsLayers.length;row++) {
            int fill = bfsLayers[row].size();
            int gap = maxCols-fill;
            int colIndex = 0;
            for(int col=0;col<maxCols;col++){
                if(fill >= gap){
                    coordinates[row][col] = bfsLayers[row].get(colIndex);
                    fill--;
                    colIndex++;
                }
                else{
                    coordinates[row][col] = -1;
                    gap--;
                }
            }
        }

        for(int row=0;row<coordinates.length;row++) {
            for(int col=0;col<coordinates[row].length;col++) {
                if(coordinates[row][col] != -1) {
                    graphTree.addVertex(coordinates[row][col], row, col);
                }
            }
        }

        // Rows is 0-indexed, Cols is using 1-indexed, as size() is used
        graphTree.noOfRows = maxRows+1;
        graphTree.noOfCols = maxCols;
        // --------------------- END OF GRAPH TREE ---------------------

        return graphSequence;
    }

    public GraphSequence bfsCC(){
        int size = graph.noOfVertices;

        if (size < 1)
            return graphSequence;

        // Add all vertices
        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            VertexCLRS vertexCLRS = VertexCLRS.bfsVertexCCCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
            verticesState.put(entry.getKey(), new Vertex(entry.getValue(), NONE));
        }

        // Add all edges
        for(Edge edge : graph.getAllEdges()) {
            edges.add(new Edge(edge, NONE));
        }

        currentID = 0;

        // Start Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("bfsConnectedComponents()")
                        .setVerticesState(verticesState)
                        .addEdges(edges));

        for(Map.Entry<Integer, VertexCLRS> entry : map.entrySet()){
            if(entry.getValue().connectedID == -1) {
                bfsCCVisit(entry.getValue().data);
                currentID++;
            }
        }

        // End Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("bfsConnectedComponents() completed"
                                + "\n" + "no. of connected components = " + currentID)
                        .setVerticesState(verticesState)
                        .addEdges(edges));

        return graphSequence;
    }

    private void bfsCCVisit(int s){
        queue.add(s);

        while (queue.size() != 0) {
            int u = queue.pop();
            VertexCLRS srcVertexCLRS = map.get(u);
            Vertex srcVertex = verticesState.get(u);
            srcVertex.data = currentID;
            srcVertex.setToDone();

            srcVertexCLRS.color = BLACK;
            srcVertexCLRS.connectedID = currentID;

            // Visit Node
            graphSequence.addGraphAnimationState(
                    GraphAnimationState.create()
                            .setInfo("vertex (" + u + ") popped from queue, check all edges")
                            .setVerticesState(verticesState)
                            .addEdges(edges));

            for(Edge curEdge : graph.edgeListMap.get(u)) {
                int v = curEdge.des;
                Vertex desVertex = verticesState.get(curEdge.des);
                VertexCLRS endVertexCLRS = map.get(curEdge.des);
                Edge edge = edges.get(edges.indexOf(curEdge));
                GraphAnimationStateType edgeGAST = edge.graphAnimationStateType;
                GraphAnimationStateType desVertexGAST = desVertex.graphAnimationStateType;
                edge.setToHighlight();
                desVertex.setToHighlight();

                if (endVertexCLRS.color == WHITE) {
                    desVertex.data = currentID;
                    endVertexCLRS.color = BLACK;
                    endVertexCLRS.connectedID = currentID;
                    endVertexCLRS.parent = u;
                    queue.add(v);

                    graphSequence.addGraphAnimationState(
                            GraphAnimationState.create()
                                    .setInfo("vertex (" + v + ") not visited" + "\n" + "added to queue")
                                    .setVerticesState(verticesState)
                                    .addEdges(edges));


                    edge.setToDone();
                    desVertex.setToDone();
                }
                else{
                    graphSequence.addGraphAnimationState(
                            GraphAnimationState.create()
                                    .setInfo("vertex (" + v + ") already visited, continue")
                                    .setVerticesState(verticesState)
                                    .addEdges(edges));

                    edge.setGAST(edgeGAST);
                    desVertex.setGAST(desVertexGAST);
                }
            }
            srcVertex.setToDone();
        }

    }

}