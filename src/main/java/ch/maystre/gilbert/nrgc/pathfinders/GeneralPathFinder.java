package ch.maystre.gilbert.nrgc.pathfinders;

import ch.maystre.gilbert.nrgc.datastructures.Graph;

import java.util.*;

/**
 * This class finds all the simple paths in a graph. It runs in linear time for the
 * number of paths (which indeed is exponential).
 */
public class GeneralPathFinder {

    private GeneralPathFinder(){}

    public static List<List<Integer>> findAllPaths(Graph graph){
        List<List<Integer>> toReturn = new ArrayList<>();
        for(int s = 0; s < graph.size(); s++){
            for(int t = s + 1; t < graph.size(); t++){
                toReturn.addAll(allSTPaths(s, t, graph));
            }
        }
        return toReturn;
    }

    private static List<List<Integer>> allSTPaths(int s, int t, Graph graph){
        List<List<Integer>> r = rAllSTPaths(s, t, Collections.singleton(s), graph);
        for(List<Integer> path : r){
            path.add(s);
        }
        return r;
    }

    private static List<List<Integer>> rAllSTPaths(int s, int t, Set<Integer> visited, Graph graph){
        List<List<Integer>> toReturn = new ArrayList<>();
        for(int v : graph.neighborsOf(s)){
            if(!visited.contains(v)){
                if(v == t){
                    ArrayList<Integer> x = new ArrayList<>();
                    x.add(t);
                    toReturn.add(x);
                }
                else{
                    HashSet<Integer> nVisited = new HashSet<>(visited);
                    nVisited.add(v);
                    List<List<Integer>> paths = rAllSTPaths(v, t, nVisited, graph);
                    for(List<Integer> path : paths){
                        path.add(v);
                        toReturn.add(path);
                    }
                }
            }
        }
        return toReturn;
    }

}
