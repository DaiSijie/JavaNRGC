package ch.maystre.gilbert.nrgc.datastructures;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private final int n;
    private final Set<Edge> edges;
    private final int[] multiplicities;
    private final boolean caterpillar;
    private final Set<Edge> largestEdgeClique;

    private Graph(int n, Set<Edge> edges, int[] multiplicities, boolean caterpillar, Set<Edge>largestEdgeClique ){
        this.n = n;
        this.edges = edges;
        this.multiplicities = multiplicities;
        this.caterpillar = caterpillar;
        this.largestEdgeClique = largestEdgeClique;
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

        public Builder(int n){
            this.n = n;
            this.edges = new HashSet<>();
            this.multiplicities = new int[n];
        }

        public Graph build(){
            return new Graph(n, edges, multiplicities, caterpillar, findLargestEdgeClique());
        }

        public void setIsCaterpillar(boolean caterpillar){
            this.caterpillar = caterpillar;
        }

        public void addEdge(Integer fst, Integer snd){
            edges.add(new Edge(fst, snd));
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