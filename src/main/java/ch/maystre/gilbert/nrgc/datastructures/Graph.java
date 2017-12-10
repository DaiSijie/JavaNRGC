package ch.maystre.gilbert.nrgc.datastructures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph {

    private final int n;
    private final Set<Edge> edges;
    private final int[] multiplicities;
    private final boolean caterpillar;
    private final Set<Edge> largestEdgeClique;
    private final HashMap<Integer, HashSet<Integer>> neighbors;

    private Graph(int n, Set<Edge> edges, int[] multiplicities, boolean caterpillar, Set<Edge>largestEdgeClique, HashMap<Integer, HashSet<Integer>> neighbors ){
        this.n = n;
        this.edges = edges;
        this.multiplicities = multiplicities;
        this.caterpillar = caterpillar;
        this.largestEdgeClique = largestEdgeClique;
        this.neighbors = neighbors;
    }

    public HashSet<Integer> neighborsOf(int vertex){
        return neighbors.get(vertex);
    }

    public boolean hasEdge(Edge e){
        return edges.contains(e);
    }

    public int getN() {
        return n;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public int[] getMultiplicities() {
        return multiplicities;
    }

    public boolean isCaterpillar() {
        return caterpillar;
    }

    public Set<Edge> getLargestEdgeClique() {
        return largestEdgeClique;
    }

    public static class Builder {

        private final int n;
        private final Set<Edge> edges;
        private final int[] multiplicities;
        private boolean caterpillar;
        private Set<Integer> largestClique;
        private HashMap<Integer, HashSet<Integer>> neighbors;

        public Builder(int n){
            this.n = n;
            this.edges = new HashSet<>();
            this.multiplicities = new int[n];
            this.neighbors = new HashMap<>();
            for(int i = 0; i < n; i++){
                neighbors.put(i, new HashSet<Integer>());
            }
        }

        public Graph build(){
            return new Graph(n, edges, multiplicities, caterpillar, findLargestEdgeClique(), neighbors);
        }

        public void setIsCaterpillar(boolean caterpillar){
            this.caterpillar = caterpillar;
        }

        public void addEdge(Integer fst, Integer snd){
            edges.add(new Edge(fst, snd));
            neighbors.get(fst).add(snd);
            neighbors.get(snd).add(fst);
            multiplicities[fst]++;
            multiplicities[snd]++;
        }

        private Set<Edge> findLargestEdgeClique(){
            int max = -1;
            int maxIndex = 0;
            for(int i = 0; i < n; i++){
                if(multiplicities[i] > max){
                    max = multiplicities[i];
                    maxIndex = i;
                }
            }

            HashSet<Edge> toReturn = new HashSet<>();
            for(Edge e : edges){
                if(e.has(maxIndex)){
                    toReturn.add(e);
                }
            }

            return toReturn;
        }

        private boolean isCaterpillar(){
            //todo
            return false;
        }

    }
}