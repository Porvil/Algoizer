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

import static com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateType.*;

// Kruskal's
public class Kruskals {
    Graph graph;
    GraphSequence graphSequence;
    HashMap<Integer, VertexCLRS> map;
    HashMap<Integer, Vertex> verticesState;
    ArrayList<Edge> edges;

    public Kruskals(Graph graph) {
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.KRUSKALS);
        this.map = new HashMap<>();
        this.verticesState = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    public GraphSequence kruskals() {
        int size = graph.noOfVertices;

        if (size < 1)
            return graphSequence;

        ArrayList<Edge> allEdgesBothWays = graph.getAllEdges();
        ArrayList<Edge> allEdges = new ArrayList<>();
        DisjointSet ds = new DisjointSet();

        // Add all vertices
        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            ds.addSingleSet(entry.getKey());
            verticesState.put(entry.getKey(), new Vertex(entry.getValue(), NONE));
        }

        // Add all edges
        for(Edge edge : allEdgesBothWays) {
            if(edge.isFirstEdge) {
                edges.add(new Edge(edge, NONE));
            }
        }

        allEdges.addAll(edges);

        // Start Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("kruskal()")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                        .addEdges(allEdges)));

        // Sort Edges Start
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("sort all edges")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                .addEdges(allEdges)));

        // Sort Edges
        Collections.sort(allEdges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        });

        // Sort Edges Done
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("all edges sorted")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                .addEdges(allEdges)));

        // Kruskal's Algorithm
        for(Edge curEdge : allEdges){
            if(curEdge.isFirstEdge) {
                int first = curEdge.src;
                int second = curEdge.des;
                Vertex srcVertex = verticesState.get(curEdge.src);
                Vertex desVertex = verticesState.get(curEdge.des);
                Edge edge = edges.get(edges.indexOf(curEdge));
                edge.setToHighlight();

                if (ds.findSet(first) != ds.findSet(second)) {
                    ds.union(first, second);
                    srcVertex.setToHighlight();
                    desVertex.setToHighlight();

                    graphSequence.addGraphAnimationState(
                            GraphAnimationState.create()
                                    .setInfo("edge (" + curEdge.src + " ── " + curEdge.des + ")"
                                            + "\n" + "edge added to MST")
                                    .setVerticesState(verticesState)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                            .addEdges(allEdges)));

                    srcVertex.setToDone();
                    desVertex.setToDone();
                    edge.setToDone();
                }
                else {
                    graphSequence.addGraphAnimationState(
                            GraphAnimationState.create()
                                    .setInfo("edge (" + curEdge.src + " ── " + curEdge.des + ")"
                                            + "\n" + "vertices (" + curEdge.src + ", " + curEdge.des + ") already in MST, continue")
                                    .setVerticesState(verticesState)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                            .addEdges(allEdges)));

                    edge.setToNormal();
                }
            }
        }

        // End Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("kruskal() completed")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                .addEdges(allEdges)));

        return graphSequence;
    }

}