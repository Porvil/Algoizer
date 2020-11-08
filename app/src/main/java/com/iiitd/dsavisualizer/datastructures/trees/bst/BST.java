package com.iiitd.dsavisualizer.datastructures.trees.bst;

public class BST {

    private BSTNode root;
    public int lastElementIndex;

    public BST() {
        root = null;
        lastElementIndex = -1;
    }

    public int insert(int key){
        root = _insert(root, key, 8, 4);
        return lastElementIndex;
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
        System.out.println("index = "+index+"   level = ="+ level);

        if (bstNode == null){
            lastElementIndex = index;
            return new BSTNode(key);
        }

        if (key == bstNode.key){
            lastElementIndex = index;
            bstNode.count++;
            return bstNode;
        }

        if(level == 0){
            lastElementIndex = -1;
            return bstNode;
        }

        if (key < bstNode.key)
            bstNode.left = _insert(bstNode.left, key, index-level, level/2);
        else
            bstNode.right = _insert(bstNode.right, key, index+level, level/2);

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

}
