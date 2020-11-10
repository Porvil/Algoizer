package com.iiitd.dsavisualizer.datastructures.trees.bst;

import com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationState;
import com.iiitd.dsavisualizer.datastructures.trees.TreeSequence;

import java.util.ArrayList;

public class BST {

    private BSTNode root;
    public int lastElementIndex;
    int s;
    TreeSequence treeSequence;
    ArrayList<TreeAnimationState> animationStates;

    public BST() {
        root = null;
        treeSequence = new TreeSequence();
        lastElementIndex = -1;
    }

    public ArrayList<TreeAnimationState> insert(int key){
        s = 0;
        animationStates = new ArrayList<>();
//        treeSequence = new TreeSequence();
        root = _insert(root, key, 8, 4);
        treeSequence = new TreeSequence(animationStates);
        return animationStates;
    }

    public void inorder(){
        _inorder(root);
    }

    public void delete(int key){
        root = _delete(root, key);
    }

    private void _inorder(BSTNode bstNode){
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
            TreeAnimationState treeAnimationState = new TreeAnimationState(key, 1, lastElementIndex);
            System.out.println("==++ "  + treeAnimationState);
            animationStates.add(treeAnimationState);
            return new BSTNode(key);
        }

        if (key == bstNode.key){
            lastElementIndex = index;
            bstNode.count++;
            s = bstNode.count;
            animationStates.add(new TreeAnimationState(bstNode.key, bstNode.count, lastElementIndex));
            return bstNode;
        }

        if(level == 0){
            lastElementIndex = -1;
            animationStates.clear();
//            sequence.add()
            animationStates.add(new TreeAnimationState(-1, -1, lastElementIndex));
            return bstNode;
        }

        animationStates.add(new TreeAnimationState(bstNode.key, bstNode.count, index));
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

    private BSTNode _delete(BSTNode bstNode, int key){
        if (bstNode == null)
            return bstNode;

        if (key < bstNode.key)
            bstNode.left = _delete(bstNode.left, key);
        else if (key > bstNode.key)
            bstNode.right = _delete(bstNode.right, key);
        else{
            if (bstNode.count > 1){
                bstNode.count--;
                return bstNode;
            }

            if (bstNode.left == null){
                BSTNode temp = bstNode.right;
                return temp;
            }
            else if (bstNode.right == null){
                BSTNode temp = bstNode.left;
                return temp;
            }

            BSTNode temp = _minValueNode(bstNode.right);
            bstNode.key = temp.key;
            bstNode.right = _delete(bstNode.right, temp.key);
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
