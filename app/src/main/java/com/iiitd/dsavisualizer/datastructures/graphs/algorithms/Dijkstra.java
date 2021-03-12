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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.BACK;
import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.CROSS;
import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.TREE;
import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.BLACK;
import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.GRAY;
import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.WHITE;

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

    public void run(int source) {
        int size = graph.noOfVertices;

        if (size < 1)
            return;

        this.map = new HashMap<>();
        int count = graph.noOfVertices;
//        PriorityQueue<VertexCLRS> pq = new PriorityQueue<>(size, new Comparator<VertexCLRS>() {
//            @Override
//            public int compare(VertexCLRS o1, VertexCLRS o2) {
//                // your codes here
//                return o1.dijkstraDist - o2.dijkstraDist;
//            }
//        });


        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            VertexCLRS vertexCLRS = VertexCLRS.dijkstraVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
        }

        map.get(source).dijkstraDist = 0;

        for (int i = 0; i < count; i++) {

            // Update the distance between neighbouring vertex and source vertex
            int cur = findMinDistance();
            if (cur >= 0) {
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

    }

    // Finding the minimum distance
    private int findMinDistance() {
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
