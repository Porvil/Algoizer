package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import android.util.Pair;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.EdgePro;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphTree;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexCLRS;

import static com.iiitd.dsavisualizer.datastructures.graphs.EdgeClass.*;
import static com.iiitd.dsavisualizer.datastructures.graphs.VertexVisitState.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DFS {

    Graph graph;
    GraphSequence graphSequence;
    int time;
    HashMap<Integer, VertexCLRS> map;
    public GraphTree graphTree;

    public DFS(Graph graph){
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.DFS);
        this.time = 0;
        this.graphTree = new GraphTree(graph.directed, graph.weighted);
    }

    public GraphSequence dfs(){

        map = new HashMap<>();

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = new VertexCLRS(entry.getValue(), WHITE, Integer.MAX_VALUE, -1, -1);
            map.put(entry.getKey(), vertexCLRS);
        }

        time = 0;

        for(Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()){
            VertexCLRS vertexCLRS = map.get(entry.getKey());
            if(vertexCLRS.color == WHITE){
                dfs_visit(entry.getKey());
            }
        }

        // ALL DONE
        for(Map.Entry<Integer, VertexCLRS> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }

        System.out.println("______________________________________");
        System.out.println("______________________________________");

        // TOPOLOGICAL SORT HERE
        List<Map.Entry<Integer, VertexCLRS> > list = new LinkedList<>(map.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, VertexCLRS> >() {
            public int compare(Map.Entry<Integer, VertexCLRS> o1,
                               Map.Entry<Integer, VertexCLRS> o2)
            {
                return o2.getValue().f - o1.getValue().f;
            }
        });


        Random random = new Random();


        int row = 0;
        for(Map.Entry<Integer, VertexCLRS> entry : list){
            System.out.println(entry.getValue());
//            graphTree.vertexMap.put(entry.getKey(), Pair.create(row, 0));
            graphTree.vertexMap.put(entry.getKey(), Pair.create(row, random.nextInt(5)));

            row++;
        }



        return graphSequence;
    }

    private void dfs_visit(int u) {// u = src, v = des
        time++;

        VertexCLRS vertexCLRS = map.get(u);
        vertexCLRS.dist = time;
        vertexCLRS.color = GRAY;

        for(Edge edge : graph.edgeListMap.get(u)) {
            int v = edge.des;

            if(map.get(v).color == BLACK){
                if (map.get(u).dist < map.get(v).dist) {
                    System.out.println("FORWARD EDGE : " + u + " -> " + v);
                    graphTree.edgePros.add(new EdgePro(edge, TREE));
                }
                else {
                    System.out.println("CROSS EDGE : " + u + " -> " + v);
                    graphTree.edgePros.add(new EdgePro(edge, CROSS));
                }
            }
            else if (map.get(v).color == GRAY) {
                System.out.println("BACK EDGE : " + u + " -> " + v);
                graphTree.edgePros.add(new EdgePro(edge, BACK));
            }
            else if (map.get(v).color == WHITE) {
                System.out.println("TREE EDGE : " + u + " -> " + v);
                graphTree.edgePros.add(new EdgePro(edge, TREE));

                map.get(v).parent = u;
                dfs_visit(v);
            }
        }

        vertexCLRS.color = BLACK;
        time++;
        vertexCLRS.f = time;
    }

}
