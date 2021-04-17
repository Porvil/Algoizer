package com.iiitd.dsavisualizer.datastructures.graphs;

import com.iiitd.dsavisualizer.utility.UtilUI;

public class PriorityQueueElementState {
    public int data;
    public boolean visited;
    public int distance;

    public PriorityQueueElementState(int data, boolean visited, int distance) {
        this.data = data;
        this.visited = visited;
        this.distance = distance;
    }

    @Override
    public String toString() {
        String dist = distance == Integer.MAX_VALUE ? UtilUI.getInfinity() : String.valueOf(distance);
        return data + " " + visited + " " + dist;
    }
}
