package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.util.Pair;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

// GraphWrapper is the main class which wraps the Board [ frontend ] and Graph [ backend ] together
public class GraphWrapper {
    public boolean directed;
    public boolean weighted;
    public boolean isLargeGraph;
    public Board board;
    public Graph graph;

    private final int MAX_BOUNDS = 1000;

    public GraphWrapper(Context context, CustomCanvas customCanvas, boolean directed, boolean weighted, boolean isLargeGraph) {
        this.directed = directed;
        this.weighted = weighted;
        this.isLargeGraph = isLargeGraph;
        graph = new Graph(directed, weighted);
        board = new Board(context, customCanvas, isLargeGraph);
    }

    // Returns true if the passed string is a graph input
    public static boolean isGraphInput(String customGraphString) {
        String[] ss = customGraphString.split("\\n");
        boolean error = false;

        if(customGraphString.length() == 0){
            error = true;
        }
        else {
            try {
                for (String line : ss) {
                    String[] chars = line.split("\\s+");

                    switch (chars[0].toUpperCase()) {
                        case "D":
                        case "W":
                        case "VC":
                        case "VA":
                        case "V":
                        case "E":
                            break;
                        default:
                            error = true;
                            break;
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Exception while parsing graph data");
                error = true;
            }
        }

        if(error){
            System.out.println("BAD INPUT or error");
            return false;
        }

        return true;
    }

    public void changeDirected(boolean directed){
        this.directed = directed;
        graph = new Graph(directed, weighted);
        board.reset(graph);
    }

    public void changeWeighted(boolean weighted){
        this.weighted = weighted;
        graph = new Graph(directed, weighted);
        board.reset(graph);
    }

    public void changeDirectedWeighted(boolean directed, boolean weighted){
        this.directed = directed;
        this.weighted = weighted;
        graph = new Graph(directed, weighted);
        board.reset(graph);
    }

    public int getNoOfNodes(){
        return graph.noOfVertices;
    }

    public boolean addVertex(MotionEvent motionEvent){
        TouchData touchData = board.getTouchData(motionEvent);
        return addVertex(touchData);
    }

    public boolean addVertex(TouchData touchData){
        if(touchData.isElement || touchData.isExtendedElement){
            int nodeNumber = graph.getNewVertexNumber();
            int row = touchData.row;
            int col = touchData.col;

            if(!board.getState(row, col)){
                boolean addVertex = graph.addVertex(nodeNumber, row, col);
                if(addVertex){
                    board.addVertex(row, col, nodeNumber);
                    update();
                    return true;
                }

            }

            return false;
        }

        return  false;
    }

    public boolean addVertex(TouchData touchData, int nodeNumber){
        if(touchData.isElement || touchData.isExtendedElement){
            int row = touchData.row;
            int col = touchData.col;

            if(!board.getState(row, col)){
                boolean addVertex = graph.addVertex(nodeNumber, row, col);
                if(addVertex){
                    board.addVertex(row, col, nodeNumber);
                    update();
                    return true;
                }

            }

            return false;
        }

        return  false;
    }

    // Used by CustomInput in Graph
    private boolean addVertex(int row, int col, int nodeNumber){
        if(row<0 || row>= board.yCount){
            return false;
        }
        if(col<0 || col>= board.xCount){
            return false;
        }

        TouchData touchData = new TouchData();
        touchData.row = row;
        touchData.col = col;
        touchData.isElement = true;
        touchData.isExtendedElement = false;
        touchData.x = col;
        touchData.y = row;

        return addVertex(touchData, nodeNumber);
    }

    public boolean removeVertex(MotionEvent motionEvent){
        TouchData touchData = board.getTouchData(motionEvent);
        return removeVertex(touchData);
    }

    public boolean removeVertex(TouchData touchData) {
        if(touchData.isElement || touchData.isExtendedElement){
            int row = touchData.row;
            int col = touchData.col;
            return removeVertex(row, col);
        }

        return false;
    }

    public boolean removeVertex(int row, int col){
        if(board.getState(row, col)){
            int nodeNumber = board.boardElements[row][col].value;
            graph.removeVertex(nodeNumber);
            board.removeVertex(row, col);
            update();
            return true;
        }

        return false;
    }

    public boolean addEdge(int src, int des){
        return addEdge(src, des, 1, false);
    }

    public boolean addEdge(int src, int des, int weight, boolean update){
        if(!graph.checkContainsEdge(src, des)){
            if(graph.checkContainsVertices(src, des)){

                if(!graph.directed){
                    graph.addEdge(des, src, weight, false);//reverse edge
                }

                graph.addEdge(src, des, weight, true);

                update();

                return true;
            }
        }
        else if(update){
            if(!graph.directed){
                graph.removeEdge(des, src);
                graph.addEdge(des, src, weight, false);//reverse edge
            }

            graph.removeEdge(src, des);
            graph.addEdge(src, des, weight, true);

            update();

            return true;
        }

        return false;
    }

    public boolean removeEdge(int src, int des){
        if(!graph.directed){
            graph.removeEdge(des, src);//reverse edge
        }

        graph.removeEdge(src, des);
        update();

        return true;
    }

    public void update(){
        board.update(graph);
    }

    // should be called only after "changeDirectedWeighted()" method
    public boolean customInput(ArrayList<Vertex> vertices, ArrayList<Edge> edges){
        // Reset the graph
        reset();

        int xCount = board.xCount;
        int yCount = board.yCount;

        int vertexCount = vertices.size();
        if(vertexCount > board.maxVertices){
            return false;
        }

        for(Vertex vertex : vertices){
            if(vertex.row == -1 || vertex.col == -1) {
                Pair<Integer, Integer> randomAvailableNode = board.getRandomAvailableNode();
                vertex.row = randomAvailableNode.first;
                vertex.col = randomAvailableNode.second;
            }
            else if(vertex.row > yCount || vertex.col > xCount){
                Pair<Integer, Integer> randomAvailableNode = board.getRandomAvailableNode();
                vertex.row = randomAvailableNode.first;
                vertex.col = randomAvailableNode.second;
            }

            addVertex(vertex.row, vertex.col, vertex.data);
        }

        for(Edge edge : edges){
            addEdge(edge.src, edge.des, edge.weight, false);
        }

        update();

        return true;
    }

    // Resets the graph
    public void reset(){
        this.graph = new Graph(directed, weighted);
        board.reset(graph);
    }

    // Minimizes the graph
    public void minimizeGraph(ArrayList<Vertex> vertices){
        int maxX = -1;
        int maxY = -1;
        int gap = 0;

        for(Vertex vertex : vertices){
            maxX = Math.max(maxX, vertex.col);
            maxY = Math.max(maxY, vertex.row);
        }

        // Compress
        int[] row = new int[maxY+1];
        int[] col = new int[maxX+1];

        for(Vertex pair : vertices){
            if(pair.row != -1 && pair.col != -1) {
                row[pair.row] = 1;
                col[pair.col] = 1;
            }
        }

        // Compress Rows
        gap = 0;
        for (int i = row.length-1; i >= 0; i--) {
            if (row[i] == 0) {
                gap++;
            }
            else {
                if(gap > 0) {
                    for(Vertex vertex : vertices){
                        if(vertex.row != -1 && vertex.row > i) {
                            vertex.row -= gap;
                        }
                    }
                }
                gap = 0;
            }
        }

        // Compress Cols
        gap = 0;

        for (int i = col.length - 1; i >= 0; i--) {
            if (col[i] == 0) {
                gap++;
            }
            else {
                if(gap > 0) {
                    for(Vertex vertex : vertices){
                        if(vertex.col != -1 && vertex.col > i){
                            vertex.col -= gap;
                        }
                    }
                }
                gap = 0;
            }
        }

    }

    // Checks if all vertices can be placed on the graph or not
    public boolean checkBounds(ArrayList<Vertex> data){
        if(data.size() == 0)
            return true;

        int xCount = board.xCount;
        int yCount = board.yCount;

        int maxX = -1;
        int maxY = -1;

        boolean boundCheckedOnce = false;

        for(Vertex vertex : data){
            if(vertex.row != -1 && vertex.col != -1) {
                maxX = Math.max(maxX, vertex.col);
                maxY = Math.max(maxY, vertex.row);

                boundCheckedOnce = true;
            }
        }

        if(boundCheckedOnce)
            return maxX < xCount && maxY < yCount;
        else
            return true;

    }

    // Returns false if absolute location of vertices is higher than MAX_BOUNDS
    public boolean checkPerformanceBounds(ArrayList<Vertex> data){
        if(data.size() == 0)
            return false;

        int maxX = -1;
        int maxY = -1;


        for(Vertex vertex : data){
            if(vertex.row != -1 && vertex.col != -1) {
                maxX = Math.max(maxX, vertex.col);
                maxY = Math.max(maxY, vertex.row);
            }
        }

        return !(maxX < MAX_BOUNDS && maxY < MAX_BOUNDS);
    }

    // Checks whether if graph can be minimized or not
    public boolean checkIfMinimizable(ArrayList<Vertex> data) {
        if (data.size() == 0)
            return true;

        HashSet<Integer> rows = new HashSet<>();
        HashSet<Integer> cols = new HashSet<>();


        int xCount = board.xCount;
        int yCount = board.yCount;

        for (Vertex vertex : data) {
            if(vertex.row != -1 && vertex.col != -1) {
                rows.add(vertex.row);
                cols.add(vertex.col);
            }
        }

        //  yCount = rows, xCount = cols
        return rows.size() < yCount && cols.size() < xCount;
    }

    // Returns a serialized String for the current Graph
    public String getSerializableGraphString() {
        StringBuilder stringBuilder = new StringBuilder();
        String newLine = "\n";

        // Graph Type
        stringBuilder.append("D ")
                .append(directed ? "1" : "0")
                .append(newLine)
                .append("W ")
                .append(weighted ? "1" : "0")
                .append(newLine);

        // No of Vertices
        int noOfVertices = graph.noOfVertices;
        stringBuilder.append("VC ")
                .append(noOfVertices)
                .append(newLine);

        // Vertices
        for (Map.Entry<Integer, Vertex> vertexEntry : graph.vertexMap.entrySet()) {
            stringBuilder.append("VA ")
                    .append(vertexEntry.getKey())
                    .append(" ")
                    .append(vertexEntry.getValue().row)
                    .append(" ")
                    .append(vertexEntry.getValue().col)
                    .append(newLine);
        }

        // Edges
        for (Map.Entry<Integer, ArrayList<Edge>> entry : graph.edgeListMap.entrySet()) {
            for (Edge i : entry.getValue()) {
                stringBuilder.append("E ")
                        .append(i.src)
                        .append(" ")
                        .append(i.des)
                        .append(" ")
                        .append(i.weight)
                        .append(newLine);
            }
        }

        return stringBuilder.toString();
    }

}