package com.iiitd.dsavisualizer.algorithms.sorting.quick;

import java.util.HashMap;

//HARDCODED INFORMATION, Handle with care
public class QuickSortInfo {

    /*0*/  public static final String QS = "Quick Sort";
    /*4*/  public static final String LS = "Sorting Left Half";
    /*5*/  public static final String RS = "Sorting Right Half";
    /*7*/  public static final String PA = "Partition";
    /*7*/  public static final String PA_START = "Partition Start";
    /*7*/  public static final String PA_U = "Partition Done";
    /*8*/  public static final String PI = "Pivot";
    /*  */ public static final String E_GREATEREQUAL_P = "Element > Pivot";
    /*11*/ public static final String E_LESSER_P = "Element <= Pivot";
    /*2*/  public static final String SINGLE_PARTITION = "Single element is always sorted";
    /*2*/  public static final String SWAP_END = "Swap";

    public static final HashMap<String, Integer[]> map = new HashMap<>();

    static {
        map.put(QS, new Integer[]{0});
        map.put(LS, new Integer[]{4});
        map.put(RS, new Integer[]{5});
        map.put(PA, new Integer[]{3, 7});
        map.put(PA_START, new Integer[]{9, 10});
        map.put(PI, new Integer[]{8});
        map.put(E_GREATEREQUAL_P, new Integer[]{14, 15});
        map.put(E_LESSER_P, new Integer[]{11, 12, 13});
        map.put(SINGLE_PARTITION, new Integer[]{1, 2});
        map.put(SWAP_END, new Integer[]{16});
        map.put(PA_U, new Integer[]{17});
    }

    public static final int[] boldIndexes = new int[]{0, 7};

    public static final String[] psuedocode = new String[]{
       /*0*/         "quickSort(data, start, end):",
       /*1*/         "    if start > end",
       /*2*/         "        return",
       /*3*/         "    pivot = partition(data, start, end)",
       /*4*/         "    quickSort(data, start, pivot-1)",
       /*5*/         "    quickSort(data, pivot+1, end)",
       /*6*/         "",
       /*7*/         "partition(data, start, end)",
       /*8*/         "pivot = select pivot(first, last or middle)",
       /*9*/         "i = start+1",
       /*10*/        "for(j = start+1 to end)",
       /*11*/        "    if(data[j] < pivot)",
       /*12*/        "        swap data[i] and data[j]",
       /*13*/        "        i++",
       /*14*/        "    else",
       /*15*/        "        continue",
       /*16*/        "swap data[start] and data[i-1]",
       /*17*/        "return i-1",
       /*18*/        ""
    };


    public static String getComparedString(int e, int p, int eIndex, int iIndex){
       if(e < p){
           return e + " < " + p + ", swap data[" + eIndex + "] and data[" + iIndex + "], i++" ;
       }

       return e + " >= " + p + ", continue";
    }

    public static String getPivot(int pivot){
       return "Pivot : " + pivot;
    }

    public static String getPivotSwap(){
       return "Pivot element swapped to first index";
    }

    public static String getPartitionStart(){
        return "Set i and j pointers";
    }

    public static String getEndSwap(int start, int end){
       return "swap data[" + start + "] and data[" + end + "]";
    }

    public static String getQuickSortString(int left, int right){
       return "quickSort(data, " + left + ", " + right + ")";
    }

    public static String getPartitionString(int left, int right){
       return "partition(data, " + left + ", " + right + ")";
    }

}