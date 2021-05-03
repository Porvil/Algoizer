package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

// Vertex Class implements Comparable which is used in case of custom input nodes having no fixed {row, col}
public class Vertex implements Comparable<Vertex> {
    public int data;
    public int row;
    public int col;

    public GraphAnimationStateType graphAnimationStateType;

    public Vertex(int data, int row, int col) {
        this.data = data;
        this.row = row;
        this.col = col;
        this.graphAnimationStateType = GraphAnimationStateType.NONE;
    }

    public Vertex(Vertex vertex, GraphAnimationStateType graphAnimationStateType) {
        this.data = vertex.data;
        this.row = vertex.row;
        this.col = vertex.col;
        this.graphAnimationStateType = graphAnimationStateType;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "data=" + data +
                ", row=" + row +
                ", col=" + col +
                '}';
    }

    @Override
    public int compareTo(Vertex o2) {
        Vertex o1 = this;

        if(o1.row == o2.row && o1.col == o2.col){
            return o1.data - o2.data;
        }
        else{
            if(o2.row == -1 || o2.col == -1){
                return -1;
            }
            else if(o1.row == -1 || o1.col == -1){
                return 1;
            }
            else{
                return o1.row - o2.row;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Vertex vertex = (Vertex) o;
        return data == vertex.data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    public static Vertex getClone(Vertex vertex){
        Vertex vertex1 = new Vertex(vertex.data, vertex.row, vertex.col);
        vertex1.graphAnimationStateType = vertex.graphAnimationStateType;

        return vertex1;
    }

    public void setToNone(){
        this.graphAnimationStateType = GraphAnimationStateType.NONE;
    }

    public void setToNormal(){
        this.graphAnimationStateType = GraphAnimationStateType.NORMAL;
    }

    public void setToHighlight(){
        this.graphAnimationStateType = GraphAnimationStateType.HIGHLIGHT;
    }

    public void setToDone(){
        this.graphAnimationStateType = GraphAnimationStateType.DONE;
    }

    public void setGAST(GraphAnimationStateType graphAnimationStateType){
        this.graphAnimationStateType = graphAnimationStateType;
    }

}