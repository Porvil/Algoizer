package com.iiitd.dsavisualizer.datastructures.graphs.algorithms;

import com.iiitd.dsavisualizer.datastructures.graphs.Edge;
import com.iiitd.dsavisualizer.datastructures.graphs.Graph;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAlgorithmType;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationState;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphAnimationStateExtra;
import com.iiitd.dsavisualizer.datastructures.graphs.GraphSequence;
import com.iiitd.dsavisualizer.datastructures.graphs.Vertex;
import com.iiitd.dsavisualizer.datastructures.graphs.VertexCLRS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BellmanFord {
    Graph graph;
    GraphSequence graphSequence;
    HashMap<Integer, VertexCLRS> map;

    public BellmanFord(Graph graph) {
        this.graph = graph;
        this.graphSequence = new GraphSequence(GraphAlgorithmType.BELLMAN_FORD);
    }

    public GraphSequence run(int source) {
        int size = graph.noOfVertices;

        if (size < 1)
            return graphSequence;

        this.map = new HashMap<>();

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        {
            GraphAnimationState graphAnimationState =
                    GraphAnimationState.create()
                            .setState("start")
                            .setInfo("start")
                            .addVertices(vertices)
                            .addEdges(edges);

            graphSequence.addGraphAnimationState(graphAnimationState);
        }

        for (Map.Entry<Integer, Vertex> entry : graph.vertexMap.entrySet()) {
            VertexCLRS vertexCLRS = VertexCLRS.bellmanfordVertexCLRS(entry.getValue());
            map.put(entry.getKey(), vertexCLRS);
        }

        map.get(source).bellmanFordDist = 0;

        ArrayList<Edge> allEdges = graph.getAllEdges();

        for (int i=0;i<size-1;i++) {
            for(Edge edge:allEdges){
                VertexCLRS vertexCLRS = map.get(edge.src);
                VertexCLRS vertexCLRS1 = map.get(edge.des);
//                int tempDist = vertexCLRS.bellmanFordDist + edge.weight;
                int tempDistance = vertexCLRS.bellmanFordDist + edge.weight;
                int otherDistance = vertexCLRS1.bellmanFordDist;

                vertices.clear();
                vertices.add(graph.vertexMap.get(vertexCLRS.data));
                vertices.add(graph.vertexMap.get(vertexCLRS1.data));
                edges.clear();
                edges.add(edge);

                if(tempDistance < otherDistance){

                    GraphAnimationState graphAnimationState1 =
                            GraphAnimationState.create()
//                                    .setState("Visit = " + cur)
//                                    .setInfo(map.get(cur).dijkstraDist + " + " + edge.weight + " < " + otherDist
//                                            + "\n" + "Vertex(" + edge.des +").distance = " + tempDistance)
                                    .addVertices(vertices)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                            .addMapBellmanford(map));

                    graphSequence.addGraphAnimationState(graphAnimationState1);

                    vertexCLRS1.bellmanFordDist = tempDistance;
                    vertexCLRS1.parent = vertexCLRS.data;
                }
                else{
                    GraphAnimationState graphAnimationState1 =
                            GraphAnimationState.create()
//                                    .setState("Visit = " + cur)
//                                    .setInfo(map.get(cur).dijkstraDist + " + " + edge.weight + " < " + otherDist
//                                            + "\n" + "Vertex(" + edge.des +").distance = " + tempDistance)
                                    .addVertices(vertices)
                                    .addEdges(edges)
                                    .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                            .addMapBellmanford(map));

                    graphSequence.addGraphAnimationState(graphAnimationState1);
                }
            }
        }


        for(Edge edge:allEdges){
            VertexCLRS vertexCLRS = map.get(edge.src);
            VertexCLRS vertexCLRS1 = map.get(edge.des);
            int tempDist = vertexCLRS.bellmanFordDist + edge.weight;
            if(tempDist < vertexCLRS1.bellmanFordDist){
                System.out.println("NEGATIVE EDGE CYCLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }

        vertices.clear();
        edges.clear();

        for (Map.Entry<Integer, VertexCLRS> entry : map.entrySet()) {
            System.out.println(entry.getValue());
            vertices.add(entry.getValue().getVertex());
            if(entry.getValue().parent != -1){
                Edge edge = graph.getEdge(entry.getValue().parent, entry.getValue().data);
                edges.add(edge);
            }
        }


        GraphAnimationState graphAnimationState1 =
                GraphAnimationState.create()
                                    .setState("Done")
//                                    .setInfo(map.get(cur).dijkstraDist + " + " + edge.weight + " < " + otherDist
//                                            + "\n" + "Vertex(" + edge.des +").distance = " + tempDistance)
                        .addVertices(vertices)
                        .addEdges(edges)
                        .addGraphAnimationStateExtra(GraphAnimationStateExtra.create()
                                .addMapBellmanford(map));

        graphSequence.addGraphAnimationState(graphAnimationState1);

        // ALL DONE
        for (Map.Entry<Integer, VertexCLRS> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

        return graphSequence;
    }

}
