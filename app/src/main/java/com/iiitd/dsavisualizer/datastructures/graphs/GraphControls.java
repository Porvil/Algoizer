package com.iiitd.dsavisualizer.datastructures.graphs;

import static com.iiitd.dsavisualizer.datastructures.graphs.GraphControlState.*;
import static com.iiitd.dsavisualizer.datastructures.graphs.GraphViewState.*;

public class GraphControls {
    int viewState;//0
    int vertexState;//1
    int edgeState;//2

    int selectedState;

    int startEdge;

    GraphViewState graphViewState;

    public GraphControls() {
        this.viewState = 0;
        this.vertexState = 0;
        this.edgeState = 0;
        this.selectedState = 0;
        this.startEdge = -1;

        this.graphViewState = GRAPH_ONLY;
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

    public void changeGraphViewState(){
        switch (graphViewState){
            case GRAPH_ONLY:
                graphViewState = GraphViewState.GRAPH_GRID;
                break;
            case GRAPH_GRID:
                graphViewState = GraphViewState.GRAPH_GRID_COORDINATES;
                break;
            case GRAPH_GRID_COORDINATES:
                graphViewState = GraphViewState.GRAPH_ONLY;
                break;
        }
    }

}
