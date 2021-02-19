package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.EdgePro;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateShadow;
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

    public BFS(Graph graph){
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.BFS);
        this.graphTree = new GraphTree(graph.directed, graph.weighted);
    }

    public GraphSequence run(int s) {

        if(graph == null || graph.vertexMap.size() == 0){
            return graphSequence;
        }

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        LinkedList<Integer> queue = new LinkedList<>();


        {
            GraphAnimationState graphAnimationState =
                    GraphAnimationState.create()
                            .setState("start")
                            .setInfo("start")
                            .addGraphAnimationStateShadow(
                                    GraphAnimationStateShadow.create()
                                            .addVertices(vertices)
                                            .addEdges(edges)
                                            .addQueues(queue)
                            );

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

//        graphTree.addVertex(s, 0, 0);
        vertices.add(vertex);
        GraphAnimationState graphAnimationState =
                GraphAnimationState.create()
                        .setState("Visit = " + s)
                        .setInfo("Add source = " + s + " to queue")
                        .addGraphAnimationStateShadow(
                                GraphAnimationStateShadow.create()
                                        .addVertices(vertices)
                                        .addEdges(edges)
                                        .addQueues(queue)
                        );

        graphSequence.addGraphAnimationState(graphAnimationState);

        while (queue.size() != 0) {
            int u = queue.pop();

            GraphAnimationState graphAnimationState2 =
                    GraphAnimationState.create()
                            .setState("Vertex = " + u)
                            .setInfo("Vertex popped from queue = " + u)
                            .addGraphAnimationStateShadow(
                                    GraphAnimationStateShadow.create()
                                            .addVertices(vertices)
                                            .addEdges(edges)
                                            .addQueues(queue)
                            );

            graphSequence.addGraphAnimationState(graphAnimationState2);

//            System.out.println("___________");
////            System.out.println("("+u + ")");
//            System.out.println(queue);
//            System.out.println("___________");
            System.out.println("!!!!!queue = " + queue);

            for(Edge edge : graph.edgeListMap.get(u)) {
                int v = edge.des;
                if (map.get(v).color == WHITE) {

//                    graphTree.addVertex(v, 0, 0);

                    map.get(v).color = GRAY;
                    map.get(v).dist = map.get(u).dist + 1;
                    map.get(v).parent = u;

                    Vertex vertex1 = graph.vertexMap.get(v);
                    queue.add(v);
                    vertices.add(vertex1);
                    edges.add(edge);

                    System.out.println("queue = " + queue);
                    GraphAnimationState graphAnimationState1 =
                            GraphAnimationState.create()
                                    .setState("Vertex = " + v)
                                    .setInfo("Vertex visited = " + v)
                                    .addGraphAnimationStateShadow(
                                            GraphAnimationStateShadow.create()
                                                    .addVertices(vertices)
                                                    .addEdges(edges)
                                                    .addQueues(queue)
                                    );

                    graphSequence.addGraphAnimationState(graphAnimationState1);

                }
                else{
                    System.out.println("queue = " + queue);
                    GraphAnimationState graphAnimationState1 =
                            GraphAnimationState.create()
                                    .setState("Vertex = " + v)
                                    .setInfo("Vertex already visited = " + v)
                                    .addGraphAnimationStateShadow(
                                            GraphAnimationStateShadow.create()
                                                    .addVertices(vertices)
                                                    .addEdges(edges)
                                                    .addQueues(queue)
                                    );

                    graphSequence.addGraphAnimationState(graphAnimationState1);
                }

                // EDGE CLASSIFICATION
                if (map.get(v).dist == map.get(u).dist + 1) {
//                    System.out.println("TREE EDGE : " + u + " -> " + v);
                    graphTree.addEdge(new EdgePro(edge, TREE));
                }
                else {
                    int src = u;
                    int des = v;

                    while (map.get(src).dist > 0 && map.get(src).dist > map.get(des).dist) {
                        src = map.get(src).parent;
                    }
                    while (map.get(des).dist > 0 && map.get(des).dist > map.get(src).dist) {
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


        // add vertices to the graphtree
        List<Map.Entry<Integer, VertexCLRS> > list = new LinkedList<>(map.entrySet());
        int size = list.size();

        HashMap<Integer, Integer> levelmap = new HashMap<>();
        for(Map.Entry<Integer, VertexCLRS> entry : list){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
//
//            graphTree.vertexMap.put(entry.getKey(), Pair.create(row, 0));
//            graphTree.vertexMap.put(entry.getKey(), Pair.create(row, random.nextInt(5)));
            if(entry.getValue().dist != Integer.MAX_VALUE && entry.getValue().dist >= 0) {

                if (levelmap.containsKey(entry.getValue().dist)) {
                    levelmap.put(entry.getValue().dist, levelmap.get(entry.getValue().dist) + 1);
                } else {
                    levelmap.put(entry.getValue().dist, 0);
                }

                System.out.println("Vertex = " + entry.getKey() + " row = " +
                        entry.getValue().dist + " col = " + levelmap.get(entry.getValue().dist));
                graphTree.addVertex(entry.getKey(), entry.getValue().dist, levelmap.get(entry.getValue().dist));
            }
        }

        graphTree.noOfRows = size;
        graphTree.noOfCols = size;


        return graphSequence;
    }

}
