package ch.maystre.gilbert.nrgc.pathfinders;

import ch.maystre.gilbert.nrgc.datastructures.Graph;

import java.util.*;

public class GeneralPathFinder {

    private final Graph graph;

    public GeneralPathFinder(Graph graph){
        this.graph = graph;

    }

    public List<List<Integer>> findAllPaths(){
        List<List<Integer>> toReturn = new ArrayList<>();
        for(int s = 0; s < graph.size(); s++){
            for(int t = s + 1; t < graph.size(); t++){
                toReturn.addAll(allStPaths(s, t));
            }
        }
        return toReturn;
    }

    private List<List<Integer>> allStPaths(int s, int t){
        List<List<Integer>> r = rAllStPaths(s, t, Collections.singleton(s));
        for(List<Integer> path : r){
            path.add(s);
        }
        return r;
    }

    private List<List<Integer>> rAllStPaths(int s, int t, Set<Integer> visited){
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
                    List<List<Integer>> paths = rAllStPaths(v, t, nVisited);
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
