package com.iiitd.dsavisualizer.datastructures.trees.bst;

import android.util.Pair;

import com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationState;
import com.iiitd.dsavisualizer.datastructures.trees.TreeElementAnimationData;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayout;
import com.iiitd.dsavisualizer.datastructures.trees.TreeSequence;
import static com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationStateType.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// BST Backend
public class BST {

    private BSTNode root;
    private final int baseIndex = TreeLayout.baseIndex;
    private final int baseLevel = TreeLayout.baseLevel;
    public TreeSequence treeSequence;
    private ArrayList<TreeAnimationState> treeAnimationStates;

    public BST() {
        root = null;
        treeSequence = new TreeSequence();
    }

    public ArrayList<TreeAnimationState> insert(int key){
        treeAnimationStates = new ArrayList<>();
        root = _insert(root, key, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
        return treeAnimationStates;
    }

    private BSTNode _insert(BSTNode bstNode, int key, int index, int level){
        if (bstNode == null){
            TreeAnimationState treeAnimationState = new TreeAnimationState(INSERT, BSTInfo.getInsertString(key, 1));
            treeAnimationState.add(new TreeElementAnimationData(key, 1, index));
            treeAnimationStates.add(treeAnimationState);
            return new BSTNode(key);
        }

        if (key == bstNode.key){
            bstNode.count++;
            TreeAnimationState treeAnimationState = new TreeAnimationState(INSERT, BSTInfo.getInsertString(key, bstNode.count));
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            return bstNode;
        }

        if(level == 0){
            treeAnimationStates.clear();
            TreeAnimationState treeAnimationState = new TreeAnimationState(NO_SPACE, BSTInfo.getNoSpaceString());
            treeAnimationState.add(new TreeElementAnimationData(-1, -1, -1));
            treeAnimationStates.add(treeAnimationState);
            return bstNode;
        }

        TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, BSTInfo.getSearchString(key, bstNode.key));
        treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
        treeAnimationStates.add(treeAnimationState);

        if (key < bstNode.key) {
            bstNode.left = _insert(bstNode.left, key, index - level, level / 2);
        }
        else {
            bstNode.right = _insert(bstNode.right, key, index + level, level / 2);
        }

        return bstNode;
    }

    public void delete(int key){
        treeAnimationStates = new ArrayList<>();
        root = _delete(root, key, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
    }

    private BSTNode _delete(BSTNode bstNode, int key, int index, int level){
        if (bstNode == null) {
            System.out.println("NULL Node, Not found");
            TreeAnimationState treeAnimationState = new TreeAnimationState(NOT_FOUND, BSTInfo.getNotFoundString(key));
            treeAnimationState.add(new TreeElementAnimationData(-1,-1));
            treeAnimationStates.add(treeAnimationState);
            return bstNode;
        }

        if (key < bstNode.key) {
            System.out.println("LESS KEY = " + bstNode.key);
            TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, BSTInfo.getSearchString(key, bstNode.key));
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            bstNode.left = _delete(bstNode.left, key, index - level, level / 2);
        }
        else if (key > bstNode.key) {
            System.out.println("More KEY = " + bstNode.key);
            TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, BSTInfo.getSearchString(key, bstNode.key));
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            bstNode.right = _delete(bstNode.right, key, index + level, level / 2);
        }
        else{
            if (bstNode.count > 1){
                bstNode.count--;
                TreeAnimationState treeAnimationState = new TreeAnimationState(DELETE_DECREASE, BSTInfo.getDeleteString(key, bstNode.count));
                treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
                treeAnimationStates.add(treeAnimationState);
                System.out.println("Count decreased = " + bstNode.key + " : " + bstNode.count);
                return bstNode;
            }

            System.out.println(bstNode.key +"  ---===== " + bstNode.count);

            if (bstNode.left == null && bstNode.right == null){
                System.out.println("Simple delete");
                TreeAnimationState treeAnimationState = new TreeAnimationState(DELETE_NO_CHILD, BSTInfo.getDeleteString(key, 1));
                treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
                treeAnimationStates.add(treeAnimationState);
                return null;
            }
            else if(bstNode.left == null && bstNode.right != null){
                System.out.println("Right copy");
                TreeAnimationState step1 = new TreeAnimationState(DELETE_1_CHILD, BSTInfo.getDeleteString(key, 1));
                TreeAnimationState step2 = new TreeAnimationState(COPY_AND_MOVE, BSTInfo.getRightSubtreeString());
                TreeAnimationState step3 = new TreeAnimationState(MOVE_BACK, BSTInfo.getRightSubtreeString());
                step1.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));

                BSTNode temp = bstNode.right;
                Queue<BSTNode> queue = new LinkedList<>();
                Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();

                System.out.println("in = " + (index+level));
                queue.add(temp);
                queue2.add(new Pair(index+level, index));

                while(queue.size() > 0 ) {
                    BSTNode node = queue.remove();
                    Pair<Integer, Integer> pair = queue2.remove();
                    int currentIndex = pair.first;
                    int parentIndex = pair.second;
                    System.out.println(node.key + " | " + pair);
                    step2.add(new TreeElementAnimationData(node.key, node.count, currentIndex, parentIndex));
                    step3.add(new TreeElementAnimationData(node.key, node.count, currentIndex, parentIndex));

                    if (node.left != null) {
                        queue.add(node.left);
                        queue2.add(new Pair(TreeLayout.childs[currentIndex].first, TreeLayout.childs[parentIndex].first));

                    }
                    if (node.right != null) {
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
                TreeAnimationState step1 = new TreeAnimationState(DELETE_1_CHILD, BSTInfo.getDeleteString(key, 1));
                TreeAnimationState step2 = new TreeAnimationState(COPY_AND_MOVE, BSTInfo.getLeftSubtreeString());
                TreeAnimationState step3 = new TreeAnimationState(MOVE_BACK, BSTInfo.getLeftSubtreeString());
                step1.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));

                BSTNode temp = bstNode.left;
                Queue<BSTNode> queue = new LinkedList<>();
                Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();

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

                TreeAnimationState step1 = new TreeAnimationState(DELETE_NO_CHILD, BSTInfo.getDeleteString(key, 1));

                step1.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
                treeAnimationStates.add(step1);

                while (current.left != null) {
                    TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, BSTInfo.getFindSuccessorString(key));
                    treeAnimationState.add(new TreeElementAnimationData(current.key, current.count, curIndex));
                    current = current.left;
                    curLevel /= 2;
                    curIndex = curIndex - curLevel;
                    treeAnimationStates.add(treeAnimationState);
                }
                TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, BSTInfo.getFoundSuccessorString(key, current.key));
                treeAnimationState.add(new TreeElementAnimationData(current.key, current.count, curIndex));
                treeAnimationStates.add(treeAnimationState);

                BSTNode temp = current;

                TreeAnimationState step2 = new TreeAnimationState(COPY_AND_MOVE, BSTInfo.getMoveUpString(temp.key, bstNode.key));
                TreeAnimationState step3 = new TreeAnimationState(MOVE_BACK, BSTInfo.getMoveUpString(temp.key, bstNode.key));

                step2.add(new TreeElementAnimationData(temp.key, temp.count, curIndex, index));
                step3.add(new TreeElementAnimationData(temp.key, temp.count, curIndex, index));

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

    public void search(int key){
        treeAnimationStates = new ArrayList<>();
        _search(root, key, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
    }

    private void  _search(BSTNode bstNode, int key, int index, int level){
        if (bstNode == null) {
            System.out.println("NULL Node, Not found");
            TreeAnimationState treeAnimationState = new TreeAnimationState(NOT_FOUND, BSTInfo.getNotFoundString(key));
            treeAnimationState.add(new TreeElementAnimationData(-1,-1));
            treeAnimationStates.add(treeAnimationState);
            return;
        }

        if (key < bstNode.key) {
            System.out.println("LESS KEY = " + bstNode.key);
            TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, BSTInfo.getSearchString(key, bstNode.key));
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            _search(bstNode.left, key, index - level, level / 2);
        }
        else if (key > bstNode.key) {
            System.out.println("More KEY = " + bstNode.key);
            TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, BSTInfo.getSearchString(key, bstNode.key));
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            _search(bstNode.right, key, index + level, level / 2);
        }
        else{
            TreeAnimationState treeAnimationState = new TreeAnimationState(FOUND, BSTInfo.getFoundString(key));
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
        }

    }

    public void inorder(){
        treeAnimationStates = new ArrayList<>();
        _inorder(root, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
    }

    private void _inorder(BSTNode bstNode, int index, int level){
        System.out.println("inorder");
        if (bstNode != null){
            _inorder(bstNode.left, index - level, level / 2);
            TreeAnimationState treeAnimationState = new TreeAnimationState(ORDER_TRAVERSAL, BSTInfo.getOrderTraversalString("In"));
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            System.out.print(bstNode.key + "(" + bstNode.count + ") ");
            _inorder(bstNode.right, index + level, level / 2);
        }
    }

    public void preorder(){
        treeAnimationStates = new ArrayList<>();
        _preorder(root, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
    }

    private void _preorder(BSTNode bstNode, int index, int level){
        System.out.println("preorder");
        if (bstNode != null){
            TreeAnimationState treeAnimationState = new TreeAnimationState(ORDER_TRAVERSAL, BSTInfo.getOrderTraversalString("Pre"));
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            System.out.print(bstNode.key + "(" + bstNode.count + ") ");
            _preorder(bstNode.left, index - level, level / 2);
            _preorder(bstNode.right, index + level, level / 2);
        }
    }

    public void postorder(){
        treeAnimationStates = new ArrayList<>();
        _postorder(root, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
    }

    private void _postorder(BSTNode bstNode, int index, int level){
        System.out.println("postorder");
        if (bstNode != null){
            _postorder(bstNode.left, index - level, level / 2);
            _postorder(bstNode.right, index + level, level / 2);
            TreeAnimationState treeAnimationState = new TreeAnimationState(ORDER_TRAVERSAL, BSTInfo.getOrderTraversalString("Post"));
            treeAnimationState.add(new TreeElementAnimationData(bstNode.key, bstNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            System.out.print(bstNode.key + "(" + bstNode.count + ") ");
        }
    }

}