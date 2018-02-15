/*
 * Gilbert Maystre
 * 10.02.18
 */

package ch.maystre.gilbert.nrgc.graphproperties;

import ch.maystre.gilbert.nrgc.datastructures.Graph;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GraphUtils {

    private static final Random random = new Random(1995);

    private GraphUtils(){}

    /*
     * A tree T is a caterpillar if and only if every vertex of degree >= 3 has at
     * most two non-leaf neighbors (the "backbone" of the caterpillar).
     *
     * This gives rise to a simple O(|V(T)|) detection algorithm.
     */
    public static boolean isCaterpillar(Graph tree){
        int[] multiplicities = tree.getMultiplicities();
        for(int v = 0; v < tree.size(); v++){
            if(multiplicities[v] >= 3){
                int nonLeafNeighbors = 0;
                for(int neighbor : tree.neighborsOf(v)){
                    if(multiplicities[neighbor] > 1)
                        nonLeafNeighbors++;
                }
                if(nonLeafNeighbors > 2){
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * Make five random restart, and grow greedily
     */
    public static Set<Integer> largeClique(Graph graph){
        Set<Integer> toReturn = new HashSet<>();
        for(int i = 0; i < 5; i++){
            Set<Integer> candidate = growGreedily(graph, random.nextInt(graph.size()));
            if(candidate.size() > toReturn.size())
                toReturn = candidate;
        }
        return toReturn;
    }

    private static Set<Integer> growGreedily(Graph graph, int from){
        Set<Integer> toReturn = new HashSet<>();
        toReturn.add(from);

        for(int neighbors : graph.neighborsOf(from)){
            if(graph.neighborsOf(neighbors).containsAll(toReturn))
                toReturn.add(neighbors);
        }
        return toReturn;
    }

}
