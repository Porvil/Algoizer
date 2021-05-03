package com.iiitd.dsavisualizer.datastructures.trees;

import android.view.View;

// TreeLayoutElement contains data about single element in tree view
public class TreeLayoutElement {
    public int weight;
    public NodeType type;
    public NodeState state;

    public TreeLayoutElement(int weight, NodeType type) {
        this.weight = weight;
        this.type = type;
        if(this.type == NodeType.EMPTY)
            this.state = NodeState.EMPTY;
        else if(this.type == NodeType.ARROW)
            this.state = NodeState.ARROW_HIDDEN;
        else if(this.type == NodeType.ELEMENT)
            this.state = NodeState.ELEMENT_HIDDEN;
    }

    public int getVisibility(){
        if(type == NodeType.EMPTY)
            return View.INVISIBLE;

        switch (state){
            case ARROW_SHOWN:
            case ELEMENT_SHOWN:
                return View.VISIBLE;
            case EMPTY:
            case ARROW_HIDDEN:
            case ELEMENT_HIDDEN:
                return View.INVISIBLE;
        }

        return View.INVISIBLE;
    }

}