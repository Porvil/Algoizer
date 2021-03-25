package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateExtra;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexCLRS;
import com.iiitd.dsavisualizer.runapp.others.DisjointSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Kruskals {
    Graph graph;
    GraphSequence graphSequence;
    HashMap<Integer, VertexCLRS> map;

    public Kruskals(Graph graph) {
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.KRUSKALS);
    }

    public GraphSequence run() {
        int size = graph.noOfVertices;

        if (size < 1)
            return graphSequence;

        this.map = new HashMap<>();

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        ArrayList<Edge> allEdges = graph.getAllEdges();

        System.out.println(allEdges);

        Collections.sort(allEdges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        });

        System.out.println(allEdges);

        DisjointSet ds = new DisjointSet();

        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            ds.addSingleSet(entry.getKey());
        }

        for(Edge edge : allEdges){
            int first = edge.src;
            int second = edge.des;
            if(ds.findSet(first) != ds.findSet(second)){
                vertices.add(graph.vertexMap.get(first));
                vertices.add(graph.vertexMap.get(second));
                edges.add(edge);
                ds.union(first, second);
            }
        }

        System.out.println("ANSWERS");
        System.out.println(edges);

        GraphAnimationState graphAnimationState1 =
                GraphAnimationState.create()
                        .setState("Done")
//                                    .setInfo(map.get(cur).dijkstraDist + " + " + edge.weight + " < " + otherDist
//                                            + "\n" + "Vertex(" + edge.des +").distance = " + tempDistance)
                        .addVertices(vertices)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                .addMapBellmanford(map));

        graphSequence.addGraphAnimationState(graphAnimationState1);

        return graphSequence;
    }

}
