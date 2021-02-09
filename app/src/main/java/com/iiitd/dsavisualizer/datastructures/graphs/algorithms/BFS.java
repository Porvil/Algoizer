package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateShadow;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexBFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BFS {
    Graph graph;
    GraphSequence graphSequence;

    public BFS(Graph graph){
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.BFS);
    }

    public GraphSequence runOld(int s) {

        if(graph == null || graph.vertexMap.size() == 0){
            return graphSequence;
        }

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        {
            GraphAnimationState graphAnimationState = new GraphAnimationState("start");

            GraphAnimationStateShadow graphAnimationStateShadow = new GraphAnimationStateShadow();
            graphAnimationStateShadow.vertices.addAll(vertices);
            graphAnimationStateShadow.edges.addAll(edges);
            graphAnimationState.graphAnimationStateShadow.add(graphAnimationStateShadow);
            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        HashMap<Integer, Boolean> visited = new HashMap<>();
        for(Map.Entry<Integer, ArrayList<Edge>> entry : graph.map.entrySet()){
            visited.put(entry.getKey(), false);
        }

        LinkedList<Integer> queue = new LinkedList<>();

        Vertex vertex = graph.vertexMap.get(s);
        visited.put(s, true);
        queue.add(s);

        vertices.add(vertex);
        GraphAnimationState graphAnimationState = new GraphAnimationState("Visit = " + s);
        GraphAnimationStateShadow graphAnimationStateShadow = new GraphAnimationStateShadow();
        graphAnimationStateShadow.vertices.addAll(vertices);
        graphAnimationState.graphAnimationStateShadow.add(graphAnimationStateShadow);
        graphSequence.addGraphAnimationState(graphAnimationState);


        while (queue.size() != 0) {
            s = queue.pop();
            System.out.println(s + " ");

            ArrayList<Edge> edgeArrayList = graph.map.get(s);
            for(Edge edge : edgeArrayList){
                if(!visited.get(edge.des)){
                    visited.put(edge.des, true);
                    System.out.println("EDGE IN BFS :-    " + s + " --> " + edge.des);
                    queue.add(edge.des);
                    Vertex vertex1 = graph.vertexMap.get(edge.des);

                    vertices.add(vertex1);
                    edges.add(edge);
                    GraphAnimationState graphAnimationState1 = new GraphAnimationState("Visit = " + edge.des);
                    GraphAnimationStateShadow graphAnimationStateShadow1 = new GraphAnimationStateShadow();
                    graphAnimationStateShadow1.vertices.addAll(vertices);
                    graphAnimationStateShadow1.edges.addAll(edges);
                    graphAnimationState1.graphAnimationStateShadow.add(graphAnimationStateShadow1);
                    graphSequence.addGraphAnimationState(graphAnimationState1);
                }
            }

        }

        return graphSequence;
    }

    public GraphSequence run(int s) {

        if(graph == null || graph.vertexMap.size() == 0){
            return graphSequence;
        }

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        LinkedList<Integer> queue = new LinkedList<>();


        {
            GraphAnimationState graphAnimationState = new GraphAnimationState("start");

            GraphAnimationStateShadow graphAnimationStateShadow = new GraphAnimationStateShadow();
            graphAnimationStateShadow.vertices.addAll(vertices);
            graphAnimationStateShadow.edges.addAll(edges);
            graphAnimationStateShadow.queues.addAll(new ArrayList<>(queue));
            graphAnimationState.graphAnimationStateShadow.add(graphAnimationStateShadow);
            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        HashMap<Integer, VertexBFS> map = new HashMap<>();

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexBFS vertexBFS = new VertexBFS(entry.getValue(), "WHITE", Integer.MAX_VALUE, -1);
            map.put(entry.getKey(), vertexBFS);
        }
        queue.add(s);

        Vertex vertex = graph.vertexMap.get(s);
        map.get(s).color = "GRAY";
        map.get(s).dist = 0;
        map.get(s).parent = -1;


        System.out.println("qu = " + queue);

        vertices.add(vertex);
        GraphAnimationState graphAnimationState = new GraphAnimationState("Visit = " + s);
        GraphAnimationStateShadow graphAnimationStateShadow = new GraphAnimationStateShadow();
        graphAnimationStateShadow.vertices.addAll(vertices);
        graphAnimationStateShadow.queues.addAll(new ArrayList<>(queue));
        graphAnimationState.graphAnimationStateShadow.add(graphAnimationStateShadow);
        graphSequence.addGraphAnimationState(graphAnimationState);


        while (queue.size() != 0) {
            int u = queue.pop();
            System.out.println("___________");
            System.out.println("("+u + ")");
            System.out.println(queue);
            System.out.println("___________");

            for(Edge edge : graph.map.get(u)) {
                int v = edge.des;
                if (map.get(v).color.equals("WHITE")) {
                    map.get(v).color = "GRAY";
                    map.get(v).dist = map.get(u).dist + 1;
                    map.get(v).parent = u;

                    Vertex vertex1 = graph.vertexMap.get(v);
                    queue.add(v);
                    vertices.add(vertex1);
                    edges.add(edge);

                    System.out.println("queue = " + queue);
                    GraphAnimationState graphAnimationState1 = new GraphAnimationState("Visit = " + v);
                    GraphAnimationStateShadow graphAnimationStateShadow1 = new GraphAnimationStateShadow();
                    graphAnimationStateShadow1.vertices.addAll(vertices);
                    graphAnimationStateShadow1.edges.addAll(edges);
                    graphAnimationStateShadow1.queues.addAll(new ArrayList<>(queue));
                    graphAnimationState1.graphAnimationStateShadow.add(graphAnimationStateShadow1);
                    graphSequence.addGraphAnimationState(graphAnimationState1);

                }

//                if(map.get(v).dist == map.get(u).dist + 1){
//                    System.out.println("TREE EDGE : " + u + " -> " + v);
//                }
//                if(map.get(v).dist <= map.get(u).dist + 1){
//                    System.out.println("CROSS EDGE : " + u + " -> " + v);
//                }
//                if( 0 <= map.get(v).dist &&  map.get(v).dist <= map.get(u).dist){
//                    System.out.println("BACK EDGE : " + u + " -> " + v);
//                }

            }
            map.get(u).color = "BLACK";

        }

        return graphSequence;
    }

}
