package com.iiitd.dsavisualizer.algorithms.sorting.selection;

import java.util.HashMap;

//HARDCODED INFORMATION, Handle with care
public class SelectionSortInfo {

    /*0*/  public static final String SS = "Selection Sort";
    /*11*/ public static final String VAL = "I";
    /*11*/ public static final String SWAP = "Swap";
    /*11*/ public static final String L_LESSER_R = "Left <= Right";
    /*13*/ public static final String L_GREATEREQUAL_R = "Left > Right";

    public static final HashMap<String, Integer[]> map = new HashMap<>();

    static {
        map.put(SS, new Integer[]{0});
        map.put(VAL, new Integer[]{3, 4});
        map.put(SWAP, new Integer[]{12, 13});
        map.put(L_LESSER_R, new Integer[]{7, 8});
        map.put(L_GREATEREQUAL_R, new Integer[]{9, 10});
    }

    public static final int[] boldIndexes = new int[]{0};

    public static final String[] psuedocode = new String[]{
    /*0*/         "selectionSort(data):",
    /*1*/         "    length = data.length",
    /*2*/         "",
    /*3*/         "    for(i = 0 to length-1)",
    /*4*/         "        min_ind = i",
    /*5*/         "",
    /*6*/         "        for(j = i+1 to length)",
    /*7*/         "            if(data[j] < data[min_ind])",
    /*8*/         "                min_ind = j",
    /*9*/         "            else",
    /*10*/        "                continue",
    /*11*/        "",
    /*12*/        "        if(min_ind != i)",
    /*13*/        "            swap data[i] and data[min_ind]",
    /*14*/        "",

    };

    public static String getComparedString(int a, int b, int index){
        if(a < b){
            return a + " < " + b + ", min_ind = " + index;
        }

        return a + " >= " + b + ", continue";
    }

    public static String getSelectionSortString(){
        return "selectionSort(data)";
    }

    public static String getValString(int index){
        return "min_ind = " + index;
    }

    public static String getSwapString(int i, int min_index){
        return "swap data[" + i + "] and data[" + min_index + "]";
    }

}