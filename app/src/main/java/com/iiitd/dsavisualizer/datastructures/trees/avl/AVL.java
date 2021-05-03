package com.iiitd.dsavisualizer.datastructures.trees.avl;

import android.util.Pair;

import com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationState;
import com.iiitd.dsavisualizer.datastructures.trees.TreeElementAnimationData;
import com.iiitd.dsavisualizer.datastructures.trees.TreeLayout;
import com.iiitd.dsavisualizer.datastructures.trees.TreeSequence;
import static com.iiitd.dsavisualizer.datastructures.trees.TreeAnimationStateType.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// AVL Backend
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

    private int height(AVLNode avlNode) {
        if (avlNode == null) {
            return 0;
        }

        return avlNode.height;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public void search(int key) {
        treeAnimationStates = new ArrayList<>();
        _search(root, key, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
    }

    private void  _search(AVLNode avlNode, int key, int index, int level){
        if (avlNode == null) {
            System.out.println("NULL Node, Not found");
            TreeAnimationState treeAnimationState = new TreeAnimationState(NOT_FOUND, AVLInfo.getNotFoundString(key));
            treeAnimationState.add(new TreeElementAnimationData(-1,-1));
            treeAnimationStates.add(treeAnimationState);
            return;
        }

        if (key < avlNode.key) {
            System.out.println("LESS KEY = " + avlNode.key);
            TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, AVLInfo.getSearchString(key, avlNode.key));
            treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            _search(avlNode.left, key, index - level, level / 2);
        }
        else if (key > avlNode.key) {
            System.out.println("More KEY = " + avlNode.key);
            TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, AVLInfo.getSearchString(key, avlNode.key));
            treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            _search(avlNode.right, key, index + level, level / 2);
        }
        else{
            TreeAnimationState treeAnimationState = new TreeAnimationState(FOUND, AVLInfo.getFoundString(key));
            treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
            treeAnimationStates.add(treeAnimationState);
        }

    }

    public ArrayList<TreeAnimationState> insert(int key) {
        treeAnimationStates = new ArrayList<>();
        root = _insert(root, key, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
        return treeAnimationStates;
    }

    private AVLNode _insert(AVLNode avlNode, int key, int index, int level) {
        if (avlNode == null) {
            TreeAnimationState treeAnimationState = new TreeAnimationState(INSERT, AVLInfo.getInsertString(key, 1));
            treeAnimationState.add(new TreeElementAnimationData(key, 1, index));
            treeAnimationStates.add(treeAnimationState);
            return new AVLNode(key);
        }

        if (key == avlNode.key){
            avlNode.count++;
            TreeAnimationState treeAnimationState = new TreeAnimationState(INSERT, AVLInfo.getInsertString(key, avlNode.count));
            treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            return avlNode;
        }

        if(level == 0){
            treeAnimationStates.clear();
            TreeAnimationState treeAnimationState = new TreeAnimationState(NO_SPACE, AVLInfo.getNoSpaceString());
            treeAnimationState.add(new TreeElementAnimationData(-1, -1, -1));
            treeAnimationStates.add(treeAnimationState);
            return avlNode;
        }

        TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, AVLInfo.getSearchString(key, avlNode.key));
        treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
        treeAnimationStates.add(treeAnimationState);

        if (key < avlNode.key) {
            avlNode.left = _insert(avlNode.left, key, index - level, level / 2);
        }
        else if (key > avlNode.key) {
            avlNode.right = _insert(avlNode.right, key, index + level, level / 2);
        }

        avlNode.height = 1 + max(height(avlNode.left), height(avlNode.right));

        int diff = 0;
        diff = height(avlNode.left) - height(avlNode.right);

        System.out.println("diff = " + diff);
        return rotate(avlNode, key, diff, index, level);
    }

    public void delete(int key){
        treeAnimationStates = new ArrayList<>();
        root = _delete(root, key, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
    }

    private AVLNode _delete(AVLNode avlNode, int key, int index, int level){
        AVLNode ret = null;
        if (avlNode == null) {
            System.out.println("NULL Node, Not found");
            TreeAnimationState treeAnimationState = new TreeAnimationState(NOT_FOUND, AVLInfo.getNotFoundString(key));
            treeAnimationState.add(new TreeElementAnimationData(-1,-1));
            treeAnimationStates.add(treeAnimationState);
            return avlNode;
        }

        if (key < avlNode.key) {
            System.out.println("LESS KEY = " + avlNode.key);

            TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, AVLInfo.getSearchString(key, avlNode.key));
            treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            avlNode.left = _delete(avlNode.left, key, index - level, level / 2);
            ret = avlNode;
        }
        else if (key > avlNode.key) {
            System.out.println("More KEY = " + avlNode.key);
            TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, AVLInfo.getSearchString(key, avlNode.key));
            treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            avlNode.right = _delete(avlNode.right, key, index + level, level / 2);
            ret = avlNode;
        }
        else{
            if (avlNode.count > 1){
                avlNode.count--;
                TreeAnimationState treeAnimationState = new TreeAnimationState(DELETE_DECREASE, AVLInfo.getDeleteString(key, avlNode.count));
                treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
                treeAnimationStates.add(treeAnimationState);
                System.out.println("Count decreased = " + avlNode.key + " : " + avlNode.count);
                return avlNode;
            }

            System.out.println(avlNode.key +"  ---===== " + avlNode.count);

            if (avlNode.left == null && avlNode.right == null){
                System.out.println("Simple delete");
                TreeAnimationState treeAnimationState = new TreeAnimationState(DELETE_NO_CHILD, AVLInfo.getDeleteString(key, 1));
                treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
                treeAnimationStates.add(treeAnimationState);
                ret = null;
            }
            else if(avlNode.left == null && avlNode.right != null){
                System.out.println("Right copy");
                TreeAnimationState step1 = new TreeAnimationState(DELETE_1_CHILD, AVLInfo.getDeleteString(key, 1));
                TreeAnimationState step2 = new TreeAnimationState(COPY_AND_MOVE, AVLInfo.getRightSubtreeString());
                TreeAnimationState step3 = new TreeAnimationState(MOVE_BACK, AVLInfo.getRightSubtreeString());
                step1.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));

                AVLNode temp = avlNode.right;
                Queue<AVLNode> queue = new LinkedList<>();
                Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();

                System.out.println("in = " + (index+level));
                queue.add(temp);
                queue2.add(new Pair(index+level, index));

                while(queue.size() > 0 ) {
                    AVLNode node = queue.remove();
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

                ret = avlNode.right;
            }
            else if(avlNode.left != null && avlNode.right == null){
                System.out.println("Left copy");
                TreeAnimationState step1 = new TreeAnimationState(DELETE_1_CHILD, AVLInfo.getDeleteString(key, 1));
                TreeAnimationState step2 = new TreeAnimationState(COPY_AND_MOVE, AVLInfo.getLeftSubtreeString());
                TreeAnimationState step3 = new TreeAnimationState(MOVE_BACK, AVLInfo.getLeftSubtreeString());
                step1.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));

                AVLNode temp = avlNode.left;
                Queue<AVLNode> queue = new LinkedList<>();
                Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();

                System.out.println("in = " + (index-level));
                queue.add(temp);
                queue2.add(new Pair(index-level, index));

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

                treeAnimationStates.add(step1);
                treeAnimationStates.add(step2);
                treeAnimationStates.add(step3);

                ret = avlNode.left;
            }
            else if(avlNode.left != null && avlNode.right != null){
                System.out.println("Right min element");
                AVLNode current = avlNode.right;
                System.out.println("index == " + index);
                int curIndex = index+level;
                int curLevel = level;

                TreeAnimationState step1 = new TreeAnimationState(DELETE_NO_CHILD, AVLInfo.getDeleteString(key, 1));

                step1.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
                treeAnimationStates.add(step1);

                while (current.left != null) {
                    TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, AVLInfo.getFindSuccessorString(key));
                    treeAnimationState.add(new TreeElementAnimationData(current.key, current.count, curIndex));
                    current = current.left;
                    curLevel /= 2;
                    curIndex = curIndex - curLevel;
                    treeAnimationStates.add(treeAnimationState);
                }
                TreeAnimationState treeAnimationState = new TreeAnimationState(SEARCH, AVLInfo.getFoundSuccessorString(key, current.key));
                treeAnimationState.add(new TreeElementAnimationData(current.key, current.count, curIndex));
                treeAnimationStates.add(treeAnimationState);

                AVLNode temp = current;

                TreeAnimationState step2 = new TreeAnimationState(COPY_AND_MOVE, AVLInfo.getMoveUpString(temp.key, avlNode.key));
                TreeAnimationState step3 = new TreeAnimationState(MOVE_BACK, AVLInfo.getMoveUpString(temp.key, avlNode.key));

                step2.add(new TreeElementAnimationData(temp.key, temp.count, curIndex, index));
                step3.add(new TreeElementAnimationData(temp.key, temp.count, curIndex, index));

                treeAnimationStates.add(step2);
                treeAnimationStates.add(step3);

                System.out.println("IN = " + index + " | lev = " + level);
                avlNode.key = temp.key;
                avlNode.count = temp.count;
                temp.count = 1;
                avlNode.right = _delete(avlNode.right, temp.key, index + level, level / 2);

                ret = avlNode;
            }
        }

        if (ret == null) {
            return null;
        }

        ret.height = max(height(ret.left), height(ret.right)) + 1;

        int diff;
        diff = height(ret.left) - height(ret.right);

        return rotateDel(ret, diff, index, level);
    }

    private AVLNode rotateDel(AVLNode avlNode, int diff, int index, int level) {
        System.out.println("in = " + index + " | lev = " + level);

        if(diff == 2 || diff == -2) {
            TreeAnimationState treeAnimationState = new TreeAnimationState(NULL, "BF = " + diff + " of " + avlNode.key);
            treeAnimationStates.add(treeAnimationState);

            // Node deleted from Right Subtree, diff = +2
            if (diff == 2) {
                int bf = height(avlNode.left.left) - height(avlNode.left.right);
                System.out.println("BF ===================== " + bf);
                if (bf == 1) {
                    System.out.println("Left Left");
                    return rightRotate(avlNode, index, level);
                } else if (bf == -1) {
                    System.out.println("Left Right");
                    avlNode.left = leftRotate(avlNode.left, index - level, level / 2);
                    return rightRotate(avlNode, index, level);
                } else if (bf == 0) {
                    System.out.println("Left Left");
                    return rightRotate(avlNode, index, level);
                }
            }

            // Node deleted from Left Subtree, diff = -2
            if (diff == -2) {
                int bf = height(avlNode.right.left) - height(avlNode.right.right);
                System.out.println("BF ===================== " + bf);
                if (bf == -1) {
                    System.out.println("Right Right");
                    return leftRotate(avlNode, index, level);
                } else if (bf == 1) {
                    System.out.println("Right Left");
                    avlNode.right = rightRotate(avlNode.right, index + level, level / 2);
                    return leftRotate(avlNode, index, level);
                } else if (bf == 0) {
                    System.out.println("Right Right");
                    return leftRotate(avlNode, index, level);
                }
            }
        }

        System.out.println("no rotation needed :p");
        return avlNode;
    }

    private AVLNode rotate(AVLNode avlNode, int key, int diff, int index, int level) {
        System.out.println("in = " + index + " | lev = " + level);

        if(diff >= -1 && diff <= 1){
            System.out.println("no rotation needed :p");
            return avlNode;
        }

        TreeAnimationState treeAnimationState = new TreeAnimationState(NULL, "BF = " + diff + " of " + avlNode.key);
        treeAnimationStates.add(treeAnimationState);

        //LL
        if (diff > 1 && key < avlNode.left.key) {
            System.out.println("Left Left");
            return rightRotate(avlNode, index, level);
        }

        // RR
        if (diff < -1 && key > avlNode.right.key) {
            System.out.println("Right Right");
            return leftRotate(avlNode, index, level);
        }

        // LR
        if (diff > 1 && key > avlNode.left.key) {
            System.out.println("Left Right");
            avlNode.left = leftRotate(avlNode.left, index - level, level/2);
            return rightRotate(avlNode, index, level);
        }

        // RL
        if (diff < -1 && key < avlNode.right.key) {
            System.out.println("Right Left");
            avlNode.right = rightRotate(avlNode.right, index + level, level/2);
            return leftRotate(avlNode, index, level);
        }

        System.out.println("no rotation needed :p");
        return avlNode;
    }

    private AVLNode rightRotate(AVLNode avlNode, int index, int level) {
        AVLNode left = avlNode.left;
        AVLNode temp = left.right;

        System.out.println("rotation needed at index = " + index + " | level = " + level);
        // l = left
        // a = node where rotation needed
        // ls = left subtree of "l"
        // rs = right subtree of "a"
        // ts = right subtree of "l"

        int index_a = index;
        int index_l = TreeLayout.childs[index_a].first;
        int index_rs = TreeLayout.childs[index_a].second;
        int index_ts = TreeLayout.childs[index_l].second;

        int new_index_a = TreeLayout.childs[index_a].second;
        int new_index_l = TreeLayout.parents[index_l];

        TreeAnimationState treeAnimationState = new TreeAnimationState(ROTATION, AVLInfo.getRightRotateString(avlNode.key));
        treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
        treeAnimationStates.add(treeAnimationState);

        System.out.println("a  | " + index_a + " -> " + new_index_a);
        System.out.println("l  | " + index_l + " -> " + new_index_l);

        TreeAnimationState step2 = new TreeAnimationState(COPY_AND_MOVE, AVLInfo.getRightRotateString(avlNode.key));
        TreeAnimationState step3 = new TreeAnimationState(MOVE_BACK, AVLInfo.getRightRotateString(avlNode.key));

        // a
        step2.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index_a, new_index_a));
        step3.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index_a, new_index_a));

        // l
        step2.add(new TreeElementAnimationData(left.key, left.count, index_l, new_index_l));
        step3.add(new TreeElementAnimationData(left.key, left.count, index_l, new_index_l));

        // ls
        if(left.left != null) {
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

        // rs
        if(avlNode.right != null) {
            int level2 = level/2;
            int index2 = index_rs;

            AVLNode temp2 = avlNode.right;
            Queue<AVLNode> queue = new LinkedList<>();
            Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();

            System.out.println("in = " + (index2+level2));
            queue.add(temp2);
            queue2.add(new Pair(index2+level2, index2));

            while(queue.size() > 0 ) {
                AVLNode node = queue.remove();
                Pair<Integer, Integer> pair = queue2.remove();
                int currentIndex = pair.first;
                int parentIndex = pair.second;
                System.out.println(node);
                System.out.println(pair);
                System.out.println(node.key + " | " + pair);
                step2.add(new TreeElementAnimationData(node.key, node.count, parentIndex, currentIndex));
                step3.add(new TreeElementAnimationData(node.key, node.count, parentIndex, currentIndex));

                if (node.left != null) {
                    queue.add(node.left);
                    queue2.add(new Pair(TreeLayout.childs[currentIndex].first, TreeLayout.childs[parentIndex].first));

                }
                if (node.right != null) {
                    queue.add(node.right);
                    queue2.add(new Pair(TreeLayout.childs[currentIndex].second, TreeLayout.childs[parentIndex].second));
                }
            }

        }

        // ts
        if(temp != null) {
            int level3 = level/(2*2);
            int index3 = index_ts;

            int add = index_ts == 3 ? 2 : index_ts == 6 ? 4 : -1;
            System.out.println("add   " + add);
            AVLNode temp3 = temp;
            Queue<AVLNode> queue = new LinkedList<>();
            Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();

            System.out.println("in = " + (index3));
            queue.add(temp3);
            queue2.add(new Pair<>(index3, level3));

            while(queue.size() > 0 ) {
                AVLNode node = queue.remove();
                Pair<Integer, Integer> pair = queue2.remove();
                int currentIndex = pair.first;
                int parentIndex = currentIndex + add;
                step2.add(new TreeElementAnimationData(node.key, node.count, currentIndex, parentIndex));
                step3.add(new TreeElementAnimationData(node.key, node.count, currentIndex, parentIndex));

                if (node.left != null) {
                    queue.add(node.left);
                    queue2.add(new Pair<>(currentIndex - level3, level3/2));

                }
                if (node.right != null) {
                    queue.add(node.right);
                    queue2.add(new Pair<>(currentIndex + level3, level3/2));
                }
            }

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

    private AVLNode leftRotate(AVLNode avlNode, int index, int level) {
        AVLNode right = avlNode.right;
        AVLNode temp = right.left;

        System.out.println("rotation needed at index = " + index + " | level = " + level);
        // r = right
        // a = node where rotation needed
        // ls = left subtree of "a"
        // rs = right subtree of "r"
        // ts = right subtree of "r"

        int index_a = index;
        int index_r = TreeLayout.childs[index_a].second;
        int index_ls = TreeLayout.childs[index_a].first;
        int index_ts = TreeLayout.childs[index_r].first;

        int new_index_a = TreeLayout.childs[index_a].first;
        int new_index_r = TreeLayout.parents[index_r];

        TreeAnimationState treeAnimationState = new TreeAnimationState(ROTATION, AVLInfo.getLeftRotateString(avlNode.key));
        treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
        treeAnimationStates.add(treeAnimationState);

        System.out.println("a  | " + index_a + " -> " + new_index_a);
        System.out.println("l  | " + index_r + " -> " + new_index_r);

        TreeAnimationState step2 = new TreeAnimationState(COPY_AND_MOVE, AVLInfo.getLeftRotateString(avlNode.key));
        TreeAnimationState step3 = new TreeAnimationState(MOVE_BACK, AVLInfo.getLeftRotateString(avlNode.key));

        // a
        step2.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index_a, new_index_a));
        step3.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index_a, new_index_a));

        // r
        step2.add(new TreeElementAnimationData(right.key, right.count, index_r, new_index_r));
        step3.add(new TreeElementAnimationData(right.key, right.count, index_r, new_index_r));

        // ls
        if(avlNode.left != null) {
            int level2 = level/2;
            int index2 = index_ls;

            AVLNode temp2 = avlNode.left;
            Queue<AVLNode> queue = new LinkedList<>();
            Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();

            System.out.println("in = " + (index2-level2));
            queue.add(temp2);
            queue2.add(new Pair(index2-level2, index2));

            while(queue.size() > 0 ) {
                AVLNode node = queue.remove();
                Pair<Integer, Integer> pair = queue2.remove();
                int currentIndex = pair.first;
                int parentIndex = pair.second;
                System.out.println(node);
                System.out.println(pair);
                System.out.println(node.key + " | " + pair);
                step2.add(new TreeElementAnimationData(node.key, node.count, parentIndex, currentIndex));
                step3.add(new TreeElementAnimationData(node.key, node.count, parentIndex, currentIndex));

                if (node.left != null) {
                    queue.add(node.left);
                    queue2.add(new Pair(TreeLayout.childs[currentIndex].first, TreeLayout.childs[parentIndex].first));

                }
                if (node.right != null) {
                    queue.add(node.right);
                    queue2.add(new Pair(TreeLayout.childs[currentIndex].second, TreeLayout.childs[parentIndex].second));
                }
            }

        }

        // rs
        if(right.right != null) {
            int level1 = level / (2);
            int index1 = index_r;

            AVLNode temp1 = right.right;
            Queue<AVLNode> queue = new LinkedList<>();
            Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();

            System.out.println("in = " + (index1+level1));
            queue.add(temp1);
            queue2.add(new Pair(index1+level1, index1));

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

        // ts
        if(temp != null) {
            int level3 = level/(2*2);
            int index3 = index_ts;

            int add = index_ts == 13 ? 2 : index_ts == 10 ? 4 : -1;
            System.out.println("add   " + add);
            AVLNode temp3 = temp;
            Queue<AVLNode> queue = new LinkedList<>();
            Queue<Pair<Integer, Integer>> queue2 = new LinkedList<>();

            System.out.println("in = " + (index3));
            queue.add(temp3);
            queue2.add(new Pair<>(index3, level3));

            while(queue.size() > 0 ) {
                AVLNode node = queue.remove();
                Pair<Integer, Integer> pair = queue2.remove();
                int currentIndex = pair.first;
                int parentIndex = currentIndex - add;
                step2.add(new TreeElementAnimationData(node.key, node.count, currentIndex, parentIndex));
                step3.add(new TreeElementAnimationData(node.key, node.count, currentIndex, parentIndex));

                if (node.left != null) {
                    queue.add(node.left);
                    queue2.add(new Pair<>(currentIndex - level3, level3/2));

                }
                if (node.right != null) {
                    queue.add(node.right);
                    queue2.add(new Pair<>(currentIndex + level3, level3/2));
                }
            }

        }

        treeAnimationStates.add(step2);
        treeAnimationStates.add(step3);

        // Perform rotation
        right.left = avlNode;
        avlNode.right = temp;

        // Update heights
        avlNode.height = max(height(avlNode.left), height(avlNode.right)) + 1;
        right.height = max(height(right.left), height(right.right)) + 1;

        return right;
    }

    public void inorder(){
        treeAnimationStates = new ArrayList<>();
        _inorder(root, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
    }

    private void _inorder(AVLNode avlNode, int index, int level){
        System.out.println("inorder");
        if (avlNode != null){
            _inorder(avlNode.left, index - level, level / 2);
            TreeAnimationState treeAnimationState = new TreeAnimationState(ORDER_TRAVERSAL, AVLInfo.getOrderTraversalString("In"));
            treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            System.out.print(avlNode.key + "(" + avlNode.count + ") ");
            _inorder(avlNode.right, index + level, level / 2);
        }
    }

    public void preorder(){
        treeAnimationStates = new ArrayList<>();
        _preorder(root, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
    }

    private void _preorder(AVLNode avlNode, int index, int level){
        System.out.println("preorder");
        if (avlNode != null){
            TreeAnimationState treeAnimationState = new TreeAnimationState(ORDER_TRAVERSAL, AVLInfo.getOrderTraversalString("Pre"));
            treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            System.out.print(avlNode.key + "(" + avlNode.count + ") ");
            _preorder(avlNode.left, index - level, level / 2);
            _preorder(avlNode.right, index + level, level / 2);
        }
    }

    public void postorder(){
        treeAnimationStates = new ArrayList<>();
        _postorder(root, baseIndex, baseLevel);
        treeSequence = new TreeSequence(treeAnimationStates);
    }

    private void _postorder(AVLNode avlNode, int index, int level){
        System.out.println("postorder");
        if (avlNode != null){
            _postorder(avlNode.left, index - level, level / 2);
            _postorder(avlNode.right, index + level, level / 2);
            TreeAnimationState treeAnimationState = new TreeAnimationState(ORDER_TRAVERSAL, AVLInfo.getOrderTraversalString("Post"));
            treeAnimationState.add(new TreeElementAnimationData(avlNode.key, avlNode.count, index));
            treeAnimationStates.add(treeAnimationState);
            System.out.print(avlNode.key + "(" + avlNode.count + ") ");
        }
    }

}