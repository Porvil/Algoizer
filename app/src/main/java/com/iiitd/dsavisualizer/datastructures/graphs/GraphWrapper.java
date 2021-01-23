package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.util.Pair;
import android.view.MotionEvent;

import com.iiitd.dsavisualizer.runapp.others.CustomCanvas;

import java.util.ArrayList;

public class GraphWrapper {
    public boolean directed;
    public boolean weighted;
    public Board board;
    public Graph graph;

    public GraphWrapper(Context context, CustomCanvas customCanvas, boolean directed, boolean weighted) {
        this.directed = directed;
        this.weighted = weighted;
        graph = new Graph(directed, weighted);
        board = new Board(context, customCanvas);
    }

    public boolean addVertex(MotionEvent motionEvent, int nodeNumber){
        float x = (motionEvent.getX() / board.xSize);
        float y =  (motionEvent.getY() / board.ySize);

        int row = (int) y;
        int col = (int) x;

        return addVertex(row, col, nodeNumber);
    }

    public boolean addVertex(MotionEvent motionEvent){
        float x = (motionEvent.getX() / board.xSize);
        float y =  (motionEvent.getY() / board.ySize);

        int row = (int) y;
        int col = (int) x;

        int newVertexNumber = graph.getNewVertexNumber();
        return addVertex(row, col, newVertexNumber);
    }

    private boolean addVertex(int row, int col, int nodeNumber){
        if(!board.getState(row, col)){
            graph.addVertex(nodeNumber, row, col);
            board.addVertex(row, col, nodeNumber);
            update();
            return true;
        }

        return false;
    }

    public boolean removeVertex(MotionEvent motionEvent){

        float x = (motionEvent.getX() / board.xSize);
        float y =  (motionEvent.getY() / board.ySize);

        int row = (int) y;
        int col = (int) x;

        if(board.getState(row, col)){
            int nodeNumber = board.data[row][col].data;
            graph.removeVertex(nodeNumber, row, col);
            board.removeVertex(row, col);
            update();
            return true;
        }

        return false;
    }

    public boolean addEdge(int src, int des){
        return addEdge(src, des, 1);
    }

    public boolean addEdge(int src, int des, int weight){

        if(!graph.directed){
            graph.addEdge(des, src, weight);//reverse edge
        }

        graph.addEdge(src, des, weight);
        update();

        return true;
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

    public boolean customInput(ArrayList<Integer> vertices, ArrayList<Edge> edges){

        reset();

        int vertexCount = vertices.size();
        if(vertexCount > board.maxVertices){
            return false;
        }

        for(int i : vertices){
            Pair<Integer, Integer> randomAvailableNode = board.getRandomAvailableNode();
            if(randomAvailableNode != null) {
                addVertex(randomAvailableNode.first, randomAvailableNode.second, i);
            }
            else{
                System.out.println("NO SPACE IN GRAPH");
            }
        }

        for(Edge edge : edges){
            addEdge(edge.src, edge.des, (int) edge.weight);
        }

        update();

        //check distances
        double score = board.score(graph);
        System.out.println("Score = " + score);



        return true;

    }

    public boolean customInput1(ArrayList<Vertex> vertices, ArrayList<Edge> edges){

        reset();

        // check if it is in bound -> ok
        // else check if possible to be minimized -> ok
        // else check if count is in bound then randomize graph
        // else error not enough space

        minimizeGraph(vertices);


        int vertexCount = vertices.size();
        if(vertexCount > board.maxVertices){
            return false;
        }

        for(Vertex vertex : vertices){
            addVertex(vertex.row, vertex.col, vertex.data);
        }

        for(Edge edge : edges){
            addEdge(edge.src, edge.des, (int) edge.weight);
        }

        update();

        //check distances
        double score = board.score(graph);
        System.out.println("Score = " + score);


        return true;

    }

    public void reset(){
        this.graph = new Graph(directed, weighted);
        board.reset(graph);
    }

    public void minimizeGraph(ArrayList<Vertex> data){
        int xCount = board.xCount;
        int yCount = board.yCount;

//        data.add(Pair.create(0, 0));
//        data.add(Pair.create(0, 3));
//        data.add(Pair.create(2, 0));
//        data.add(Pair.create(2, 4));
//        data.add(Pair.create(4, 3));

        int minX = xCount+1;
        int maxX = -1;
        int minY = yCount+1;
        int maxY = -1;

        for(Vertex vertex : data){
            minX = minX < vertex.col ? minX : vertex.col;
            maxX = maxX > vertex.col ? maxX : vertex.col;
            minY = minY < vertex.row ? minY : vertex.row;
            maxY = maxY > vertex.row ? maxY : vertex.row;
        }

        System.out.println(minX + " " + maxX);
        System.out.println(minY + " " + maxY);

        // compress
        int[] row = new int[yCount];
        int[] col = new int[xCount];
        for(Vertex pair : data){
            row[pair.row] = 1;
            col[pair.col] = 1;
        }

        //rows
        {
            int gap = 0;
            for (int i = yCount - 1; i >= 0; i--) {
                if (row[i] == 0) {
                    gap++;
                } else {
                    if (gap > 0) {
                        for (int c = data.size() - 1; c >= 0; c--) {
                            if (data.get(c).row > i)
                                data.get(c).row -= gap;
                        }
                    }

                    gap = 0;
                }
            }
        }

        //cols
        {
            int gap = 0;
            for (int i = xCount - 1; i >= 0; i--) {
                if (col[i] == 0) {
                    gap++;
                } else {
                    if (gap > 0) {
                        for (int c = data.size() - 1; c >= 0; c--) {
                            if (data.get(c).col > i)
                                data.get(c).col -= gap;
                        }
                    }

                    gap = 0;
                }
            }
        }

    }

}
