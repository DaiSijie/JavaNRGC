package ch.maystre.gilbert.nrgc.pathfinders;

import ch.maystre.gilbert.nrgc.datastructures.Edge;
import ch.maystre.gilbert.nrgc.datastructures.Graph;
import ch.maystre.gilbert.nrgc.datastructures.Pair;

import java.util.*;

public class TreePathsFinder {

    private TreePathsFinder(){}

    /*
     * TODO: MAKE A BETTER ALGORITHM!!!
     */

    public static List<List<Integer>> allPaths(Graph g){
        List<List<Integer>> toReturn = new ArrayList<>();
        for(int s = 0; s < g.getN(); s++){
            toReturn.addAll(allPathsFromS(g, s));
        }
        return toReturn;
    }

    private static List<List<Integer>> allPathsFromS(Graph g, int s){
        List<List<Integer>> toReturn = new ArrayList<>();
        HashSet<Integer> visited = new HashSet<>();
        Queue<Pair<Integer, List<Integer>>> toVisit = new ArrayDeque<>();
        toVisit.add(new Pair<>(s, Collections.singletonList(s)));

        while(toVisit.size() > 0){
            Pair<Integer, List<Integer>> visiting = toVisit.remove();
            visited.add(visiting.fst());
            for(int i = 0; i < g.getN() ; i++){
                if(!visited.contains(i) && g.hasEdge(new Edge(i, visiting.fst()))){
                    List<Integer> nHow = new ArrayList<>(visiting.snd());
                    nHow.add(i);
                    toVisit.add(new Pair<>(i, nHow));
                    // do we have discovered a new path?
                    if(s < i){
                        toReturn.add(nHow);
                    }

                }
            }
        }

        return toReturn;
    }

}
