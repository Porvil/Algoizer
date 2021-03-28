package com.iiitd.dsavisualizer.datastructures.graphs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.opengl.GLES10;

import javax.microedition.khronos.opengles.GL10;

public class GraphDimensions {
    int nodeTextSize;
    int coordinatesTextSize;
    int edgeWidth;
    int edgeArrowWidth;
    int edgeWeightTextSize;

    boolean isLargeGraph;
    Context context;

    private final int nodeRect              = 100;
    private final int nodeCircleDiameter    = 50;
    private final int nodeEdgeArrow         = 15;
    private final int nodeText              = 50;
    private final int nodeTextCoordinates   = 25;


    GraphDimensions(Context _context, boolean _isLargeGraph){
        this.context = _context;
        this.isLargeGraph = _isLargeGraph;

        // X = total col. size
        // c = no. of columns
        // cSize = col. size
        // cEmpty = col. empty size
        // Y = total row size
        // r = no. of rows
        // rSize = row. size
        // rEmpty = row empty size
        // We use 3:1 Ratio for size:empty [cSize : cEmpty]
        // We use 3:1 Ratio for size:empty [rSize : rEmpty]
        // rSize == cSize && rEmpty == cEmpty
        // X >= c * cSize + (c+1) * cEmpty
        // X >= c * cSize + (c+1) * 1/3 cSize
        // X >= cSize/3 * (4*c + 1)
        // cSize <= (3*X) / (4*c + 1)


        int noOfCols = GraphSettings.getNoOfCols(isLargeGraph);
        int noOfRows = GraphSettings.getNoOfRows(isLargeGraph);

        float maxX = 2048;
        float maxY = 2048;

        float cSize = (3*maxX) / (4*noOfCols + 1);

        System.out.println(cSize);


        float ratio = cSize/nodeRect;

        float f_nodeRect             = nodeRect  * ratio;
        float f_nodeCircleDiameter   = nodeCircleDiameter * ratio;
        float f_nodeEdgeArrow        = nodeEdgeArrow  * ratio;
        float f_nodeText             = nodeText  * ratio;
        float f_nodeTextCoordinates  = nodeTextCoordinates * ratio;

        int i_nodeRect            = (int) f_nodeRect;
        int i_nodeCircleDiameter  = (int) f_nodeCircleDiameter;
        int i_nodeEdgeArrow       = (int) f_nodeEdgeArrow;
        int i_nodeText            = (int) f_nodeText;
        int i_nodeTextCoordinates = (int) f_nodeTextCoordinates;


        System.out.println(i_nodeRect            );
        System.out.println(i_nodeCircleDiameter  );
        System.out.println(i_nodeEdgeArrow       );
        System.out.println(i_nodeText            );
        System.out.println(i_nodeTextCoordinates );

    }
}
