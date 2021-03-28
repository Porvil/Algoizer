package com.iiitd.dsavisualizer.datastructures.graphs;

public class GraphData {
    int nodeRect;
    int nodeCircleRadius;
    int nodeEdgeArrow;
    int nodeText;
    int nodeTextCoordinates;

    private GraphData(int nodeRect, int nodeCircleRadius, int nodeEdgeArrow, int nodeText, int nodeTextCoordinates) {
        this.nodeRect = nodeRect;
        this.nodeCircleRadius = nodeCircleRadius;
        this.nodeEdgeArrow = nodeEdgeArrow;
        this.nodeText = nodeText;
        this.nodeTextCoordinates = nodeTextCoordinates;
    }

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
    public static GraphData getInstance(boolean isLargeGraph){
        int noOfCols = GraphSettings.getNoOfCols(isLargeGraph);

        float maxX = GraphSettings.maxX;
        float cSize = (3*maxX) / (4*noOfCols + 1);

        System.out.println(cSize);


        float ratio = cSize/GraphSettings.nodeRect;

        float f_nodeRect             = GraphSettings.nodeRect  * ratio;
        float f_nodeCircleRadius     = GraphSettings.nodeCircleRadius * ratio;
        float f_nodeEdgeArrow        = GraphSettings.nodeEdgeArrow  * ratio;
        float f_nodeText             = GraphSettings.nodeText  * ratio;
        float f_nodeTextCoordinates  = GraphSettings.nodeTextCoordinates * ratio;

        int i_nodeRect            = (int) f_nodeRect;
        int i_nodeCircleRadius    = (int) f_nodeCircleRadius;
        int i_nodeEdgeArrow       = (int) f_nodeEdgeArrow;
        int i_nodeText            = (int) f_nodeText;
        int i_nodeTextCoordinates = (int) f_nodeTextCoordinates;


        System.out.println(i_nodeRect            );
        System.out.println(i_nodeCircleRadius    );
        System.out.println(i_nodeEdgeArrow       );
        System.out.println(i_nodeText            );
        System.out.println(i_nodeTextCoordinates );

        return new GraphData(i_nodeRect, i_nodeCircleRadius, i_nodeEdgeArrow, i_nodeText, i_nodeTextCoordinates);
    }
}
