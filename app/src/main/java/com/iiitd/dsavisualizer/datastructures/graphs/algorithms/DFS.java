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

import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.*;
import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class DFS {

    Graph graph;
    GraphSequence graphSequence;
    int time;
    HashMap<Integer, VertexCLRS> map;
    public GraphTree graphTree;
    Stack<Integer> stack;
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;
    int currentID;


    public DFS(Graph graph, GraphAlgorithmType graphAlgorithmType){
        this.graph = graph;
        this.graphSequence = new GraphSequence(graphAlgorithmType);
        this.time = 0;
        this.graphTree = new GraphTree(graph.directed, graph.weighted);
        this.stack = new Stack<>();
        vertices = new ArrayList<>();
        currentID = 0;
        edges = new ArrayList<>();
        this.map = new HashMap<>();
    }

    public GraphSequence run(int s){

        this.map = new HashMap<>();
        this.stack = new Stack<>();

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = VertexCLRS.dfsVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
        }

        time = 0;
        {
            GraphAnimationState graphAnimationState =
                    GraphAnimationState.create()
                            .setState("start")
                            .setInfo("start")
                            .addVertices(vertices)
                            .addEdges(edges)
                            .addGraphAnimationStateExtra(
                                    GraphAnimationStateExtra.create()
                                            .addStacks(stack));

            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        stack.push(s);
        dfs_visit(s);

//        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
//            VertexCLRS vertexCLRS = map.get(entry.getKey());
//            Vertex vertex = graph.vertexMap.get(entry.getKey());
//            if(vertexCLRS.color == WHITE){
//                stack.push(entry.getKey());
//                System.out.println("pushes  = " + entry.getKey());
//                System.out.println("stack  = " + stack);
//
//                dfs_visit(entry.getKey());
//            }
//        }

        // ALL DONE
        for(Map.Entry<Integer, VertexCLRS> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

        System.out.println("______________________________________");
        System.out.println("______________________________________");

        // TOPOLOGICAL SORT HERE
        List<Map.Entry<Integer, VertexCLRS> > list = new LinkedList<>(map.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, VertexCLRS> >() {
            public int compare(Map.Entry<Integer, VertexCLRS> o1,
                               Map.Entry<Integer, VertexCLRS> o2)
            {
                return o2.getValue().finishTime - o1.getValue().finishTime;
            }
        });

        int maxRows = 0;
        for(Map.Entry<Integer, VertexCLRS> entry : list){
            maxRows = entry.getValue().finishTime >= 0 ? Math.max(maxRows, entry.getValue().dfsDepth) : maxRows;
        }

        ArrayList<Integer>[] dfsLayers = new ArrayList[maxRows+1];

        for(int i=0;i<maxRows+1;i++){
            dfsLayers[i] = new ArrayList<>();
        }

        for(Map.Entry<Integer, VertexCLRS> entry : list){
            if(entry.getValue().finishTime >= 0) {
                dfsLayers[entry.getValue().dfsDepth].add(entry.getKey());
            }
        }

        int maxCols = 0;
        for(ArrayList<Integer> arrayList : dfsLayers){
            maxCols = Math.max(maxCols, arrayList.size());
        }

        int[][] coordinates = new int[maxRows+1][maxCols];

        for(int row=0;row<dfsLayers.length;row++) {
            int fill = dfsLayers[row].size();
            int gap = maxCols-fill;
            int colIndex = 0;
            for(int col=0;col<maxCols;col++){
                if(fill >= gap){
                    coordinates[row][col] = dfsLayers[row].get(colIndex);
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

//        Random random = new Random();
//
//        int size = list.size();
//
//        int row = 0;
//        for(Map.Entry<Integer, VertexCLRS> entry : list){
//            System.out.println(entry.getValue());
//            if(entry.getValue().f >= 0) {
//                graphTree.addVertex(entry.getKey(), row, random.nextInt(size));
//                row++;
//            }
//        }
//
//        graphTree.noOfRows = row;
//        graphTree.noOfCols = size;


        return graphSequence;
    }

    private void dfs_visit(int u) {// u = src, v = des
        VertexCLRS vertexCLRS = map.get(u);
        Vertex vertex = graph.vertexMap.get(u);

        vertices.add(vertex);
        GraphAnimationState graphAnimationState =
                GraphAnimationState.create()
                        .setState("Visit = " + vertexCLRS.data)
                        .setInfo("Add source = " + vertexCLRS.data + " to stack")
                        .addVertices(vertices)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addStacks(stack));

        graphSequence.addGraphAnimationState(graphAnimationState);

        System.out.println("dfs_visit called for " + u);
//        Integer pop = stack.pop();
//        System.out.println("Popped = " + pop);
//        System.out.println("stack  = " + stack);
        time++;

        vertexCLRS.startTime = time;
        vertexCLRS.color = GRAY;
        if(vertexCLRS.parent != -1){
            vertexCLRS.dfsDepth = map.get(vertexCLRS.parent).dfsDepth + 1;
        }
        else {
            vertexCLRS.dfsDepth = 0;
        }

        for(Edge edge : graph.edgeListMap.get(u)) {
            int v = edge.des;

            // Non-White
            if(map.get(v).color != WHITE) {
                if (map.get(v).color == BLACK) {
                    if (map.get(u).startTime < map.get(v).startTime) {
                        System.out.println("FORWARD EDGE : " + u + " -> " + v);
                        graphTree.addEdge(new EdgePro(edge, FORWARD));
                    }
                    else {
                        System.out.println("CROSS EDGE : " + u + " -> " + v);
                        graphTree.addEdge(new EdgePro(edge, CROSS));
                    }
                }
                else if (map.get(v).color == GRAY) {
                    System.out.println("BACK EDGE : " + u + " -> " + v);
                    graphTree.addEdge(new EdgePro(edge, BACK));
                }

                GraphAnimationState graphAnimationState2 =
                        GraphAnimationState.create()
                                .setState("Vertex = " + v)
                                .setInfo("Vertex already visited = " + v)
                                .addVertices(vertices)
                                .addEdges(edges)
                                .addGraphAnimationStateExtra(
                                        GraphAnimationStateExtra.create()
                                                .addStacks(stack));

                graphSequence.addGraphAnimationState(graphAnimationState2);
            }
            // White
            else if (map.get(v).color == WHITE) {
                System.out.println("TREE EDGE : " + u + " -> " + v);
                graphTree.addEdge(new EdgePro(edge, TREE));

                map.get(v).parent = u;

                stack.push(v);
                System.out.println("pushes  = " + v);
                System.out.println("stack  = " + stack);

                Vertex vertex1 = graph.vertexMap.get(v);
                vertices.add(vertex1);
                edges.add(edge);

                dfs_visit(v);
            }
        }

        vertexCLRS.color = BLACK;
        time++;
        vertexCLRS.finishTime = time;

        Integer pop = stack.pop();
        System.out.println("Popped = " + pop);
        System.out.println("stack  = " + stack);

        GraphAnimationState graphAnimationState1 =
                GraphAnimationState.create()
                        .setState("Vertex = " + u)
                        .setInfo("Vertex popped from stack = " + u)
                        .addVertices(vertices)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addStacks(stack));

        graphSequence.addGraphAnimationState(graphAnimationState1);
    }

    public GraphSequence completeDFS(){

        this.map = new HashMap<>();
        this.stack = new Stack<>();

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = VertexCLRS.dfsVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
        }

        time = 0;
        {
            GraphAnimationState graphAnimationState =
                    GraphAnimationState.create()
                            .setState("start")
                            .setInfo("start")
                            .addVertices(vertices)
                            .addEdges(edges)
                            .addGraphAnimationStateExtra(
                                    GraphAnimationStateExtra.create()
                                            .addStacks(stack));

            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = map.get(entry.getKey());
            Vertex vertex = graph.vertexMap.get(entry.getKey());
            if(vertexCLRS.color == WHITE){
                stack.push(entry.getKey());
                System.out.println("pushes  = " + entry.getKey());
                System.out.println("stack  = " + stack);

                dfs_visit(entry.getKey());
            }
        }

        // ALL DONE
        for(Map.Entry<Integer, VertexCLRS> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

        System.out.println("______________________________________");
        System.out.println("______________________________________");

        // TOPOLOGICAL SORT HERE
        List<Map.Entry<Integer, VertexCLRS> > list = new LinkedList<>(map.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, VertexCLRS> >() {
            public int compare(Map.Entry<Integer, VertexCLRS> o1,
                               Map.Entry<Integer, VertexCLRS> o2)
            {
                return o2.getValue().finishTime - o1.getValue().finishTime;
            }
        });


        Random random = new Random();

        int size = list.size();

        int row = 0;
        for(Map.Entry<Integer, VertexCLRS> entry : list){
            System.out.println(entry.getValue());
            graphTree.addVertex(entry.getKey(), row, random.nextInt(size));
            row++;
        }

        graphTree.noOfRows = size;
        graphTree.noOfCols = size;


        return graphSequence;
    }

    public GraphSequence connectedComponents(){
        if(graph == null || graph.vertexMap.size() == 0){
            return graphSequence;
        }

//        stack = new Stack<>();

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = VertexCLRS.dfsVertexCCCLRS(entry.getValue());
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
                run3(entry.getValue().data);
                currentID++;
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
        Stack<Integer> stack = new Stack<>();
        stack.add(s);
//        Vertex vertex = map.get(s).getVertex();
//        Vertex vertex3 = new Vertex(currentID, vertex.row, vertex.col);
//        vertices.add(vertex3);

        while (stack.size() != 0) {
            int u = stack.pop();
            map.get(u).color = BLACK;
            map.get(u).connectedID = currentID;

            Vertex vertex1 = graph.vertexMap.get(u);
            Vertex vertex2 = new Vertex(currentID, vertex1.row, vertex1.col);
//            stack.add(v);
            vertices.add(vertex2);
//            ArrayList<Edge> edges = graph.edgeListMap.get(u);
//
//            this.edges.add(edge);

            GraphAnimationState graphAnimationState2 =
                    GraphAnimationState.create()
                            .setState("Vertex = " + u)
                            .setInfo("Vertex visited = " + u)
                            .addVertices(vertices)
                            .addEdges(this.edges);

            graphSequence.addGraphAnimationState(graphAnimationState2);

            for(Edge edge : graph.edgeListMap.get(u)) {
                int v = edge.des;
                if (map.get(v).color == WHITE) {

                    map.get(v).color = BLACK;
                    map.get(v).connectedID = currentID;
                    map.get(v).parent = u;

//                    Vertex vertex1 = graph.vertexMap.get(v);
//                    Vertex vertex2 = new Vertex(currentID, vertex1.row, vertex1.col);
                    stack.add(v);
//                    vertices.add(vertex2);
//                    edges.add(edge);

                    GraphAnimationState graphAnimationState4 =
                            GraphAnimationState.create()
                                    .setState("Vertex = " + v)
                                    .setInfo("Vertex visited = " + v)
                                    .addVertices(vertices)
                                    .addEdges(this.edges);

//                    graphSequence.addGraphAnimationState(graphAnimationState4);

                }

            }

        }

        currentID++;

    }

    private void run3(int u) {// u = src, v = des
        VertexCLRS vertexCLRS = map.get(u);
        System.out.println(vertexCLRS.data +" " + vertexCLRS.connectedID + " | " + currentID);
        if(vertexCLRS.connectedID != -1)
            return;

        Vertex vertex = graph.vertexMap.get(u);
        vertex.data = currentID;

        vertexCLRS.color = BLACK;
        vertexCLRS.connectedID = currentID;

        vertices.add(vertex);
        GraphAnimationState graphAnimationState =
                GraphAnimationState.create()
                        .setState("Visit = " + vertexCLRS.data)
                        .setInfo("Add source = " + vertexCLRS.data + " to stack")
                        .addVertices(vertices)
                        .addEdges(edges);

        graphSequence.addGraphAnimationState(graphAnimationState);

        for (Edge edge : graph.edgeListMap.get(u)) {
            int v = edge.des;

            // Non-White
            if (map.get(v).color != WHITE) {

                GraphAnimationState graphAnimationState2 =
                        GraphAnimationState.create()
                                .setState("Vertex = " + v)
                                .setInfo("Vertex already visited = " + v)
                                .addVertices(vertices)
                                .addEdges(edges);

//                graphSequence.addGraphAnimationState(graphAnimationState2);
            }
            // White
            else if (map.get(v).color == WHITE) {
                graphTree.addEdge(new EdgePro(edge, TREE));

//                map.get(v).parent = u;

//                stack.push(v);

                Vertex vertex1 = graph.vertexMap.get(v);
                vertices.add(vertex1);
                edges.add(edge);

                run3(v);
            }
        }


//
//        GraphAnimationState graphAnimationState1 =
//                GraphAnimationState.create()
//                        .setState("Vertex = " + u)
//                        .setInfo("Vertex popped from stack = " + u)
//                        .addVertices(vertices)
//                        .addEdges(edges)
//                        .addGraphAnimationStateExtra(
//                                GraphAnimationStateExtra.create()
//                                        .addStacks(stack));
//
//        graphSequence.addGraphAnimationState(graphAnimationState1);
    }

}
