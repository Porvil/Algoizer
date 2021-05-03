package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.Objects;

// VertexCLRS contains Vertex class variables
// and also maintains extra variables which are used by different graph algorithms
public class VertexCLRS {

    // Vertex Variables
    public int data;
    public int row;
    public int col;
    public GraphAnimationStateType graphAnimationStateType;

    // Vertex CLRS Variables
    public GraphAlgorithmType graphAlgorithmType;      // graphAlgorithmType, used in toString()
    public VertexVisitState color;                     // used by bfs and dfs
    public int parent;                                 // used by bfs, dfs and dijkstra
    public int bfsDist;                                // used as distance in  bfs [level order]
    public int startTime;                              // used as start time in dfs
    public int finishTime;                             // used as end time in dfs
    public int dfsDepth;                               // used as depth in dfs
    public int dijkstraDist;                           // used as distance in dijkstra and prim
    public int bellmanFordDist;                        // used as distance in bellman-ford
    public boolean visited;                            // used by dijkstra
    public int connectedID;                            // used by bfs and dfs connected components

    // Used by BFS
    public static VertexCLRS bfsVertexCLRS(Vertex vertex) {
        VertexCLRS vertexCLRS = new VertexCLRS(vertex);

        vertexCLRS.graphAlgorithmType = GraphAlgorithmType.BFS;
        vertexCLRS.color = VertexVisitState.WHITE;
        vertexCLRS.bfsDist = Integer.MAX_VALUE;
        vertexCLRS.parent = -1;

        return vertexCLRS;
    }

    // Used by BFS Connected Component
    public static VertexCLRS bfsVertexCCCLRS(Vertex vertex) {
        VertexCLRS vertexCLRS = new VertexCLRS(vertex);

        vertexCLRS.graphAlgorithmType = GraphAlgorithmType.BFS_CC;
        vertexCLRS.color = VertexVisitState.WHITE;
        vertexCLRS.connectedID = -1;
        vertexCLRS.parent = -1;

        return vertexCLRS;
    }

    // Used by BFS Connected Component
    public static VertexCLRS dfsVertexCCCLRS(Vertex vertex) {
        VertexCLRS vertexCLRS = new VertexCLRS(vertex);

        vertexCLRS.graphAlgorithmType = GraphAlgorithmType.DFS_CC;
        vertexCLRS.color = VertexVisitState.WHITE;
        vertexCLRS.connectedID = -1;
        vertexCLRS.parent = -1;

        return vertexCLRS;
    }

    // Used by DFS
    public static VertexCLRS dfsVertexCLRS(Vertex vertex) {
        VertexCLRS vertexCLRS = new VertexCLRS(vertex);

        vertexCLRS.graphAlgorithmType = GraphAlgorithmType.DFS;
        vertexCLRS.color = VertexVisitState.WHITE;
        vertexCLRS.startTime = Integer.MAX_VALUE;
        vertexCLRS.finishTime = -1;
        vertexCLRS.parent = -1;
        vertexCLRS.dfsDepth = 0;

        return vertexCLRS;
    }

    // Used by Dijkstra, Prim's
    public static VertexCLRS dijkstraVertexCLRS(Vertex vertex) {
        VertexCLRS vertexCLRS = new VertexCLRS(vertex);

        vertexCLRS.graphAlgorithmType = GraphAlgorithmType.DIJKSTRA;
        vertexCLRS.dijkstraDist = Integer.MAX_VALUE;
        vertexCLRS.parent = -1;
        vertexCLRS.visited = false;

        return vertexCLRS;
    }

    // Used by Bellman-Ford
    public static VertexCLRS bellmanfordVertexCLRS(Vertex vertex) {
        VertexCLRS vertexCLRS = new VertexCLRS(vertex);

        vertexCLRS.graphAlgorithmType = GraphAlgorithmType.BELLMAN_FORD;
        vertexCLRS.bellmanFordDist = Integer.MAX_VALUE;
        vertexCLRS.parent = -1;

        return vertexCLRS;
    }

    // Used internally in this class itself
    private VertexCLRS(Vertex vertex) {
        this.data = vertex.data;
        this.row = vertex.row;
        this.col = vertex.col;
        this.graphAlgorithmType = GraphAlgorithmType.NULL;
    }

    // toString based on type of GraphAlgorithm is used
    @Override
    public String toString() {
        if (graphAlgorithmType == GraphAlgorithmType.BFS) {
            return "VertexCLRS{" +
                    "data=" + data +
                    ", color=" + color +
                    ", parent=" + parent +
                    ", bfsDist=" + bfsDist +
                    '}';
        }
        else if (graphAlgorithmType == GraphAlgorithmType.DFS) {
            return "VertexCLRS{" +
                    "data=" + data +
                    ", color=" + color +
                    ", parent=" + parent +
                    ", dfsDepth=" + dfsDepth +
                    ", startTime=" + startTime +
                    ", finishTime=" + finishTime +
                    '}';
        }
        else if (graphAlgorithmType == GraphAlgorithmType.DIJKSTRA) {
            return "VertexCLRS{" +
                    "data=" + data +
                    ", parent=" + parent +
                    ", visited=" + visited +
                    ", dijkstraDist=" + dijkstraDist +
                    '}';
        }
        else if (graphAlgorithmType == GraphAlgorithmType.BELLMAN_FORD) {
            return "VertexCLRS{" +
                    "data=" + data +
                    ", parent=" + parent +
                    ", bellmanFordDist=" + bellmanFordDist +
                    '}';
        }

        return "VertexCLRS{" +
                "graphAlgorithmType=" + graphAlgorithmType +
                ", data=" + data +
                ", row=" + row +
                ", col=" + col +
                ", color=" + color +
                ", parent=" + parent +
                ", bfsDist=" + bfsDist +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", dfsDepth=" + dfsDepth +
                ", dijkstraDist=" + dijkstraDist +
                ", visited=" + visited +
                '}';
    }

    public Vertex getVertex(){
        return new Vertex(data, row, col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
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

    public static VertexCLRS getClone(VertexCLRS vertexCLRS){
        VertexCLRS vertexCLRS1 = new VertexCLRS(vertexCLRS.getVertex());

        vertexCLRS1.data = vertexCLRS.data;
        vertexCLRS1.row = vertexCLRS.row;
        vertexCLRS1.col = vertexCLRS.col;
        vertexCLRS1.graphAnimationStateType = vertexCLRS.graphAnimationStateType;

        vertexCLRS1.graphAlgorithmType = vertexCLRS.graphAlgorithmType;
        vertexCLRS1.color = vertexCLRS.color;
        vertexCLRS1.parent = vertexCLRS.parent;
        vertexCLRS1.bfsDist = vertexCLRS.bfsDist;
        vertexCLRS1.startTime = vertexCLRS.startTime;
        vertexCLRS1.finishTime = vertexCLRS.finishTime;
        vertexCLRS1.dfsDepth = vertexCLRS.dfsDepth;
        vertexCLRS1.dijkstraDist = vertexCLRS.dijkstraDist;
        vertexCLRS1.bellmanFordDist = vertexCLRS.bellmanFordDist;
        vertexCLRS1.visited = vertexCLRS.visited;
        vertexCLRS1.connectedID = vertexCLRS.connectedID;


        return vertexCLRS1;
    }

}