package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateExtra;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexCLRS;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateType.NONE;

// BellmanFord
public class BellmanFord {
    Graph graph;
    GraphSequence graphSequence;
    HashMap<Integer, VertexCLRS> map;
    HashMap<Integer, Vertex> verticesState;
    ArrayList<Edge> edges;
    ArrayList<Edge> cycles;

    public BellmanFord(Graph graph) {
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.BELLMAN_FORD);
        this.map = new HashMap<>();
        this.verticesState = new HashMap<>();
        this.edges = new ArrayList<>();
        this.cycles = new ArrayList<>();
    }

    public GraphSequence bellmanford() {
        int size = graph.noOfVertices;
        int source = 0;

        if (size < 1)
            return graphSequence;

        ArrayList<Edge> allEdges = graph.getAllEdges();

        // Add all vertices
        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            VertexCLRS vertexCLRS = VertexCLRS.bellmanfordVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
            verticesState.put(entry.getKey(), new Vertex(entry.getValue(), NONE));
        }

        // Add all edges
        for(Edge edge : graph.getAllEdges()) {
            // Add all edges in case of directed, add only firstedge in case of undirected graph
            if(graph.directed || edge.isFirstEdge){
                edges.add(new Edge(edge, NONE));
            }
        }

        // Start Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("bellman ford()")
                        .setVerticesState(verticesState)
                        .addEdges(edges));

        // Fixing a Source Vertex
        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            map.get(entry.getKey()).bellmanFordDist = 0;
            source = entry.getKey();
            break;
        }

        boolean checkNegativeLoop = true;

        // Bellman Ford Algorithm
        for (int i=0;i<size-1;i++) {
            boolean isRelaxed = false;
            for(Edge curEdge : allEdges) {
                if (graph.directed || curEdge.isFirstEdge) {
                    VertexCLRS srcVertexCLRS = map.get(curEdge.src);
                    VertexCLRS desVertexCLRS = map.get(curEdge.des);
                    Vertex srcVertex = verticesState.get(curEdge.src);
                    Vertex desVertex = verticesState.get(curEdge.des);
                    Edge edge = edges.get(edges.indexOf(curEdge));

                    int tempDistance = srcVertexCLRS.bellmanFordDist + curEdge.weight;
                    int otherDistance = desVertexCLRS.bellmanFordDist;
                    String otherDist = String.valueOf(otherDistance);
                    if (otherDistance == Integer.MAX_VALUE) {
                        otherDist = DecimalFormatSymbols.getInstance().getInfinity();
                    }

                    srcVertex.setToHighlight();
                    desVertex.setToDone();
                    edge.setToHighlight();

                    String updated = "";
                    if (tempDistance < otherDistance) {
                        updated = srcVertexCLRS.bellmanFordDist + " + " + curEdge.weight + " < " + otherDist + ", vertex(" + desVertex.data + ") distance updated";
                    }
                    else {
                        updated = srcVertexCLRS.bellmanFordDist + " + " + curEdge.weight + " >= " + otherDist + ", continue";
                    }

                    // Updating Distance of Edge's des. Vertex
                    graphSequence.addGraphAnimationState(
                            GraphAnimationState.create()
                                    .setInfo("vertex (" + curEdge.src + "), edge (" + curEdge.src + " ── " + curEdge.des + ")"
                                            + "\n" + updated)
                                    .setVerticesState(verticesState)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(
                                            GraphAnimationStateExtra.create()
                                                    .addMapBellmanford(map)));

                    if (tempDistance < otherDistance) {
                        desVertexCLRS.bellmanFordDist = tempDistance;
                        desVertexCLRS.parent = srcVertexCLRS.data;
                        isRelaxed = true;
                    }

                    edge.setToNormal();
                    srcVertex.setToNormal();
                    desVertex.setToNormal();
                }
            }

            if(!isRelaxed){
                // Updating Distance of Edge's des. Vertex
                graphSequence.addGraphAnimationState(
                        GraphAnimationState.create()
                                .setInfo("no edges relaxed in this iteration"
                                        + "\n" + "break loop")
                                .setVerticesState(verticesState)
                                .addEdges(edges)
                                .addGraphAnimationStateExtra(
                                        GraphAnimationStateExtra.create()
                                                .addMapBellmanford(map)));

                System.out.println("nothing relaxed");
                checkNegativeLoop = false;
                break;
            }
        }

        boolean hasNegativeEdgeLoop = false;
        if(checkNegativeLoop){
            for(Edge curEdge : allEdges) {
                if (graph.directed || curEdge.isFirstEdge) {
                    VertexCLRS srcVertexCLRS = map.get(curEdge.src);
                    VertexCLRS desVertexCLRS = map.get(curEdge.des);
                    Vertex srcVertex = verticesState.get(curEdge.src);
                    Vertex desVertex = verticesState.get(curEdge.des);
                    Edge edge = edges.get(edges.indexOf(curEdge));

                    int tempDistance = srcVertexCLRS.bellmanFordDist + curEdge.weight;
                    int otherDistance = desVertexCLRS.bellmanFordDist;
                    String otherDist = String.valueOf(otherDistance);
                    if (otherDistance == Integer.MAX_VALUE) {
                        otherDist = DecimalFormatSymbols.getInstance().getInfinity();
                    }

                    srcVertex.setToHighlight();
                    desVertex.setToDone();
                    edge.setToHighlight();

                    String updated = "";
                    if (tempDistance < otherDistance) {
                        updated = map.get(curEdge.src).bellmanFordDist + " + " + curEdge.weight + " < " + otherDist + ", vertex(" + desVertex.data + ") distance updated";
                    }
                    else {
                        updated = map.get(curEdge.src).bellmanFordDist + " + " + curEdge.weight + " >= " + otherDist + ", continue";
                    }

                    // Updating Distance of Edge's des. Vertex
                    graphSequence.addGraphAnimationState(
                            GraphAnimationState.create()
                                    .setInfo("vertex (" + curEdge.src + "), edge (" + curEdge.src + " ── " + curEdge.des + ")"
                                            + "\n" + updated)
                                    .setVerticesState(verticesState)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(
                                            GraphAnimationStateExtra.create()
                                                    .addMapBellmanford(map)));

                    if (tempDistance < otherDistance) {
                        // Updating Distance of Edge's des. Vertex
                        graphSequence.addGraphAnimationState(
                                GraphAnimationState.create()
                                        .setInfo("edge relaxed in " + size + "th iteration(last)"
                                                + "\n" + "negative loop in graph !!!")
                                        .setVerticesState(verticesState)
                                        .addEdges(edges)
                                        .addGraphAnimationStateExtra(
                                                GraphAnimationStateExtra.create()
                                                        .addMapBellmanford(map)));

                        hasNegativeEdgeLoop = true;
                        edge.setToNormal();
                        srcVertex.setToNormal();
                        desVertex.setToNormal();

                        // Negative Cycle Detection Path
                        int cycleStartVertex = srcVertexCLRS.data;
                        int self = srcVertexCLRS.data;
                        int parent = srcVertexCLRS.parent;
                        while (parent >= 0) {
                            Edge parentEdge = edges.get(edges.indexOf(graph.getEdge(parent, self)));
                            cycles.add(parentEdge);

                            if(parent == cycleStartVertex){
                                break;
                            }

                            self = parent;
                            parent = map.get(self).parent;
                        }

                        break;
                    }

                    edge.setToNormal();
                    srcVertex.setToNormal();
                    desVertex.setToNormal();
                }
            }
        }

        String finalResult = "";
        if(hasNegativeEdgeLoop){
            finalResult = "\ngraph contains negative edge cycle";

            StringBuilder cycle = new StringBuilder();
            int weight = 0;
            for(Edge edge : cycles){
                cycle.append(edge.src + " ── ");
                weight += edge.weight;
            }
            cycle.append(cycles.get(0).src);
            cycle.reverse();
            cycle.insert(0, "edge cycle (");
            cycle.append(")");
            cycle.append("\n");
            cycle.append("edge cycle weight = ");
            cycle.append(weight);

            // Negative Edge Cycle Animation
            graphSequence.addGraphAnimationState(
                    GraphAnimationState.create()
                            .setInfo(cycle.toString())
                            .setVerticesState(verticesState)
                            .addEdges(edges)
                            .addGraphAnimationStateExtra(
                                    GraphAnimationStateExtra.create()
                                            .addMapBellmanford(map)
                                            .addCycle(cycles)));
        }
        else{
            // Set Final Edges
            graphSequence.addGraphAnimationState(
                    GraphAnimationState.create()
                            .setInfo("iterating over all vertices and"
                                    + "\n" + "selecting their parent's to select final edges")
                            .setVerticesState(verticesState)
                            .addEdges(edges)
                            .addGraphAnimationStateExtra(
                                    GraphAnimationStateExtra.create()
                                            .addMapBellmanford(map)));

            for (Map.Entry<Integer, VertexCLRS> entry : map.entrySet()) {
                if(entry.getValue().parent >= 0){
                    int src = entry.getValue().parent;
                    int des = entry.getValue().data;
                    Edge edge = edges.get(edges.indexOf(graph.getEdge(src, des)));
                    Vertex srcVertex = verticesState.get(src);
                    Vertex desVertex = verticesState.get(des);
                    srcVertex.setToDone();
                    desVertex.setToDone();
                    edge.setToDone();
                }
            }
        }

        // End Animation
        graphSequence.addGraphAnimationState(
                GraphAnimationState.create()
                        .setInfo("bellman ford() completed" + finalResult)
                        .setVerticesState(verticesState)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(
                                GraphAnimationStateExtra.create()
                                        .addMapBellmanford(map)
                                        .addCycle(cycles)));


        // ALL DONE
        for (Map.Entry<Integer, VertexCLRS> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

        return graphSequence;
    }

}