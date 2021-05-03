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

import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.*;
import static com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateType.NONE;
import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

// DFS
public class DFS {
    Graph graph;
    GraphSequence graphSequence;
    int time;
    HashMap<Integer, VertexCLRS> map;
    public GraphTree graphTree;
    Stack<Integer> stack;
    HashMap<Integer, Vertex> verticesState;
    ArrayList<Edge> edges;
    int currentID;

    public DFS(Graph graph, GraphAlgorithmType graphAlgorithmType){
        this.graph = graph;
        this.graphSequence = new GraphSequence(graphAlgorithmType);
        this.graphTree = new GraphTree(graph.directed, graph.weighted);
        this.map = new HashMap<>();
        this.verticesState = new HashMap<>();
        this.edges = new ArrayList<>();
        this.stack = new Stack<>();
        this.time = 0;
        this.currentID = 0;
    }

    public GraphSequence dfs(int source){
        int size = graph.noOfVertices;

        if (size < 1)
            return graphSequence;

        // Add all vertices
        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            VertexCLRS vertexCLRS = VertexCLRS.dfsVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
            verticesState.put(entry.getKey(), new Vertex(entry.getValue(), NONE));
        }

        // Add all edges
        for(Edge edge : graph.getAllEdges()) {
            edges.add(new Edge(edge, NONE));
        }

        time = 0;

        // Start Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("dfs(" + source + ")")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addStacks(stack)));

        Vertex vertex = verticesState.get(source);
        stack.push(source);
        vertex.setToHighlight();

        // Push to stack Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("source vertex(" + source + ") pushed to stack")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addStacks(stack)));

        vertex.setToNormal();

        dfsVisit(source);

        // End Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("dfs() completed")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addStacks(stack)));

        // ALL DONE
        for(Map.Entry<Integer, VertexCLRS> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

        // --------------------- START OF GRAPH TREE ---------------------
        List<Map.Entry<Integer, VertexCLRS> > list = new LinkedList<>(map.entrySet());

        // Sort the list by their finish times
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
        // --------------------- END OF GRAPH TREE ---------------------

        return graphSequence;
    }

    private void dfsVisit(int u) {// u = src, v = des
        VertexCLRS srcVertexCLRS = map.get(u);
        Vertex srcVertex = verticesState.get(u);
        srcVertex.setToHighlight();

        // Visit Node
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("vertex (" + u + ") popped from stack, check all edges")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addStacks(stack)));

        time++;
        srcVertexCLRS.startTime = time;
        srcVertexCLRS.color = GRAY;

        if(srcVertexCLRS.parent != -1){
            srcVertexCLRS.dfsDepth = map.get(srcVertexCLRS.parent).dfsDepth + 1;
        }
        else {
            srcVertexCLRS.dfsDepth = 0;
        }

        for(Edge curEdge : graph.edgeListMap.get(u)) {
            int v = curEdge.des;

            Vertex desVertex = verticesState.get(curEdge.des);
            VertexCLRS endVertexCLRS = map.get(curEdge.des);
            Edge edge = edges.get(edges.indexOf(curEdge));
            GraphAnimationStateType edgeGAST = edge.graphAnimationStateType;
            GraphAnimationStateType desVertexGAST = desVertex.graphAnimationStateType;
            edge.setToHighlight();
            desVertex.setToHighlight();

            // Non-White
            if(endVertexCLRS.color != WHITE) {
                if (endVertexCLRS.color == BLACK) {
                    if (srcVertexCLRS.startTime < endVertexCLRS.startTime) {
                        graphTree.addEdge(new EdgePro(curEdge, FORWARD));
                    }
                    else {
                        graphTree.addEdge(new EdgePro(curEdge, CROSS));
                    }
                }
                else if (endVertexCLRS.color == GRAY) {
                    graphTree.addEdge(new EdgePro(curEdge, BACK));
                }

                graphSequence.addGraphAnimationState(
                        GraphAnimationState.create()
                                .setInfo("vertex (" + v + ") already visited, continue")
                                .setVerticesState(verticesState)
                                .addEdges(edges)
                                .addGraphAnimationStateExtra(
                                        GraphAnimationStateExtra.create()
                                                .addStacks(stack)));

                edge.setGAST(edgeGAST);
                desVertex.setGAST(desVertexGAST);
            }
            // White
            else if (endVertexCLRS.color == WHITE) {
                graphTree.addEdge(new EdgePro(curEdge, TREE));
                endVertexCLRS.parent = u;
                stack.push(v);

                graphSequence.addGraphAnimationState(
                        GraphAnimationState.create()
                                .setInfo("vertex (" + v + ") not visited" + "\n" + "added to stack")
                                .setVerticesState(verticesState)
                                .addEdges(edges)
                                .addGraphAnimationStateExtra(
                                        GraphAnimationStateExtra.create()
                                                .addStacks(stack)));

                desVertex.setGAST(desVertexGAST);

                dfsVisit(v);
            }
        }

        srcVertexCLRS.color = BLACK;
        time++;
        srcVertexCLRS.finishTime = time;

        srcVertex.setToDone();
        stack.pop();

        int parent = srcVertexCLRS.parent;
        int self = srcVertexCLRS.data;
        if(parent != -1){
            Edge parentEdge = edges.get(edges.indexOf(graph.getEdge(parent, self)));
            parentEdge.setToDone();
        }

        // Visit Node
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("all edges of vertex (" + u + ") visited" +
                                "\n" + "vertex (" + u + ") popped from stack")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addStacks(stack)));
    }

    public GraphSequence dfsCC(){
        int size = graph.noOfVertices;

        if (size < 1)
            return graphSequence;

        // Add all vertices
        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            VertexCLRS vertexCLRS = VertexCLRS.dfsVertexCCCLRS(entry.getValue());
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
                        .setInfo("dfsConnectedComponents()")
                        .setVerticesState(verticesState)
                        .addEdges(edges));

        for(Map.Entry<Integer, VertexCLRS> entry : map.entrySet()){
            if(entry.getValue().connectedID == -1) {
                dfsCCVisit(entry.getValue().data);
                currentID++;
            }
        }

        // End Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("dfsConnectedComponents() completed"
                                + "\n" + "no. of connected components = " + currentID)
                        .setVerticesState(verticesState)
                        .addEdges(edges));

        return graphSequence;
    }

    private void dfsCCVisit(int u) {// u = src, v = des
        VertexCLRS srcVertexCLRS = map.get(u);
        Vertex srcVertex = verticesState.get(u);
        srcVertex.setToHighlight();

        if(srcVertexCLRS.connectedID != -1)
            return;

        srcVertex.data = currentID;

        srcVertexCLRS.color = BLACK;
        srcVertexCLRS.connectedID = currentID;

        // Visit Node
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("vertex (" + u + ") popped from stack, check all edges")
                        .setVerticesState(verticesState)
                        .addEdges(edges));


        for (Edge curEdge : graph.edgeListMap.get(u)) {
            int v = curEdge.des;
            Vertex desVertex = verticesState.get(curEdge.des);
            VertexCLRS endVertexCLRS = map.get(curEdge.des);
            Edge edge = edges.get(edges.indexOf(curEdge));
            GraphAnimationStateType edgeGAST = edge.graphAnimationStateType;
            GraphAnimationStateType desVertexGAST = desVertex.graphAnimationStateType;
            edge.setToHighlight();
            desVertex.setToHighlight();

            // Non-White
            if (map.get(v).color != WHITE) {
                graphSequence.addGraphAnimationState(
                        GraphAnimationState.create()
                                .setInfo("vertex (" + v + ") already visited, continue")
                                .setVerticesState(verticesState)
                                .addEdges(edges));

                edge.setGAST(edgeGAST);
                desVertex.setGAST(desVertexGAST);
            }
            // White
            else if (map.get(v).color == WHITE) {
                graphTree.addEdge(new EdgePro(edge, TREE));
                endVertexCLRS.parent = u;
                graphSequence.addGraphAnimationState(
                        GraphAnimationState.create()
                                .setInfo("vertex (" + v + ") not visited" + "\n" + "added to stack")
                                .setVerticesState(verticesState)
                                .addEdges(edges));

                desVertex.setGAST(desVertexGAST);

                dfsCCVisit(v);
            }
        }

        int parent = srcVertexCLRS.parent;
        int self = u;
        if(parent != -1){
            Edge parentEdge = edges.get(edges.indexOf(graph.getEdge(parent, self)));
            parentEdge.setToDone();
        }

        srcVertex.setToDone();

        // Visit Node
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("all edges of vertex (" + u + ") visited" +
                                "\n" + "vertex (" + u + ") popped from stack")
                        .setVerticesState(verticesState)
                        .addEdges(edges));
    }

}