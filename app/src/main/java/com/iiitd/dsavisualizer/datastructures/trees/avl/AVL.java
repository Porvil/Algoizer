package com.iiitd.dsavisualizer.datastructures.trees.avl;

import android.util.Pair;

import com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationState;
import com.iiitd.dsavisualizer.datastructures.trees.TreeElementAnimationData;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayout;
import com.iiitd.dsavisualizer.datastructures.trees.TreeSequence;
import com.iiitd.dsavisualizer.datastructures.trees.bst.BSTNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AVL {

    private AVLNode root;
    private final int baseIndex = TreeLayout.baseIndex;
    private final int baseLevel = TreeLayout.baseLevel;
    public TreeSequence treeSequence;
    private ArrayList<TreeAnimationState> treeAnimationStates;

    public AVL() {
        root = null;
        treeSequence = new TreeSequence();
    }

    int height(AVLNode avlNode) {
        if (avlNode == null) {
            return 0;
        }

        return avlNode.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

//    public boolean search(int key) {
//        return search(root, key);
//    }
//
//    private boolean search(AVLNode avlNode, int key) {
//        boolean found = false;
//        while ((avlNode != null) && !found) {
//            int rval = avlNode.key;
//            if (key < rval) {
//                avlNode = avlNode.left;
//            }
//            else if (key > rval) {
//                avlNode = avlNode.right;
//            }
//            else {
//                found = true;
//                break;
//            }
//            found = search(avlNode, key);
//        }
//        return found;
//    }

//    public int largest_SmallerEqual(int key) {
//        if (root != null) {
//            return largest_SmallerEqual(root, key);
//        }
//
//        return -1;
//    }
//
//    private int largest_SmallerEqual(AVLNode avlNode, int key) {
//        // Base cases
//        if (avlNode == null) {
//            return -1;
//        }
//        if (avlNode.key == key) {
//            return key;
//        }
//        // If avlNode's value is smaller, try in right
//        // subtree
//        else if (avlNode.key < key) {
//            int k = largest_SmallerEqual(avlNode.right, key);
//            if (k == -1) {
//                return avlNode.key;
//            } else {
//                return k;
//            }
//        } // If avlNode's key is greater, return value
//        // from left subtree.
//        else if (avlNode.key > key) {
//            return largest_SmallerEqual(avlNode.left, key);
//        }
//        return -1;
//    }

    public ArrayList<TreeAnimationState> insert(int key) {
        treeAnimationStates = new ArrayList<>();
        root = insert(root, key, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
        return treeAnimationStates;
    }

    private AVLNode insert(AVLNode avlNode, int key, int index, int level) {
        if (avlNode == null) {
            System.out.println("Last Node ez :p");
            TreeAnimationState treeAnimationState = new TreeAnimationState("I");
            treeAnimationState.add(new TreeElementAnimationData(key, 1, index));
            treeAnimationStates.add(treeAnimationState);
            return (new AVLNode(key));
        }

        TreeAnimationState treeAnimationState = new TreeAnimationState("S");
        treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
        treeAnimationStates.add(treeAnimationState);

        if (key < avlNode.key) {
            System.out.println("Going Left");
            avlNode.left = insert(avlNode.left, key, index - level, level / 2);
        }
        else if (key > avlNode.key) {
            System.out.println("Going Right");
            avlNode.right = insert(avlNode.right, key, index + level, level / 2);
        }
        else {
            System.out.println("Same value, just return");
            return avlNode;
        }

        avlNode.height = 1 + max(height(avlNode.left), height(avlNode.right));

        int diff = 0;
        if (avlNode == null) {
            diff = 0;
        }
        else {
            diff = height(avlNode.left) - height(avlNode.right);
        }

        System.out.println("diff = " + diff);
        return rotate(avlNode, key, diff, index, level);
    }

//    public void delete(int value) {
//        root = delete(root, value);
//    }
//
//    private AVLNode delete(AVLNode node, int data) {
//        if (node == null) {
//            return node;
//        }
//        if (data < node.key) {
//            node.left = delete(node.left, data);
//        }
//        else if (data > node.key) {
//            node.right = delete(node.right, data);
//        }
//        else {
//            if ((node.left == null) || (node.right == null)) {
//                AVLNode temp = null;
//                if (temp == node.left) {
//                    temp = node.right;
//                }
//                else {
//                    temp = node.left;
//                }
//
//                if (temp == null) {//NO CHILD NODES
//                    temp = node;
//                    node = null;
//                }
//                else //ONLY ONE CHILD
//                {
//                    node = temp;
//                }
//            }
//            else {
//                AVLNode temp = minimum(node.right);
//                node.key = temp.key;
//                node.right = delete(node.right, temp.key);
//            }
//        }
//
//        if (node == null) {
//            return node;
//        }
//
//        node.height = max(height(node.left), height(node.right)) + 1;
//
//        int diff;
//        if (node == null) {
//            diff = 0;
//        }
//        else {
//            diff = height(node.left) - height(node.right);
//        }
//
//        return rotate(node, data, diff);
//    }

    AVLNode rotate(AVLNode avlNode, int key, int diff, int index, int level) {
        //LL
        if (diff > 1 && key < avlNode.left.key) {
            System.out.println("Left Left");
            return rightRotate(avlNode, index, level);
        }

        // RR
        if (diff < -1 && key > avlNode.right.key) {
            System.out.println("Right Right");
            return leftRotate(avlNode);
        }

        // LR
        if (diff > 1 && key > avlNode.left.key) {
            System.out.println("Left Right");
            avlNode.left = leftRotate(avlNode.left);
            return rightRotate(avlNode, index, level);
        }

        // RL
        if (diff < -1 && key < avlNode.right.key) {
            System.out.println("Right Left");
            avlNode.right = rightRotate(avlNode.right, index, level);
            return leftRotate(avlNode);
        }

        System.out.println("no rotation needed :p");
        return avlNode;
    }

    AVLNode rightRotate(AVLNode avlNode, int index, int level) {

        AVLNode left = avlNode.left;
        AVLNode temp = left.right;

        AVLNode ls = null;
        AVLNode rs = null;

        System.out.println("rotation needed at index = " + index + " | level = " + level);
        // l = left
        // a = node where rotation needed
        // ls = left subtree of "l"
        // rs = right subtree of "a"
        // ts = right subtree of "l"

        int index_a = index;
        int index_l = TreeLayout.childs[index_a].first;
        int index_ls = TreeLayout.childs[index_l].first;
        int index_rs = TreeLayout.childs[index_a].second;
        int index_ts = TreeLayout.childs[index_l].second;

        int new_index_a = TreeLayout.childs[index_a].second;
        int new_index_l = TreeLayout.parents[index_l];
        int new_index_ls = TreeLayout.parents[index_ls];
        int new_index_rs = TreeLayout.childs[index_rs].second;
        int new_index_ts = index_ts == 3 ? 5 : index_ts == 6 ? 10 : -1;
        //remove this hardcoding later

        System.out.println("a  | " + index_a + " -> " + new_index_a);
        System.out.println("l  | " + index_l + " -> " + new_index_l);
        System.out.println("ls | " + index_ls + " -> " + new_index_ls);
        System.out.println("rs | " + index_rs + " -> " + new_index_rs);
        System.out.println("ts | " + index_ts + " -> " + new_index_ts);

        TreeAnimationState step2 = new TreeAnimationState("CM");
        TreeAnimationState step3 = new TreeAnimationState("MB");

        // a
        step2.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index_a, new_index_a));
        step3.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index_a, new_index_a));

        //l
        step2.add(new TreeElementAnimationData(left.key, left.count, index_l, new_index_l));
        step3.add(new TreeElementAnimationData(left.key, left.count, index_l, new_index_l));

        //ls
        if(left.left != null) {
            ls = left.left;
//            step2.add(new TreeElementAnimationData(ls.key, ls.count, index_ls, new_index_ls));
//            step3.add(new TreeElementAnimationData(ls.key, ls.count, index_ls, new_index_ls));

            int level1 = level / (2);
            int index1 = index_l;

            AVLNode temp1 = left.left;
            Queue<AVLNode> queue = new LinkedList<>();
            Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();

            System.out.println("in = " + (index1-level1));
            queue.add(temp1);
            queue2.add(new Pair(index1-level1, index1));

            while(queue.size() > 0 ){
                AVLNode node = queue.remove();
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

        }

        //rs
        if(avlNode.right != null) {
            rs = avlNode.right;
            step2.add(new TreeElementAnimationData(rs.key, rs.count, index_rs, new_index_rs));
            step3.add(new TreeElementAnimationData(rs.key, rs.count, index_rs, new_index_rs));
        }

        //ts
        if(temp != null) {
            step2.add(new TreeElementAnimationData(temp.key, temp.count, index_ts, new_index_ts));
            step3.add(new TreeElementAnimationData(temp.key, temp.count, index_ts, new_index_ts));
        }

        treeAnimationStates.add(step2);
        treeAnimationStates.add(step3);



        // Perform rotation
        left.right = avlNode;
        avlNode.left = temp;

        // Update heights
        avlNode.height = max(height(avlNode.left), height(avlNode.right)) + 1;
        left.height = max(height(left.left), height(left.right)) + 1;

        return left;
    }

    AVLNode leftRotate(AVLNode avlNode) {
        AVLNode right = avlNode.right;
        AVLNode temp = right.left;

        // Perform rotation
        right.left = avlNode;
        avlNode.right = temp;

        // Update heights
        avlNode.height = max(height(avlNode.left), height(avlNode.right)) + 1;
        right.height = max(height(right.left), height(right.right)) + 1;

        return right;
    }

    AVLNode minimum(AVLNode avlNode) {
        AVLNode iterate = avlNode;

        while (iterate.left != null) {
            iterate = iterate.left;
        }

        return iterate;
    }

    public void preDisp() {
        if (root != null) {
            pre(root);
        } else {
            System.out.print(-1);
        }
        System.out.println();
    }

    private void pre(AVLNode avlNode) {
        if (avlNode != null) {
            System.out.print(avlNode.key + " ");
            pre(avlNode.left);
            pre(avlNode.right);
        }
    }

    public void postDisp() {
        if (root != null) {
            post(root);
        } else {
            System.out.print(-1);
        }
        System.out.println();
    }

    private void post(AVLNode avlNode) {
        if (avlNode != null) {
            post(avlNode.left);
            post(avlNode.right);
            System.out.print(avlNode.key + " ");
        }
    }

    public void inDisp() {
        if (root != null) {
            in(root);
        } else {
            System.out.print(-1);
        }
        System.out.println();
    }

    private void in(AVLNode avlNode) {
        if (avlNode != null) {
            in(avlNode.left);
            System.out.print(avlNode.key + " ");
            in(avlNode.right);
        }
    }

}
