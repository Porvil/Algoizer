package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateShadow;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexCLRS;

import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.*;

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

        HashMap<Integer, VertexCLRS> map = new HashMap<>();

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = new VertexCLRS(entry.getValue(), WHITE, Integer.MAX_VALUE, -1);
            map.put(entry.getKey(), vertexCLRS);
        }
        queue.add(s);

        Vertex vertex = graph.vertexMap.get(s);
        map.get(s).color = GRAY;
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

            for(Edge edge : graph.edgeListMap.get(u)) {
                int v = edge.des;
                if (map.get(v).color == WHITE) {
                    map.get(v).color = GRAY;
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

                if(map.get(v).dist == map.get(u).dist + 1){
                    System.out.println("TREE EDGE : " + u + " -> " + v);
                }
                else{
                    int src = u;
                    int des = v;

                    while (map.get(src).dist > 0 && map.get(src).dist > map.get(des).dist){
                        src = map.get(src).parent;
                    }
                    while (map.get(des).dist > 0 && map.get(des).dist > map.get(src).dist){
                        des = map.get(des).parent;
                    }

                    System.out.println("[[[[ " + src + " | " + des + " ]]]]");
                    if(src == des){
                        System.out.println("BACK EDGE : " + u + " -> " + v);
                    }
                    else{
                        System.out.println("CROSS EDGE : " + u + " -> " + v);
                    }

                }
//                if(map.get(v).dist == map.get(u).dist + 1){
//                    System.out.println("TREE EDGE : " + u + " -> " + v);
//                }
//                else if( 0 <= map.get(v).dist &&  map.get(v).dist <= map.get(u).dist){
//                    System.out.println("BACK EDGE : " + u + " -> " + v);
//                }
//                else if(map.get(v).dist <= map.get(u).dist + 1){
//                    System.out.println("CROSS EDGE : " + u + " -> " + v);
//                }


            }
            map.get(u).color = BLACK;

        }

        return graphSequence;
    }

}
