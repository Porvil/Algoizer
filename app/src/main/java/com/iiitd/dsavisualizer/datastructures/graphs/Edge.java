package com.iiitd.dsavisualizer.datastructures.graphs;

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
        graphAnimationStateType = GraphAnimationStateType.NONE;
    }

    public Edge(int src, int des, int weight, boolean isFirstEdge) {
        this.src = src;
        this.des = des;
        this.weight = weight;
        this.isFirstEdge = isFirstEdge;
        graphAnimationStateType = GraphAnimationStateType.NONE;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "src=" + src +
                ", des=" + des +
                ", weight=" + weight +
                '}';
    }

    public static Edge getClone(Edge edge){
        Edge edge1 = new Edge(edge.src, edge.des, edge.weight, edge.isFirstEdge);
        edge1.graphAnimationStateType = edge.graphAnimationStateType;

        return edge1;
    }
}
