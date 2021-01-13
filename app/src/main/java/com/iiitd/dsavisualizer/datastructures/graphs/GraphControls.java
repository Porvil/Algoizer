package com.iiitd.dsavisualizer.datastructures.graphs;

import static com.iiitd.dsavisualizer.datastructures.graphs.GraphControlState.*;

public class GraphControls {
    int viewState;//0
    int vertexState;//1
    int edgeState;//2

    int selectedState;

    int startEdge;

    public GraphControls() {
        this.viewState = 0;
        this.vertexState = 0;
        this.edgeState = 0;
        this.selectedState = 0;
        this.startEdge = -1;
    }

    public GraphControlState getCurrentState(){
        if(selectedState == 1){
            if(vertexState == 0)
                return VERTEX_ADD;
            return VERTEX_REMOVE;
        }
        if(selectedState == 2){
            if(edgeState == 0)
                return EDGE_ADD;
            return EDGE_REMOVE;
        }
        if(selectedState == 0){
            return VIEW;
        }

        return VIEW;
    }

    public void changeState(int which){
        if(which == 0){
            viewState++;
            viewState %= 1;
            startEdge = -1;//resets selected vertex for edge
        }
        if(which == 1){
            vertexState++;
            vertexState %= 2;
            startEdge = -1;//resets selected vertex for edge
        }
        if(which == 2){
            edgeState++;
            edgeState %= 2;
            startEdge = -1;//resets selected vertex for edge
        }
    }

}
