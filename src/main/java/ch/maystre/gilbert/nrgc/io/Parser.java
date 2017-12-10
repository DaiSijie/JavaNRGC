package ch.maystre.gilbert.nrgc.io;

import ch.maystre.gilbert.nrgc.datastructures.Graph;

public class Parser {

    private static final String EDGE_SEPARATOR = "  ";
    private static final String VERTEX_SEPARATOR = " ";

    private Parser(){}

    public static Graph parseTree(String str){
        String[] edges = str.split(EDGE_SEPARATOR);

        Graph.Builder builder = new Graph.Builder(edges.length + 1); // a tree has n - 1 edges
        for(String edge : edges){
            String[] vertices = edge.split(VERTEX_SEPARATOR);
            builder.addEdge(toInt(vertices[0]), toInt(vertices[1]));
        }

        return builder.build();
    }

    private static Integer toInt(String str){
        return new Integer(str);
    }

}
