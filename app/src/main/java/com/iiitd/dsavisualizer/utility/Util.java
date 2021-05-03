package com.iiitd.dsavisualizer.utility;

import android.os.Environment;

import com.iiitd.dsavisualizer.algorithms.sorting.bubble.BubbleSortData;
import com.iiitd.dsavisualizer.algorithms.sorting.quick.QuickSortData;
import com.iiitd.dsavisualizer.algorithms.sorting.selection.SelectionSortData;
import com.iiitd.dsavisualizer.constants.AppSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// Util class for non-UI Related helper functions
public class Util {

    // Used by QuickSort
    public static void swap(QuickSortData a, QuickSortData b){
        int oldData = a.data;
        int oldIndex = a.index;

        a.data = b.data;
        a.index = b.index;

        b.data = oldData;
        b.index = oldIndex;
    }

    // Used by BubbleSort
    public static void swap(BubbleSortData a, BubbleSortData b){
        int oldData = a.data;
        int oldIndex = a.index;

        a.data = b.data;
        a.index = b.index;

        b.data = oldData;
        b.index = oldIndex;
    }

    // Used by SelectionSort
    public static void swap(SelectionSortData a, SelectionSortData b){
        int oldData = a.data;
        int oldIndex = a.index;

        a.data = b.data;
        a.index = b.index;

        b.data = oldData;
        b.index = oldIndex;
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    // Returns modified angle between 2 points in 2D space [ used by Board's edge arrow and BoardTree's edge arrow ]
    public static double calculateDegree(float x1, float x2, float y1, float y2) {
        float startRadians = (float) Math.atan((y2 - y1) / (x2 - x1));
        startRadians += ((x2 >= x1) ? 90 : -90) * Math.PI / 180;
        return Math.toDegrees(startRadians);
    }

    // Returns angle between 2 points in 2D space [ used by Board's edge weight ]
    public static float getAngle(double x1, double y1, double x2, double y2) {
        double angle = Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));

        if(angle < 0){
            angle += 360;
        }

        return (float) angle;
    }

    // Writes the serialized string of a graph into a ".graph" file on storage
    public static boolean writeGraphToStorage(String graphString, String fileName){
        //Checking the availability state of the External Storage.
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            System.out.println("Storage is not mounted, returning!!");
            return false;
        }

        String path = AppSettings.getExternalStoragePath() + AppSettings.DIRECTORY;
        File rootFile = new File(path);
        if(!rootFile.exists()){
            boolean mkdir = rootFile.mkdirs();
            if(!mkdir){
                System.out.println("Path/file couldn't be created");
                return false;
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        if(fileName == null)
            fileName = "graph-" + currentTimeStamp + AppSettings.GRAPH_SAVEFILE_EXTENSION;
        String filePath = path + AppSettings.SEPARATOR + fileName;

        PrintWriter out = null;
        try {
            out = new PrintWriter(filePath);
            out.write(graphString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }finally {
            out.close();
        }

        return true;
    }

    // Returns true if passed file has ".graph" extension
    public static boolean isValidGraphSaveFile(String name){
        return isValidFileWithExtension(name, AppSettings.GRAPH_SAVEFILE_EXTENSION);
    }

    // Returns true if passed file has passed extension
    private static boolean isValidFileWithExtension(String name, String extension){
        if(name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0)
            return name.substring(name.lastIndexOf(".")).equals(extension);

        return false;
    }

}