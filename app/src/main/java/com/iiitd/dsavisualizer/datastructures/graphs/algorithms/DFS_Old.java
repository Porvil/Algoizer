package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.EdgePro;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphTree;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexCLRS;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.BACK;
import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.CROSS;
import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.FORWARD;
import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.TREE;
import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.BLACK;
import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.GRAY;
import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.WHITE;

public class DFS_Old {

    Graph graph;
    GraphSequence graphSequence;
    int time;
    HashMap<Integer, VertexCLRS> map;
    public GraphTree graphTree;
    Stack<Integer> stack;

    public DFS_Old(Graph graph){
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.DFS);
        this.time = 0;
        this.graphTree = new GraphTree(graph.directed, graph.weighted);
        this.stack = new Stack<>();
    }

    public GraphSequence dfs(){

        map = new HashMap<>();

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = new VertexCLRS(entry.getValue(), WHITE, Integer.MAX_VALUE, -1, -1);
            map.put(entry.getKey(), vertexCLRS);
        }

        time = 0;

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = map.get(entry.getKey());
            if(vertexCLRS.color == WHITE){
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
                return o2.getValue().f - o1.getValue().f;
            }
        });


        Random random = new Random();

        int size = list.size();

        int row = 0;
        for(Map.Entry<Integer, VertexCLRS> entry : list){
            System.out.println(entry.getValue());
//            graphTree.vertexMap.put(entry.getKey(), Pair.create(row, 0));
//            graphTree.vertexMap.put(entry.getKey(), Pair.create(row, random.nextInt(5)));
            graphTree.addVertex(entry.getKey(), row, random.nextInt(size));
            row++;
        }

        graphTree.noOfRows = size;
        graphTree.noOfCols = size;


        return graphSequence;
    }

    private void dfs_visit(int u) {// u = src, v = des
        time++;

        VertexCLRS vertexCLRS = map.get(u);
        vertexCLRS.dist = time;
        vertexCLRS.color = GRAY;

        for(Edge edge : graph.edgeListMap.get(u)) {
            int v = edge.des;

            if(map.get(v).color == BLACK){
                if (map.get(u).dist < map.get(v).dist) {
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
            else if (map.get(v).color == WHITE) {
                System.out.println("TREE EDGE : " + u + " -> " + v);
                graphTree.addEdge(new EdgePro(edge, TREE));

                map.get(v).parent = u;
                dfs_visit(v);
            }
        }

        vertexCLRS.color = BLACK;
        time++;
        vertexCLRS.f = time;
    }

    public GraphSequence run(){

        Stack<Integer> stack = new Stack<>();
        map = new HashMap<>();

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = new VertexCLRS(entry.getValue(), WHITE, Integer.MAX_VALUE, -1, -1);
            map.put(entry.getKey(), vertexCLRS);
        }

        time = 0;

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = map.get(entry.getKey());

            if(vertexCLRS.color == WHITE){

                vertexCLRS.color = GRAY;
                time++;
                vertexCLRS.dist = time;

                stack.push(vertexCLRS.data);

                while (!stack.empty()){
                    Integer u = stack.pop();
                    VertexCLRS vertexCLRS1 = map.get(u);

                    for(Edge edge : graph.edgeListMap.get(u)) {
                        int v = edge.des;

                        if(map.get(v).color == BLACK){
                            if (map.get(u).dist < map.get(v).dist) {
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
                        else if (map.get(v).color == WHITE) {
                            System.out.println("TREE EDGE : " + u + " -> " + v);
                            graphTree.addEdge(new EdgePro(edge, TREE));

                            map.get(v).color = GRAY;
                            time++;
                            map.get(v).dist = time;
                            map.get(v).parent = u;

//                            v.color = GRAY
//                            time ← time + 1
//                            v.d ← time
//                            v.π ← u

                            stack.push(v);
                        }
                    }

                    vertexCLRS.color = BLACK;
                    time++;
                    vertexCLRS.f = time;
                }
            }
        }

        // ALL DONE
        for(Map.Entry<Integer, VertexCLRS> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

        // ALL DONE HERE
        System.out.println("______________________________________");
        System.out.println("______________________________________");

        // TOPOLOGICAL SORT HERE
        List<Map.Entry<Integer, VertexCLRS> > list = new LinkedList<>(map.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, VertexCLRS> >() {
            public int compare(Map.Entry<Integer, VertexCLRS> o1,
                               Map.Entry<Integer, VertexCLRS> o2)
            {
                return o2.getValue().f - o1.getValue().f;
            }
        });


        Random random = new Random();

        int size = list.size();

        int row = 0;
        for(Map.Entry<Integer, VertexCLRS> entry : list){
            System.out.println(entry.getValue());
//            graphTree.vertexMap.put(entry.getKey(), Pair.create(row, 0));
//            graphTree.vertexMap.put(entry.getKey(), Pair.create(row, random.nextInt(5)));
            graphTree.addVertex(entry.getKey(), row, random.nextInt(size));
            row++;
        }

        graphTree.noOfRows = size;
        graphTree.noOfCols = size;


        return graphSequence;
    }

}
