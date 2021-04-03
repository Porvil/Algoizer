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

public class Prims {
    Graph graph;
    GraphSequence graphSequence;
    HashMap<Integer, VertexCLRS> map;

    public Prims(Graph graph) {
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.PRIMS);
    }

    public GraphSequence run() {
        int size = graph.noOfVertices;
        int source = 0;
        String infinity = DecimalFormatSymbols.getInstance().getInfinity();
        if (size < 1)
            return graphSequence;

        this.map = new HashMap<>();
        int count = graph.noOfVertices;

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();


        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            VertexCLRS vertexCLRS = VertexCLRS.dijkstraVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
        }

        // Fixing a Source Vertex
        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            map.get(entry.getKey()).dijkstraDist = 0;
            source = entry.getKey();
            break;
        }

        {
            GraphAnimationState graphAnimationState =
                    GraphAnimationState.create()
                            .setState("start")
                            .setInfo("prims(source = " + source +")")
                            .addVertices(vertices)
                            .addEdges(edges);

            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        {
            GraphAnimationState graphAnimationState =
                    GraphAnimationState.create()
                            .setState("start")
                            .setInfo("set " + infinity + " to all vertices, and 0 to source vertex")
                            .addVertices(vertices)
                            .addEdges(edges)
                            .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                            .addMapDijkstra(map));

            graphSequence.addGraphAnimationState(graphAnimationState);
        }


        for (int i = 0; i < count; i++) {
            // Update the distance between neighbouring vertex and source vertex
            int cur = findMinDistanceIndex();
            if (cur >= 0) {
                Vertex vertex = graph.vertexMap.get(cur);
                vertices.add(vertex);
                VertexCLRS vertexCLRS = map.get(cur);
                System.out.println(vertexCLRS);
                int self = map.get(cur).data;
                int parent = map.get(cur).parent;
                if(parent >= 0 ) {
                    System.out.println(parent  + "->" + self);
                    Edge edge = graph.getEdge(parent, self);
                    edges.add(edge);
                    System.out.println("cur Edge = " + edge);
                }

                GraphAnimationState graphAnimationState =
                        GraphAnimationState.create()
                                .setState("Visit = " + cur)
                                .setInfo("Visit = " + cur)
                                .addVertices(vertices)
                                .addEdges(edges)
                                .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                        .addMapDijkstra(map));

                graphSequence.addGraphAnimationState(graphAnimationState);

                map.get(cur).visited = true;
                for (Edge edge : graph.edgeListMap.get(cur)) {
                    if (!map.get(edge.des).visited) {
//                        int tempDistance = map.get(cur).dijkstraDist + edge.weight;
                        int tempDistance = edge.weight;
                        int otherDistance = map.get(edge.des).dijkstraDist;
                        String otherDist = String.valueOf(otherDistance);
                        if(otherDistance == Integer.MAX_VALUE){
                            otherDist = DecimalFormatSymbols.getInstance().getInfinity();
                        }

                        System.out.println("!!!!!!!!!!!!!!" + map);
                        if (tempDistance < otherDistance) {

                            GraphAnimationState graphAnimationState1 =
                                    GraphAnimationState.create()
                                            .setState("Visit = " + cur)
                                            .setInfo("Vertex = " + cur + ", Edge " + cur + " --- " + edge.des + "\n"
                                                + map.get(cur).dijkstraDist + " + " + edge.weight + " < " + otherDist
                                            )
                                            .addVertices(vertices)
                                            .addEdges(edges)
                                            .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                                    .addMapDijkstra(map));

                            graphSequence.addGraphAnimationState(graphAnimationState1);

                            map.get(edge.des).dijkstraDist = tempDistance;
                            map.get(edge.des).parent = map.get(cur).data;
                        }
                        else{
                            GraphAnimationState graphAnimationState1 =
                                    GraphAnimationState.create()
                                            .setState("Visit = " + cur)
                                            .setInfo("Vertex = " + cur + "\n"
                                                    + "Edge " + cur + " --- " + edge.des + "\n"
                                                    + map.get(cur).dijkstraDist + " + " + edge.weight + " >= " + otherDist
                                            )
                                            .addVertices(vertices)
                                            .addEdges(edges)
                                            .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                                    .addMapDijkstra(map));

                            graphSequence.addGraphAnimationState(graphAnimationState1);
                        }
                    }
                }
            }

        }

        // ALL DONE
        for (Map.Entry<Integer, VertexCLRS> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

        return graphSequence;
    }

    // Finding the minimum distance
    private int findMinDistanceIndex() {
        int minDistance = Integer.MAX_VALUE;
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
