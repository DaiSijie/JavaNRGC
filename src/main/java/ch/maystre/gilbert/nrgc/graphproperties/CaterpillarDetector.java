/*
 * Gilbert Maystre
 * 10.02.18
 */

package ch.maystre.gilbert.nrgc.graphproperties;

import ch.maystre.gilbert.nrgc.datastructures.Graph;

public class CaterpillarDetector {

    private CaterpillarDetector(){}

    /*
     * A tree T is a caterpillar if and only if every vertex of degree >= 3 has at
     * most two non-leaf neighbors (the "backbone" of the caterpillar).
     *
     * This gives rise to a simple O(|V(T)|) detection algorithm.
     */

    public static boolean isCaterpillar(Graph T){
        int[] multiplicities = T.getMultiplicities();
        for(int v = 0; v < T.size(); v++){
            if(multiplicities[v] >= 3){
                int nonLeafNeighbors = 0;
                for(int neighbor : T.neighborsOf(v)){
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

}
