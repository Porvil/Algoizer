package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphTree;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexCLRS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra {
    Graph graph;
    GraphSequence graphSequence;
    public GraphTree graphTree;
    HashMap<Integer, VertexCLRS> map;

    public Dijkstra(Graph graph) {
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.DIJKSTRA);
        this.graphTree = new GraphTree(graph.directed, graph.weighted);
    }

    public GraphSequence run(int source) {
        int size = graph.noOfVertices;

        if (size < 1)
            return graphSequence;

        this.map = new HashMap<>();
        int count = graph.noOfVertices;

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        {
            GraphAnimationState graphAnimationState =
                    GraphAnimationState.create()
                            .setState("start")
                            .setInfo("start")
                            .addVertices(vertices)
                            .addEdges(edges);

            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            VertexCLRS vertexCLRS = VertexCLRS.dijkstraVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
        }

        map.get(source).dijkstraDist = 0;

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
                if(parent >=0 ) {
                    Edge edge = graph.getEdge(self, parent);
                    edges.add(edge);
                    System.out.println("cur Edge = " + edge);
                }

                GraphAnimationState graphAnimationState =
                        GraphAnimationState.create()
                                .setState("Visit = " + cur)
                                .setInfo("Visit = " + cur)
                                .addVertices(vertices)
                                .addEdges(edges);

                graphSequence.addGraphAnimationState(graphAnimationState);

                map.get(cur).visited = true;
                for (Edge edge : graph.edgeListMap.get(cur)) {
                    if (!map.get(edge.des).visited) {
                        int tempDistance = map.get(cur).dijkstraDist + edge.weight;
                        if (tempDistance < map.get(edge.des).dijkstraDist) {
                            map.get(edge.des).dijkstraDist = tempDistance;
                            map.get(edge.des).parent = map.get(cur).data;
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
