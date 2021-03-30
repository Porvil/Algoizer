package com.iiitd.dsavisualizer.datastructures.graphs;

public class GraphData {
    public int nodeRect;
    public int nodeCircleRadius;
    public int nodeEdgeArrow;
    public int nodeText;
    public int nodeTextCoordinates;

    public int X;                                // Width of Board
    public int Y;                                // Height of Board
    public int xCount;                             // No. of columns
    public int yCount;                             // No. of rows
    public int xSize;                            // One Column Width
    public int ySize;                            // One Row Height
    public int xEmpty;                           // One Column Width Empty
    public int yEmpty;                           // One Row Height Empty
    public float xOverall;
    public float yOverall;

    /*
        X = total col. size
        c = no. of columns
        cSize = col. size
        cEmpty = col. empty size

        Y = total row size
        r = no. of rows
        rSize = row. size
        rEmpty = row empty size

        We use 3:1 Ratio for size:empty [cSize : cEmpty]
        We use 3:1 Ratio for size:empty [rSize : rEmpty]
        rSize == cSize && rEmpty == cEmpty

        X >= c * cSize + (c+1) * cEmpty
        X >= c * cSize + (c+1) * 1/3 cSize
        X >= cSize/3 * (4*c + 1)
        cSize <= (3*X) / (4*c + 1)

        similarly for Y, r, rSize and rEmpty
     */

    public GraphData(boolean isLargeGraph){
        this.xCount = GraphSettings.getNoOfCols(isLargeGraph);
        this.yCount = GraphSettings.getNoOfRows(isLargeGraph);

        float maxX = GraphSettings.maxX;
        float cSize = (3*maxX) / (4*xCount + 1);

        System.out.println(cSize);


        this.xSize = (int) cSize;
        this.ySize = (int) cSize;
        this.xEmpty = (int) (cSize/3);
        this.yEmpty = (int) (cSize/3);
        this.xOverall = xSize + xEmpty;
        this.yOverall = ySize + yEmpty;
        this.X = (xCount * xSize) + ((xCount+1) * xEmpty);
        this.Y = (yCount * ySize) + ((yCount+1) * yEmpty);

        float ratio = cSize/GraphSettings.nodeRect;

        float f_nodeRect             = GraphSettings.nodeRect  * ratio;
        float f_nodeCircleRadius     = GraphSettings.nodeCircleRadius * ratio;
        float f_nodeEdgeArrow        = GraphSettings.nodeEdgeArrow  * ratio;
        float f_nodeText             = GraphSettings.nodeText  * ratio;
        float f_nodeTextCoordinates  = GraphSettings.nodeTextCoordinates * ratio;

        this.nodeRect            = (int) f_nodeRect;
        this.nodeCircleRadius    = (int) f_nodeCircleRadius;
        this.nodeEdgeArrow       = (int) f_nodeEdgeArrow;
        this.nodeText            = (int) f_nodeText;
        this.nodeTextCoordinates = (int) f_nodeTextCoordinates;

        System.out.println(nodeRect            );
        System.out.println(nodeCircleRadius    );
        System.out.println(nodeEdgeArrow       );
        System.out.println(nodeText            );
        System.out.println(nodeTextCoordinates );
    }
}
