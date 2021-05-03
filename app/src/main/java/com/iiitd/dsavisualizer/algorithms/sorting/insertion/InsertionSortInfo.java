package com.iiitd.dsavisualizer.algorithms.sorting.insertion;

import java.util.HashMap;

//HARDCODED INFORMATION, Handle with care
public class InsertionSortInfo {

    /*0*/  public static final String IS = "Insertion Sort";
    /*11*/ public static final String VAL = "I";
    /*11*/ public static final String VAL_U = "I_Up";
    /*11*/ public static final String L_LESSEQUAL_R = "Left <= Right";
    /*13*/ public static final String L_GREATER_R = "Left > Right";

    public static final HashMap<String, Integer[]> map = new HashMap<>();

    static {
        map.put(IS, new Integer[]{0});
        map.put(VAL, new Integer[]{3, 4, 5});
        map.put(VAL_U, new Integer[]{14});
        map.put(L_LESSEQUAL_R, new Integer[]{11, 12});
        map.put(L_GREATER_R, new Integer[]{8, 9, 10});
    }

    public static final int[] boldIndexes = new int[]{0};

    public static final String[] psuedocode = new String[]{
    /*0*/         "insertionSort(data):",
    /*1*/         "    length = data.length",
    /*2*/         "",
    /*3*/         "    for(i = 1 to length)",
    /*4*/         "        val = data[i]",
    /*5*/         "        j = i - 1",
    /*6*/         "",
    /*7*/         "        while(j >= 0)",
    /*8*/         "            if(data[j] > val)",
    /*9*/         "                swap data[j] and val",
    /*10*/        "                j--",
    /*11*/        "            else",
    /*12*/        "                break",
    /*13*/        "",
    /*14*/        "        continue",
    /*15*/        "",

    };


    public static String getComparedString(int a, int b, int aIndex, int bIndex){
        if(a > b){
            return a + " > " + b + ", swap data[" + aIndex + "] and data[" + bIndex + "]" ;
        }

        return a + " <= " + b + ", continue";
    }

    public static String getInsertionSortString(){
        return "insertionSort(data)";
    }

    public static String getValString(int data, int j){
        return "val = " + data + " , j = " + j;
    }

    public static String getValUString(){
        return "break";
    }

}