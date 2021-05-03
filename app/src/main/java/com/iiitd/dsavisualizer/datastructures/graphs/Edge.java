package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

// Edge Class
public class Edge {

    public int src;
    public int des;
    public int weight;
    public boolean isFirstEdge;

    public GraphAnimationStateType graphAnimationStateType;

    public Edge(int src, int des) {
        this.src = src;
        this.des = des;
        this.weight = 1;
        this.graphAnimationStateType = GraphAnimationStateType.NONE;
    }

    public Edge(int src, int des, int weight, boolean isFirstEdge) {
        this.src = src;
        this.des = des;
        this.weight = weight;
        this.isFirstEdge = isFirstEdge;
        this.graphAnimationStateType = GraphAnimationStateType.NONE;
    }

    public Edge(Edge edge,GraphAnimationStateType graphAnimationStateType) {
        this.src = edge.src;
        this.des = edge.des;
        this.weight = edge.weight;
        this.isFirstEdge = edge.isFirstEdge;
        this.graphAnimationStateType = graphAnimationStateType;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "src=" + src +
                ", des=" + des +
                ", weight=" + weight +
                ", isFirstEdge=" + isFirstEdge +
                ", graphAnimationStateType=" + graphAnimationStateType +
                '}';
    }

    public String getStringText(){
        return src + " ── " + des + " ( " + weight + " )";
    }

    public static Edge getClone(Edge edge){
        Edge edge1 = new Edge(edge.src, edge.des, edge.weight, edge.isFirstEdge);
        edge1.graphAnimationStateType = edge.graphAnimationStateType;

        return edge1;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Edge edge = (Edge) o;

        return src == edge.src &&
                des == edge.des &&
                weight == edge.weight &&
                isFirstEdge == edge.isFirstEdge;
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, des, weight, isFirstEdge);
    }

}