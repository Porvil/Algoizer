package com.iiitd.dsavisualizer.datastructures.graphs;

import java.util.HashMap;
import java.util.Map;

public class GraphOld {

    int noOfVertices;
    HashMap<Integer, VertexOld> vertices;

    public GraphOld() {
        noOfVertices = 0;
        vertices = new HashMap<>();
    }

    /*
        Returns 0 - If VertexOld Creation Is Successful.
                1 - If VertexOld Is Already Present.
    */
    public VertexOld createVertex(int name, int row, int col){
        if(vertices.containsKey(name) )
            return null;

        noOfVertices++;

        VertexOld v = new VertexOld(name, row, col);
        vertices.put(name, v);
        return v;

    }

    /*
        Returns 0 - If EdgeOld Creation Is Successful.
                1 - If Source VertexOld Is Not Present In GraphOld.
                2 - If Destination VertexOld Is Not Present In GraphOld.
                3 - If Source And Destination VertexOld Are Not Present In GraphOld.
                4 - If EdgeOld Is Already Present.
    */
    public int createEdge(int source, int destination, double weight){
        int errorCode = 0;
        VertexOld sourceV = vertices.get(source);
        VertexOld destinationV = vertices.get(destination);

        if(sourceV == null)
            errorCode += 1;

        if(destinationV == null)
            errorCode += 2;

        if(errorCode != 0)
            return errorCode;

        boolean isSuccess = sourceV.createEdge(new EdgeOld(destinationV, weight));
        if(isSuccess)
            return errorCode;

        return 4;

    }

    public void print(){
        for(Map.Entry<Integer, VertexOld> vertex : vertices.entrySet() ){
            vertex.getValue().printEdges();
        }
    }

}
