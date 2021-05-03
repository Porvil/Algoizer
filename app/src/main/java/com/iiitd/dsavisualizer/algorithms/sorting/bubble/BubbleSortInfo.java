package com.iiitd.dsavisualizer.algorithms.sorting.bubble;

import java.util.HashMap;

//HARDCODED INFORMATION, Handle with care
public class BubbleSortInfo {

    /*0*/  public static final String BS = "Bubble Sort";
    /*4*/  public static final String FLAG = "Flag";
    /*11*/ public static final String L_LESSEQUAL_R = "Left <= Right";
    /*13*/ public static final String L_GREATER_R = "Left > Right";

    public static final HashMap<String, Integer[]> map = new HashMap<>();

    static {
        map.put(BS, new Integer[]{0});
        map.put(FLAG, new Integer[]{13, 14});
        map.put(L_LESSEQUAL_R, new Integer[]{7, 8, 9});
        map.put(L_GREATER_R, new Integer[]{10, 11});
    }

    public static final int[] boldIndexes = new int[]{0};

    public static final String[] psuedocode = new String[]{
   /*0*/         "bubbleSort(data):",
   /*1*/         "    length = data.length",
   /*2*/         "",
   /*3*/         "    for(i = 0 to length)",
   /*4*/         "        flag = false",
   /*5*/         "",
   /*6*/         "        for(j = 0 to length-i-1)",
   /*7*/         "            if(data[j] > data[j+1])",
   /*8*/         "                swap data[j] and data[j+1]",
   /*9*/         "                flag = true",
   /*10*/        "            else",
   /*11*/        "                continue",
   /*12*/        "        ",
   /*13*/        "        if(flag is false)",
   /*14*/        "            return",
   /*15*/        ""
    };


    public static String getComparedString(int a, int b, int aIndex, int bIndex){
        if(a > b){
            return a + " > " + b + ", swap data[" + aIndex + "] and data[" + bIndex + "]" ;
        }

        return a + " <= " + b + ", continue";
    }

    public static String getBubbleSortString(){
        return "bubbleSort(data)";
    }

    public static String getFlagString(){
        return "flag is false, break outer for loop";
    }

}