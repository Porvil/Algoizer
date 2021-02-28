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

    public DFS(Graph graph){
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.DFS);
        this.time = 0;
        this.graphTree = new GraphTree(graph.directed, graph.weighted);
        this.stack = new Stack<>();
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public GraphSequence run(int s){

        this.map = new HashMap<>();
        this.stack = new Stack<>();

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = new VertexCLRS(entry.getValue(), WHITE, Integer.MAX_VALUE, -1, -1);
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
                return o2.getValue().f - o1.getValue().f;
            }
        });


        Random random = new Random();

        int size = list.size();

        int row = 0;
        for(Map.Entry<Integer, VertexCLRS> entry : list){
            System.out.println(entry.getValue());
            if(entry.getValue().f >= 0) {
                graphTree.addVertex(entry.getKey(), row, random.nextInt(size));
                row++;
            }
        }

        graphTree.noOfRows = row;
        graphTree.noOfCols = size;


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

        vertexCLRS.dist = time;
        vertexCLRS.color = GRAY;

        for(Edge edge : graph.edgeListMap.get(u)) {
            int v = edge.des;

            // Non-White
            if(map.get(v).color != WHITE) {
                if (map.get(v).color == BLACK) {
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
        vertexCLRS.f = time;

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
            VertexCLRS vertexCLRS = new VertexCLRS(entry.getValue(), WHITE, Integer.MAX_VALUE, -1, -1);
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
                return o2.getValue().f - o1.getValue().f;
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

}
