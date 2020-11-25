package com.iiitd.dsavisualizer.datastructures.trees.bst;

import android.util.Pair;

import com.iiitd.dsavisualizer.datastructures.trees.NodeState;
import com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationState;
import com.iiitd.dsavisualizer.datastructures.trees.TreeElementAnimationData;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayout;
import com.iiitd.dsavisualizer.datastructures.trees.TreeSequence;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BST {

    private BSTNode root;
    public int lastElementIndex;
    TreeSequence treeSequence;
    int s;
    ArrayList<TreeElementAnimationData> elementAnimationData;
    ArrayList<TreeAnimationState> treeAnimationStates;
    BSTData[] data;


    public BST() {
        root = null;
        treeSequence = new TreeSequence();
        lastElementIndex = -1;
        data = new BSTData[16];
        for(int i=0;i<16;i++){
            data[i] = new BSTData();
        }
    }

    public ArrayList<TreeAnimationState> insert(int key){
        s = 0;
        treeAnimationStates = new ArrayList<>();
        root = _insert(root, key, 8, 4);
        treeSequence = new TreeSequence(treeAnimationStates);
        return treeAnimationStates;
    }

    public void ins(int val){

        int index = 8;
        int level = 4;

        while(level >= 0){
            if(!data[index].occupied){
                data[index].occupied = true;
                data[index].data = val;
                data[index].state = NodeState.ELEMENT_SHOWN;
                level = -1;
            }else{
                if(val < data[index].data){
                    index -= level;
                    if(level == level/2)
                        level = -1;
                    else
                        level /= 2;
                }
                else if(val > data[index].data){
                    index += level;
                    if(level == level/2)
                        level = -1;
                    else
                        level /= 2;
                }
                else{
                    // Same element
                    level = -1;
                }
            }
        }
    }

    public void del(int val){

        int index = 8;
        int level = 4;

        while(level >= 0){
            if(!data[index].occupied){
                data[index].occupied = true;
                data[index].data = val;
                data[index].state = NodeState.ELEMENT_SHOWN;
                level = -1;
            }else{
                if(val < data[index].data){
                    index -= level;
                    if(level == level/2)
                        level = -1;
                    else
                        level /= 2;
                }
                else if(val > data[index].data){
                    index += level;
                    if(level == level/2)
                        level = -1;
                    else
                        level /= 2;
                }
                else{
                    // Same element
                    level = -1;
                }
            }
        }
    }


    public void print(){
        System.out.println("       " + data[8] + "       ");
        System.out.println("   " + data[4] + "       " + data[12] + "   ");
        System.out.println(" " + data[2] + "   " + data[6] + "   " + data[10] + "   " + data[14] + " ");
        System.out.println(data[1] + " " + data[3] + " " + data[5] + " " + data[7] + " " + data[9] + " " + data[11]
        + " " + data[13] + " " + data[15]);
    }

    public void inorder(){
        _inorder(root);
    }

    public ArrayList<TreeAnimationState> delete(int key){
        treeAnimationStates = new ArrayList<>();
        root = _delete(root, key, 8, 4);
        treeSequence = new TreeSequence(treeAnimationStates);
        return treeAnimationStates;
    }

    public ArrayList<TreeAnimationState> search(int key){
        treeAnimationStates = new ArrayList<>();
        _search(root, key, 8, 4);
        treeSequence = new TreeSequence(treeAnimationStates);

        return treeAnimationStates;
    }

    private void _inorder(BSTNode bstNode){
        System.out.println("inorder");
        if (bstNode != null){
            _inorder(bstNode.left);
            System.out.print(bstNode.key + "(" + bstNode.count + ") ");
            _inorder(bstNode.right);
        }
    }

    private BSTNode _insert(BSTNode bstNode, int key, int index, int level){
//        System.out.println("index = "+index+"   level = ="+ level);

        if (bstNode == null){
            s = 1;
            lastElementIndex = index;
            TreeAnimationState treeAnimationState = new TreeAnimationState("<");
            treeAnimationState.add(new TreeElementAnimationData(key, 1, lastElementIndex));
            treeAnimationStates.add(treeAnimationState);
            return new BSTNode(key);
        }

        if (key == bstNode.key){
            lastElementIndex = index;
            bstNode.count++;
            s = bstNode.count;
            TreeAnimationState treeAnimationState = new TreeAnimationState("<");
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, lastElementIndex));
            treeAnimationStates.add(treeAnimationState);
            return bstNode;
        }

        if(level == 0){
            lastElementIndex = -1;
            treeAnimationStates.clear();
            TreeAnimationState treeAnimationState = new TreeAnimationState("<");
            treeAnimationState.add(new TreeElementAnimationData(-1, -1, lastElementIndex));
            treeAnimationStates.add(treeAnimationState);
            return bstNode;
        }

        TreeAnimationState treeAnimationState = new TreeAnimationState("<");
        treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
        treeAnimationStates.add(treeAnimationState);

        if (key < bstNode.key) {

            bstNode.left = _insert(bstNode.left, key, index - level, level / 2);
        }
        else {
//            animationStates.add(new TreeAnimationState(bstNode.key, bstNode.count, index));
            bstNode.right = _insert(bstNode.right, key, index + level, level / 2);
        }

        return bstNode;
    }

    private BSTNode _minValueNode(BSTNode bstNode){
        BSTNode current = bstNode;

        while (current.left != null)
            current = current.left;

        return current;
    }

    private BSTNode _delete(BSTNode bstNode, int key, int index, int level){

        if (bstNode == null) {
            System.out.println("NULL Node, Not found");
            TreeAnimationState treeAnimationState = new TreeAnimationState("NF");
//            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            return bstNode;
        }

        if (key < bstNode.key) {
            System.out.println("LESS KEY = " + bstNode.key);

            TreeAnimationState treeAnimationState = new TreeAnimationState("S");
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            bstNode.left = _delete(bstNode.left, key, index - level, level / 2);
        }
        else if (key > bstNode.key) {
            System.out.println("More KEY = " + bstNode.key);
            TreeAnimationState treeAnimationState = new TreeAnimationState("S");
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            bstNode.right = _delete(bstNode.right, key, index + level, level / 2);
        }
        else{
            if (bstNode.count > 1){
                bstNode.count--;
                TreeAnimationState treeAnimationState = new TreeAnimationState("C");
                treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
                treeAnimationStates.add(treeAnimationState);
                System.out.println("Count decreased = " + bstNode.key + " : " + bstNode.count);
                return bstNode;
            }

            System.out.println(bstNode.key +"  ---===== " + bstNode.count);

            if (bstNode.left == null && bstNode.right == null){
                System.out.println("Simple delete");
                TreeAnimationState treeAnimationState = new TreeAnimationState("1");
                treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
                treeAnimationStates.add(treeAnimationState);
                return null;
            }
            else if(bstNode.left == null && bstNode.right != null){
                System.out.println("Right copy");
//                elementAnimationData.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index, "DEL"));
//                elementAnimationData.add(new TreeElementAnimationData(bstNode.right.key, bstNode.right.count, index+level, "Mov up recur"));

                TreeAnimationState step1 = new TreeAnimationState("D");
                TreeAnimationState step2 = new TreeAnimationState("CM");
                TreeAnimationState step3 = new TreeAnimationState("MB");
                step1.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index, "DEL"));
                //Recurse on child to move up 1 level

                int parent = index;
                BSTNode temp = bstNode.right;
                Queue<BSTNode> queue = new LinkedList<>();
                Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();
                //         myIndex, parentIndex

                System.out.println("in = " + (index+level));
                queue.add(temp);
                queue2.add(new Pair(index+level, index));

                while(queue.size() > 0 ){
                    BSTNode node = queue.remove();
                    Pair<Integer, Integer> pair = queue2.remove();
                    int currentIndex = pair.first;
                    int parentIndex = pair.second;
                    System.out.println(node.key + " | " + pair);
                    step2.add(new TreeElementAnimationData(node.key, node.count, currentIndex, parentIndex));
                    step3.add(new TreeElementAnimationData(node.key, node.count, currentIndex, parentIndex));
//                    treeAnimationState.add(new TreeElementAnimationData(node.key, node.count, ));

                    if(node.left != null){
                        queue.add(node.left);
                        queue2.add(new Pair(TreeLayout.childs[currentIndex].first, TreeLayout.childs[parentIndex].first));

                    }
                    if(node.right != null){
                        queue.add(node.right);
                        queue2.add(new Pair(TreeLayout.childs[currentIndex].second, TreeLayout.childs[parentIndex].second));
                    }
                }


                treeAnimationStates.add(step1);
                treeAnimationStates.add(step2);
                treeAnimationStates.add(step3);


                return bstNode.right;
            }
            else if(bstNode.left != null && bstNode.right == null){
                System.out.println("Left copy");

                TreeAnimationState step1 = new TreeAnimationState("D");
                TreeAnimationState step2 = new TreeAnimationState("CM");
                TreeAnimationState step3 = new TreeAnimationState("MB");
                step1.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index, "DEL"));
                //Recurse on child to move up 1 level

                int parent = index;
                BSTNode temp = bstNode.left;
                Queue<BSTNode> queue = new LinkedList<>();
                Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();
                //         myIndex, parentIndex

                System.out.println("in = " + (index-level));
                queue.add(temp);
                queue2.add(new Pair(index-level, index));

                while(queue.size() > 0 ){
                    BSTNode node = queue.remove();
                    Pair<Integer, Integer> pair = queue2.remove();
                    int currentIndex = pair.first;
                    int parentIndex = pair.second;
                    System.out.println(node.key + " | " + pair);
                    step2.add(new TreeElementAnimationData(node.key, node.count, currentIndex, parentIndex));
                    step3.add(new TreeElementAnimationData(node.key, node.count, currentIndex, parentIndex));
//                    treeAnimationState.add(new TreeElementAnimationData(node.key, node.count, ));

                    if(node.left != null){
                        queue.add(node.left);
                        queue2.add(new Pair(TreeLayout.childs[currentIndex].first, TreeLayout.childs[parentIndex].first));

                    }
                    if(node.right != null){
                        queue.add(node.right);
                        queue2.add(new Pair(TreeLayout.childs[currentIndex].second, TreeLayout.childs[parentIndex].second));
                    }
                }


                treeAnimationStates.add(step1);
                treeAnimationStates.add(step2);
                treeAnimationStates.add(step3);


                return bstNode.left;
            }
            else if(bstNode.left != null && bstNode.right != null){
                System.out.println("Right min element");

                BSTNode current = bstNode.right;
                System.out.println("index == " + index);
                int curIndex = index+level;
                int curLevel = level;
                while (current.left != null) {
                    current = current.left;
                    curLevel /= 2;
                    curIndex = curIndex - curLevel;
                }


                BSTNode temp = current;
//                BSTNode temp = _minValueNode(bstNode.right);
                System.out.println("Right suc :: " + temp.key + " curindex = " + curIndex);

                TreeAnimationState step1 = new TreeAnimationState("1");
                TreeAnimationState step2 = new TreeAnimationState("CM");
                TreeAnimationState step3 = new TreeAnimationState("MB");

                step1.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
                step2.add(new TreeElementAnimationData(temp.key, temp.count, curIndex, index));
                step3.add(new TreeElementAnimationData(temp.key, temp.count, curIndex, index));

                treeAnimationStates.add(step1);
                treeAnimationStates.add(step2);
                treeAnimationStates.add(step3);

                System.out.println("IN = " + index + " | lev = " + level);
                bstNode.key = temp.key;
                bstNode.count = temp.count;
                temp.count = 1;
                bstNode.right = _delete(bstNode.right, temp.key, index + level, level / 2);
            }

        }

        return bstNode;
    }

    private void  _search(BSTNode bstNode, int key, int index, int level){

        if (bstNode == null) {
            System.out.println("NULL Node, Not found");
            TreeAnimationState treeAnimationState = new TreeAnimationState("NF");
//            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            return;
        }

        if (key < bstNode.key) {
            System.out.println("LESS KEY = " + bstNode.key);

            TreeAnimationState treeAnimationState = new TreeAnimationState("S");
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            _search(bstNode.left, key, index - level, level / 2);
        }
        else if (key > bstNode.key) {
            System.out.println("More KEY = " + bstNode.key);
            TreeAnimationState treeAnimationState = new TreeAnimationState("S");
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            _search(bstNode.right, key, index + level, level / 2);
        }
        else{
            TreeAnimationState treeAnimationState = new TreeAnimationState("F");
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
        }

    }

    private BSTNode __delete(BSTNode bstNode, int key, int index, int level){
        if (bstNode == null) {
            System.out.println("NULL Node, Not found");
            return bstNode;
        }

        if (key < bstNode.key) {
            System.out.println("LESS KEY = " + bstNode.key);
            bstNode.left = _delete(bstNode.left, key, index - level, level / 2);
        }
        else if (key > bstNode.key) {
            System.out.println("More KEY = " + bstNode.key);
            bstNode.right = _delete(bstNode.right, key, index + level, level / 2);
        }
        else{
            if (bstNode.count > 1){
                bstNode.count--;
                System.out.println("Count decreased = " + bstNode.key + " : " + bstNode.count);
                return bstNode;
            }

            System.out.println(bstNode.key +"  ---===== " + bstNode.count);

            if (bstNode.left == null && bstNode.right == null){
                System.out.println("Simple delete");
                return null;
            }
            else if(bstNode.left == null && bstNode.right != null){
                System.out.println("Right copy");
                return bstNode.right;
            }
            else if(bstNode.left != null && bstNode.right == null){
                System.out.println("Left copy");
                return bstNode.left;
            }
            else if(bstNode.left != null && bstNode.right != null){
                System.out.println("Right min element");

                BSTNode temp = _minValueNode(bstNode.right);

                bstNode.key = temp.key;
                bstNode.count = temp.count;
                temp.count = 1;
                bstNode.right = __delete(bstNode.right, temp.key, index + level, level / 2);
            }

        }

        return bstNode;
    }

    public void forward(){
        treeSequence.forward();
    }

    public void backward(){
        treeSequence.backward();
    }

}
