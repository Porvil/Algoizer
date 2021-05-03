package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;

import com.iiitd.dsavisualizer.R;
import com.iiitd.dsavisualizer.utility.UtilUI;

import static com.iiitd.dsavisualizer.datastructures.graphs.GraphControlState.*;
import static com.iiitd.dsavisualizer.datastructures.graphs.GraphViewState.*;

// This class maintains graph's control states and graph' grid toggle states
public class GraphControls {

    Context context;

    View inc_graphcontrols;
    FrameLayout fl_graphcontrols_view;
    FrameLayout fl_graphcontrols_node;
    FrameLayout fl_graphcontrols_edge;

    GraphControlState graphControlState;
    GraphControlState viewState;
    GraphControlState vertexState;
    GraphControlState edgeState;

    GraphViewState graphViewState;

    int startEdge;

    Drawable view_off;
    Drawable view_on;
    Drawable vertexAdd_off;
    Drawable vertexAdd_on;
    Drawable vertexRemove_off;
    Drawable vertexRemove_on;
    Drawable edgeAdd_off;
    Drawable edgeAdd_on;
    Drawable edgeRemove_off;
    Drawable edgeRemove_on;

    public GraphControls(Context context, View inc_graphcontrols) {
        this.context = context;
        this.inc_graphcontrols = inc_graphcontrols;
        this.fl_graphcontrols_view = inc_graphcontrols.findViewById(R.id.fl_graphcontrols_view);
        this.fl_graphcontrols_node = inc_graphcontrols.findViewById(R.id.fl_graphcontrols_node);
        this.fl_graphcontrols_edge = inc_graphcontrols.findViewById(R.id.fl_graphcontrols_edge);

        this.graphControlState = VIEW;
        this.viewState = VIEW;
        this.vertexState = VERTEX_ADD;
        this.edgeState = EDGE_ADD;

        this.graphViewState = GRAPH_ONLY;

        this.startEdge = -1;

        initDrawables();
    }

    public GraphControlState getCurrentState(){
        return graphControlState;
    }

    public void updateStateWithEnum(GraphControlState _graphControlState){
        if(_graphControlState == VIEW){
            graphControlState = viewState;
            startEdge = -1;
        }
        else if (_graphControlState == VERTEX_ADD || _graphControlState == VERTEX_REMOVE) {
            if(graphControlState == VERTEX_ADD ||
                    graphControlState == VERTEX_REMOVE) {
                changeVertexState();
            }
            graphControlState = vertexState;
            startEdge = -1;
        }
        else if (_graphControlState == EDGE_ADD || _graphControlState == EDGE_REMOVE) {
            if(graphControlState == EDGE_ADD ||
                    graphControlState == EDGE_REMOVE) {
                changeEdgeState();
                startEdge = -1;
            }
            graphControlState = edgeState;
        }

    }

    public void updateState(View view){
        int checkedId = view.getId();
        if (checkedId == R.id.cl_graphcontrols_view) {
            graphControlState = viewState;
            startEdge = -1;
        }
        else if (checkedId == R.id.cl_graphcontrols_node) {
            if(graphControlState == VERTEX_ADD ||
                    graphControlState == VERTEX_REMOVE) {
                changeVertexState();
            }
            graphControlState = vertexState;
            startEdge = -1;
        }
        else if (checkedId == R.id.cl_graphcontrols_edge) {
            if(graphControlState == EDGE_ADD ||
                    graphControlState == EDGE_REMOVE) {
                changeEdgeState();
                startEdge = -1;
            }
            graphControlState = edgeState;
        }

    }

    public void updateDrawables(){
        Drawable view = view_off;
        Drawable vertex = vertexState == VERTEX_ADD ? vertexAdd_off : vertexRemove_off;
        Drawable edge = edgeState == EDGE_ADD ? edgeAdd_off : edgeRemove_off;

        if(graphControlState == VIEW){
            view = view_on;
        }
        else if(graphControlState == VERTEX_ADD){
            vertex = vertexAdd_on;
        }
        else if(graphControlState == VERTEX_REMOVE){
            vertex = vertexRemove_on;
        }
        else if(graphControlState == EDGE_ADD){
            edge = edgeAdd_on;
        }
        else if(graphControlState == EDGE_REMOVE){
            edge = edgeRemove_on;
        }

        fl_graphcontrols_view.setBackground(view);
        fl_graphcontrols_node.setBackground(vertex);
        fl_graphcontrols_edge.setBackground(edge);
    }

    private void changeVertexState(){
        if(vertexState == VERTEX_ADD){
            vertexState = VERTEX_REMOVE;
        }
        else if(vertexState == VERTEX_REMOVE){
            vertexState = VERTEX_ADD;
        }
    }

    private void changeEdgeState(){
        if(edgeState == EDGE_ADD){
            edgeState = EDGE_REMOVE;
        }
        else if(edgeState == EDGE_REMOVE){
            edgeState = EDGE_ADD;
        }
    }

    public void changeGraphViewState(){
        switch (graphViewState){
            case GRAPH_ONLY:
                graphViewState = GRAPH_GRID;
                break;
            case GRAPH_GRID:
                graphViewState = GRAPH_GRID_COORDINATES;
                break;
            case GRAPH_GRID_COORDINATES:
                graphViewState = GRAPH_ONLY;
                break;
        }
    }

    private void initDrawables() {
        view_off = UtilUI.getDrawable(context, R.drawable.ic_view_off);
        view_on = UtilUI.getDrawable(context, R.drawable.ic_view_on);

        vertexAdd_off = UtilUI.getDrawable(context, R.drawable.ic_vertex_add_off);
        vertexAdd_on = UtilUI.getDrawable(context, R.drawable.ic_vertex_add_on);

        vertexRemove_off = UtilUI.getDrawable(context, R.drawable.ic_vertex_remove_off);
        vertexRemove_on = UtilUI.getDrawable(context, R.drawable.ic_vertex_remove_on);

        edgeAdd_off = UtilUI.getDrawable(context, R.drawable.ic_edge_add_off);
        edgeAdd_on = UtilUI.getDrawable(context, R.drawable.ic_edge_add_on);

        edgeRemove_off = UtilUI.getDrawable(context, R.drawable.ic_edge_delete_off);
        edgeRemove_on = UtilUI.getDrawable(context, R.drawable.ic_edge_delete_on);
    }

    @Override
    public String toString() {
        return "GraphControls{" +
                "graphControlState=" + graphControlState +
                ", viewState=" + viewState +
                ", vertexState=" + vertexState +
                ", edgeState=" + edgeState +
                '}';
    }

}