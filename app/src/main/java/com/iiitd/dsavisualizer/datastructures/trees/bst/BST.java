package com.iiitd.dsavisualizer.datastructures.trees.bst;

import com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationState;
import com.iiitd.dsavisualizer.datastructures.trees.TreeSequence;

import java.util.ArrayList;

public class BST {

    private BSTNode root;
    public int lastElementIndex;
    TreeSequence treeSequence;
    int s;
    ArrayList<TreeAnimationState> animationStates;

    public BST() {
        root = null;
        treeSequence = new TreeSequence();
        lastElementIndex = -1;
    }

    public ArrayList<TreeAnimationState> insert(int key){
        s = 0;
        animationStates = new ArrayList<>();
        root = _insert(root, key, 8, 4);
        treeSequence = new TreeSequence(animationStates);
        return animationStates;
    }

    public void inorder(){
        _inorder(root);
    }

    public ArrayList<TreeAnimationState> delete(int key){
        animationStates = new ArrayList<>();
        root = _delete(root, key, 8, 4);
        treeSequence = new TreeSequence(animationStates);

//        _inorder(this.root);
        return animationStates;
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

    private BSTNode _delete(BSTNode bstNode, int key, int index, int level){

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
            }
            else if(bstNode.left == null && bstNode.right != null){
                System.out.println("Right copy");
            }
            else if(bstNode.left != null && bstNode.right == null){
                System.out.println("Left copy");
            }
            else if(bstNode.left != null && bstNode.right != null){
                System.out.println("Right min element");
            }

            if (bstNode.left == null){
                System.out.println("LEFT CHILD NULL = ");
                BSTNode temp = bstNode.right;
                return temp;
            }
            else if (bstNode.right == null){
                System.out.println("RIGHT CHILD NULL = ");
                BSTNode temp = bstNode.left;
                return temp;
            }

            System.out.println("END CODE");
            BSTNode temp = _minValueNode(bstNode.right);
            System.out.println("Right suc :: " + temp.key);
            bstNode.key = temp.key;
            bstNode.count = temp.count;
            temp.count = 1;
            bstNode.right = _delete(bstNode.right, temp.key, index + level, level / 2);
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
