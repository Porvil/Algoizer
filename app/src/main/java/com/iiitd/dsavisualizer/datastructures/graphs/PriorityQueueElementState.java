package com.iiitd.dsavisualizer.datastructures.graphs;

import com.iiitd.dsavisualizer.utility.UtilUI;

// PriorityQueueElementState is used to maintain state of priority queue elements
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

    public String getDistanceString(){
        return distance == Integer.MAX_VALUE ? UtilUI.getInfinity() : String.valueOf(distance);
    }

    public String getStringText(){
        return data + " ( " + getDistanceString() +  " )";
    }

}