package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateExtra;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexCLRS;
import com.iiitd.dsavisualizer.utility.UtilUI;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateType.NONE;

// Dijkstra
public class Dijkstra {
    Graph graph;
    GraphSequence graphSequence;
    HashMap<Integer, VertexCLRS> map;
    HashMap<Integer, Vertex> verticesState;
    ArrayList<Edge> edges;
    String infinity = UtilUI.getInfinity();

    public Dijkstra(Graph graph) {
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.DIJKSTRA);
        this.map = new HashMap<>();
        this.verticesState = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    public GraphSequence dijkstra(int source) {
        int size = graph.noOfVertices;

        if (size < 1)
            return graphSequence;

        // Add all vertices
        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            VertexCLRS vertexCLRS = VertexCLRS.dijkstraVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
            verticesState.put(entry.getKey(), new Vertex(entry.getValue(), NONE));
        }

        // Add all edges
        for(Edge edge : graph.getAllEdges()) {
            edges.add(new Edge(edge, NONE));
        }

        // Start Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("dijkstra(" + source + ")")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addPriorityQueueElementState(map)));

        map.get(source).dijkstraDist = 0;

        // Setting initial distances
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("all vertices distance " + UtilUI.getLeftArrow() + infinity
                                + "\n" + "source vertex (" + source + ") distance " + UtilUI.getLeftArrow() + " 0")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addMapDijkstra(map)
                                        .addPriorityQueueElementState(map)));

        // Dijkstra
        for (int i = 0; i < size; i++) {
            // Heap Like Operations Simulated
            int vertexNo = findMinDistanceIndex();
            // if no vertex is returned, skip this iteration
            if(vertexNo < 0){
                continue;
            }

            // Vertex Added to Dijkstra
            Vertex srcVertex = verticesState.get(vertexNo);
            srcVertex.setToDone();
            VertexCLRS startVertexCLRS = map.get(vertexNo);

            int self = map.get(vertexNo).data;
            int parent = map.get(vertexNo).parent;
            // Parent Edge fixed for Dijkstra
            if (parent >= 0 ) {
                Edge parentEdge = edges.get(edges.indexOf(graph.getEdge(parent, self)));
                parentEdge.setToDone();
            }

            // Selecting Min. Value vertex not in Dijkstra
            graphSequence.addGraphAnimationState(
                    GraphAnimationState.create()
                            .setInfo("remove min-key vertex from priority queue"
                                    + "\n" + "vertex (" + vertexNo + ") selected")
                            .setVerticesState(verticesState)
                            .addEdges(edges)
                            .addGraphAnimationStateExtra(
                                    GraphAnimationStateExtra.create()
                                            .addMapDijkstra(map)
                                            .addPriorityQueueElementState(map)));

            startVertexCLRS.visited = true;
            for (Edge curEdge : graph.edgeListMap.get(vertexNo)) {
                Vertex desVertex = verticesState.get(curEdge.des);
                VertexCLRS endVertexCLRS = map.get(curEdge.des);
                if (!endVertexCLRS.visited) {
                    int tempDistance = startVertexCLRS.dijkstraDist + curEdge.weight;
                    int otherDistance = endVertexCLRS.dijkstraDist;
                    String otherDist = String.valueOf(otherDistance);
                    if(otherDistance == Integer.MAX_VALUE){
                        otherDist = DecimalFormatSymbols.getInstance().getInfinity();
                    }

                    Edge edge = edges.get(edges.indexOf(curEdge));
                    edge.setToHighlight();
                    desVertex.setToHighlight();

                    String updated = "";
                    if (tempDistance < otherDistance) {
                        updated = startVertexCLRS.dijkstraDist + " + " + curEdge.weight + " < " + otherDist + ", vertex(" + desVertex.data + ") distance updated";
                    }
                    else {
                        updated = startVertexCLRS.dijkstraDist + " + " + curEdge.weight + " >= " + otherDist + ", continue";
                    }

                    // Updating Distance of Edge's des. Vertex
                    graphSequence.addGraphAnimationState(
                            GraphAnimationState.create()
                                    .setInfo("vertex (" + vertexNo + "), edge (" + vertexNo + " ── " + curEdge.des + ")"
                                            + "\n" + updated)
                                    .setVerticesState(verticesState)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(
                                            GraphAnimationStateExtra.create()
                                                    .addMapDijkstra(map)
                                                    .addPriorityQueueElementState(map)));

                    if (tempDistance < otherDistance) {
                        endVertexCLRS.dijkstraDist = tempDistance;
                        endVertexCLRS.parent = startVertexCLRS.data;
                    }

                    edge.setToNormal();
                    desVertex.setToNormal();
                }
            }

        }

        // End Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("dijkstra() completed")
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                .addMapDijkstra(map)
                                .addPriorityQueueElementState(map)));

        // ALL DONE
        for (Map.Entry<Integer, VertexCLRS> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

        return graphSequence;
    }

    // Finding the minimum distance
    private int findMinDistanceIndex() {
        long minDistance = Long.MAX_VALUE;
        int minDistanceVertex = -1;
        for (Map.Entry<Integer, VertexCLRS> entry : map.entrySet()) {
            if (!entry.getValue().visited && entry.getValue().dijkstraDist < minDistance) {
                minDistance = entry.getValue().dijkstraDist;
                minDistanceVertex = entry.getKey();
            }
        }

        return minDistanceVertex;
    }

}