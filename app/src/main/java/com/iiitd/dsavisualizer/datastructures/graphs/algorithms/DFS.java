package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexBFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DFS {

    Graph graph;
    GraphSequence graphSequence;
    int time;
    HashMap<Integer, VertexBFS> map;

    public DFS(Graph graph){
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.DFS);
        this.time = 0;
    }

    public GraphSequence dfs(){

        map = new HashMap<>();

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexBFS vertexBFS = new VertexBFS(entry.getValue(), "WHITE", Integer.MAX_VALUE, -1, -1);
            map.put(entry.getKey(), vertexBFS);
        }

        time = 0;

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexBFS vertexBFS = map.get(entry.getKey());
            if(vertexBFS.color.equals("WHITE")){
                dfs_visit(entry.getKey());
            }
        }

        // ALL DONE
        for(Map.Entry<Integer, VertexBFS> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

        return graphSequence;
    }

    private void dfs_visit(int u) {// u = src, v = des
        time++;

        VertexBFS vertexBFS = map.get(u);
        vertexBFS.dist = time;
        vertexBFS.color = "GRAY";

        for(Edge edge : graph.map.get(u)) {
            int v = edge.des;

            if(map.get(v).color.equals("BLACK")){
                if (map.get(u).dist < map.get(v).dist) {
                    System.out.println("FORWARD EDGE : " + u + " -> " + v);
                }
                else {
                    System.out.println("CROSS EDGE : " + u + " -> " + v);
                }
            }
            else if (map.get(v).color.equals("GRAY")) {
                System.out.println("BACK EDGE : " + u + " -> " + v);
            }
            else if (map.get(v).color.equals("WHITE")) {
                System.out.println("TREE EDGE : " + u + " -> " + v);

                map.get(v).parent = u;
                dfs_visit(v);
            }
        }

        vertexBFS.color = "BLACK";
        time++;
        vertexBFS.f = time;
    }

}
