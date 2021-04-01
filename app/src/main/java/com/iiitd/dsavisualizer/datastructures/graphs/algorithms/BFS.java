package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.EdgePro;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateExtra;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphTree;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexCLRS;

import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.*;
import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BFS {
    Graph graph;
    GraphSequence graphSequence;
    public GraphTree graphTree;
    HashMap<Integer, VertexCLRS> map;
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;
    int currentID;

    public BFS(Graph graph, GraphAlgorithmType graphAlgorithmType){
        this.graph = graph;
        this.graphSequence = new GraphSequence(graphAlgorithmType);
        this.graphTree = new GraphTree(graph.directed, graph.weighted);
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        currentID = 0;
        map = new HashMap<>();
    }

    public GraphSequence run(int s) {

        if(graph == null || graph.vertexMap.size() == 0){
            return graphSequence;
        }

        LinkedList<Integer> queue = new LinkedList<>();

        {
            GraphAnimationState graphAnimationState =
                    GraphAnimationState.create()
                            .setState("start")
                            .setInfo("start")
                            .addVertices(vertices)
                            .addEdges(edges)
                            .addGraphAnimationStateExtra(
                                    GraphAnimationStateExtra.create()
                                            .addQueues(queue));

            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = VertexCLRS.bfsVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
        }
        queue.add(s);

        Vertex vertex = graph.vertexMap.get(s);
        map.get(s).color = GRAY;
        map.get(s).bfsDist = 0;
        map.get(s).parent = -1;


        vertices.add(vertex);
        GraphAnimationState graphAnimationState =
                GraphAnimationState.create()
                        .setState("Visit = " + s)
                        .setInfo("Add source = " + s + " to queue")
                        .addVertices(vertices)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addQueues(queue));

        graphSequence.addGraphAnimationState(graphAnimationState);

        while (queue.size() != 0) {
            int u = queue.pop();

            GraphAnimationState graphAnimationState1 =
                    GraphAnimationState.create()
                            .setState("Vertex = " + u)
                            .setInfo("Vertex popped from queue = " + u)
                            .addVertices(vertices)
                            .addEdges(edges)
                            .addGraphAnimationStateExtra(
                                    GraphAnimationStateExtra.create()
                                            .addQueues(queue));

            graphSequence.addGraphAnimationState(graphAnimationState1);

            for(Edge edge : graph.edgeListMap.get(u)) {
                int v = edge.des;
                if (map.get(v).color == WHITE) {

                    map.get(v).color = GRAY;
                    map.get(v).bfsDist = map.get(u).bfsDist + 1;
                    map.get(v).parent = u;

                    Vertex vertex1 = graph.vertexMap.get(v);
                    queue.add(v);
                    vertices.add(vertex1);
                    edges.add(edge);

                    System.out.println("queue = " + queue);
                    GraphAnimationState graphAnimationState2 =
                            GraphAnimationState.create()
                                    .setState("Vertex = " + v)
                                    .setInfo("Vertex visited = " + v)
                                    .addVertices(vertices)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(
                                            GraphAnimationStateExtra.create()
                                                    .addQueues(queue));

                    graphSequence.addGraphAnimationState(graphAnimationState2);

                }
                else{
                    System.out.println("queue = " + queue);
                    GraphAnimationState graphAnimationState2 =
                            GraphAnimationState.create()
                                    .setState("Vertex = " + v)
                                    .setInfo("Vertex already visited = " + v)
                                    .addVertices(vertices)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(
                                            GraphAnimationStateExtra.create()
                                            .addQueues(queue));

                    graphSequence.addGraphAnimationState(graphAnimationState2);
                }

                // EDGE CLASSIFICATION
                if (map.get(v).bfsDist == map.get(u).bfsDist + 1) {
//                    System.out.println("TREE EDGE : " + u + " -> " + v);
                    graphTree.addEdge(new EdgePro(edge, TREE));
                }
                else {
                    int src = u;
                    int des = v;

                    while (map.get(src).bfsDist > 0 && map.get(src).bfsDist > map.get(des).bfsDist) {
                        src = map.get(src).parent;
                    }
                    while (map.get(des).bfsDist > 0 && map.get(des).bfsDist > map.get(src).bfsDist) {
                        des = map.get(des).parent;
                    }

//                    System.out.println("[[[[ " + src + " | " + des + " ]]]]");
                    if (src == des) {
//                        System.out.println("BACK EDGE : " + u + " -> " + v);
                        graphTree.addEdge(new EdgePro(edge, BACK));
                    }
                    else {
//                        System.out.println("CROSS EDGE : " + u + " -> " + v);
                        graphTree.addEdge(new EdgePro(edge, CROSS));
                    }
                }
            }
            map.get(u).color = BLACK;

        }

        // ALL DONE
        for(Map.Entry<Integer, VertexCLRS> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

        // add vertices to the graphTree
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

        return graphSequence;
    }

    public GraphSequence connectedComponents(){
        if(graph == null || graph.vertexMap.size() == 0){
            return graphSequence;
        }

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = VertexCLRS.bfsVertexCCCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
        }

        currentID = 0;

        {
            GraphAnimationState graphAnimationState =
                    GraphAnimationState.create()
                            .setState("start")
                            .setInfo("start")
                            .addVertices(vertices)
                            .addEdges(edges);

            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        for(Map.Entry<Integer, VertexCLRS> entry : map.entrySet()){
            System.out.println(entry.getValue());
            if(entry.getValue().connectedID == -1) {
                run2(entry.getValue().data);
            }
        }

        {
            GraphAnimationState graphAnimationState =
                    GraphAnimationState.create()
                            .setState("end")
                            .setInfo("no of connected components = " + currentID)
                            .addVertices(vertices)
                            .addEdges(edges);

            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        return graphSequence;
    }

    private void run2(int s){
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        Vertex vertex = map.get(s).getVertex();
        Vertex vertex3 = new Vertex(currentID, vertex.row, vertex.col);
        vertices.add(vertex3);

        while (queue.size() != 0) {
            int u = queue.pop();
            map.get(u).color = BLACK;
            map.get(u).connectedID = currentID;

            for(Edge edge : graph.edgeListMap.get(u)) {
                int v = edge.des;
                if (map.get(v).color == WHITE) {

                    map.get(v).color = BLACK;
                    map.get(v).connectedID = currentID;
                    map.get(v).parent = u;

                    Vertex vertex1 = graph.vertexMap.get(v);
                    Vertex vertex2 = new Vertex(currentID, vertex1.row, vertex1.col);
                    queue.add(v);
                    vertices.add(vertex2);
                    edges.add(edge);

                    System.out.println("queue = " + queue);
                    GraphAnimationState graphAnimationState2 =
                            GraphAnimationState.create()
                                    .setState("Vertex = " + v)
                                    .setInfo("Vertex visited = " + v)
                                    .addVertices(vertices)
                                    .addEdges(edges);

                    graphSequence.addGraphAnimationState(graphAnimationState2);

                }

            }

        }

        currentID++;

    }

}
