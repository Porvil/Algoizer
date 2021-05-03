package com.iiitd.dsavisualizer.runapp.others;

import java.util.HashMap;
import java.util.Map;

// Used by Kruskal's algorithm
public class DisjointSet{

    class Node{
        int data;
        int rank;
        Node parent;

        public Node(int data, int rank) {
            this.data = data;
            this.rank = rank;
            this.parent = this;
        }

    }

    public Map<Integer, Node> map = new HashMap<>();

    public void addSingleSet(int data) {
        Node node = new Node(data, 0);
        map.put(data, node);
    }

    public boolean union(int data1, int data2) {
        Node node1 = map.get(data1);
        Node node2 = map.get(data2);

        Node parent1 = findSet(node1);
        Node parent2 = findSet(node2);

        if (parent1.data == parent2.data) {
            return false;
        }

        if (parent1.rank >= parent2.rank) {
            parent1.rank = (parent1.rank == parent2.rank) ? parent1.rank + 1 : parent1.rank;
            parent2.parent = parent1;
        }
        else {
            parent1.parent = parent2;
        }

        return true;
    }

    public int findSet(int data) {
        return findSet(map.get(data)).data;
    }

    private Node findSet(Node node) {
        Node parent = node.parent;
        if (parent == node) {
            return parent;
        }
        node.parent = findSet(node.parent);

        return node.parent;
    }

}