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

public class Util {

    public static void swap(QuickSortData a, QuickSortData b){
        int oldData = a.data;
        int oldIndex = a.index;

        a.data = b.data;
        a.index = b.index;

        b.data = oldData;
        b.index = oldIndex;
    }

    public static void swap(BubbleSortData a, BubbleSortData b){
        int oldData = a.data;
        int oldIndex = a.index;

        a.data = b.data;
        a.index = b.index;

        b.data = oldData;
        b.index = oldIndex;
    }

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

    public static double calculateDegree(float x1, float x2, float y1, float y2) {
        float startRadians = (float) Math.atan((y2 - y1) / (x2 - x1));
        startRadians += ((x2 >= x1) ? 90 : -90) * Math.PI / 180;
        return Math.toDegrees(startRadians);
    }

    public static float getAngle(float x1, float y1, float x2, float y2) {
        float angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));

        if(angle < 0){
            angle += 360;
        }

//        return getAngleCorrected(angle);
        return angle;
    }

    public static float getAngleCorrected(float angle) {

        if(angle == 0){
            return angle;
        }
        else if(angle == 90){
            return 0;
        }

        return angle;
    }

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
            fileName = "graph-" + currentTimeStamp + ".txt";
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

}
