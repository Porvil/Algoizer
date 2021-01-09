package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class BFS {
    Graph g;

    public BFS(Graph g){
        this.g = g;
    }

    void run(int s){
        HashMap<Integer, Boolean> visited = new HashMap<>();
        for(Map.Entry<Integer, ArrayList<Integer>> entry : g.map.entrySet()){
            visited.put(entry.getKey(), false);
        }

        LinkedList<Integer> queue = new LinkedList();

        visited.put(s, true);
        queue.add(s);

        while (queue.size() != 0) {
            s = queue.poll();
            System.out.println(s + " ");

            ArrayList<Integer> i = g.map.get(s);
            for(int des : i){
                if(visited.get(des) == false){
                    visited.put(des, true);
                    System.out.println("EDGE IN BFS :-    " + s + " --> " + des);
                    queue.add(des);
                }
            }

            System.out.println();
        }
    }
}
