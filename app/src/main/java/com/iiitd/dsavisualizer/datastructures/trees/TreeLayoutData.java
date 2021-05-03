package com.iiitd.dsavisualizer.datastructures.trees;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

// TreeLayoutData contains data about all elements in tree view and also maintains tableRows
public class TreeLayoutData {

    Context context;
    ArrayList<List<TreeLayoutElement>> treeLayout;
    ArrayList<TableRow> tableRows;

    public TreeLayoutData(Context context, ArrayList<List<TreeLayoutElement>> treeLayout, ArrayList<TableRow> tableRows) {
        this.context = context;
        this.treeLayout = treeLayout;
        this.tableRows = tableRows;
    }

    public void hideElement(int index){
        Pair<Integer, Integer> curPair = TreeLayout.map.get(index);
        int row = curPair.first;
        int col = curPair.second;
        View view = tableRows.get(row).getChildAt(col);
        TreeLayoutElement layoutElement = treeLayout.get(row).get(col);
        layoutElement.state = NodeState.ELEMENT_HIDDEN;
        view.setVisibility(View.INVISIBLE);

        int parent = TreeLayout.parents[index];
        if(parent > 0 ){
            View arrow = tableRows.get(row-1).getChildAt(col);
            TreeLayoutElement arrowElement = treeLayout.get(row-1).get(col);

            if(layoutElement.state == NodeState.ELEMENT_HIDDEN){
                arrow.setVisibility(View.INVISIBLE);
                arrowElement.state = NodeState.ARROW_HIDDEN;
            }
            else{
                arrow.setVisibility(View.VISIBLE);
                arrowElement.state = NodeState.ARROW_SHOWN;
            }
        }

    }

    public void showElement(int index){
        Pair<Integer, Integer> curPair = TreeLayout.map.get(index);
        int row = curPair.first;
        int col = curPair.second;
        View view = tableRows.get(row).getChildAt(col);
        TreeLayoutElement layoutElement = treeLayout.get(row).get(col);
        layoutElement.state = NodeState.ELEMENT_SHOWN;
        view.setVisibility(View.VISIBLE);

        int parent = TreeLayout.parents[index];
        if(parent > 0 ){
            View arrow = tableRows.get(row-1).getChildAt(col);
            TreeLayoutElement arrowElement = treeLayout.get(row-1).get(col);

            if(layoutElement.state == NodeState.ELEMENT_SHOWN){
                arrow.setVisibility(View.VISIBLE);
                arrowElement.state = NodeState.ARROW_SHOWN;
            }
            else{
                arrow.setVisibility(View.INVISIBLE);
                arrowElement.state = NodeState.ARROW_HIDDEN;
            }
        }

    }

}