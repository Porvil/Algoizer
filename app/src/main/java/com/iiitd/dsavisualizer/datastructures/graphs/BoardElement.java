package com.iiitd.dsavisualizer.datastructures.graphs;

// BoardElement contains data about a 1-data element in Board class
public class BoardElement {

    public int value;
    public boolean occupied; // true = vertex is present

    public BoardElement() {
        this.occupied = false;
        this.value = -1;
    }

    @Override
    public String toString() {
        return "BoardElement{" +
                "value = " + value +
                ", occupied = " + occupied +
                '}';
    }

}