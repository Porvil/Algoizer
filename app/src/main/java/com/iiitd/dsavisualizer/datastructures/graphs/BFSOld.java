package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BFSOld {
    GraphOld g;

    BFSOld(GraphOld g){
        this.g = g;
    }

    void run(VertexOld source){
        Queue<VertexOld> q = new LinkedList<>();
        q.add(source);

        source.isVisited = true;

        while(!q.isEmpty()){

            VertexOld cur = q.remove();
            Iterator it = cur.edgeOlds.iterator();
            while(it.hasNext()){
                EdgeOld w = (EdgeOld)it.next();
                if(!( w.dest.isVisited) ){
                    q.add(w.dest);
                    System.out.println("EDGE IN BFSOld :-    " + cur.name + " --> " + w.dest.name);
                    w.dest.isVisited = true;
                }
            }

        }

    }
}