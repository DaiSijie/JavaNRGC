package ch.maystre.gilbert.nrgc.pathfinders;

import ch.maystre.gilbert.nrgc.datastructures.Edge;
import ch.maystre.gilbert.nrgc.datastructures.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class SeparatePathsFinder {

    private final Graph g;

    private final Queue<List<Integer>> toExplore;

    private int actualWorkingSize = 0;

    /**
     * A class that pops every paths in order with no duplicates
     * Based on the algorithm in Non-repetitive coloring of graphs
     * 2017 EPFL, Gilbert MAYSTRE
     * @param g
     */
    public SeparatePathsFinder(Graph g){
        this.g = g;
        toExplore = new ArrayDeque<>();
    }

    /**
     * Add all the trivial paths (the edges) to the queue.
     * Order within edge is not specified
     */
    private List<List<Integer>> getAndStoreTrivialPaths(){
        ArrayList<List<Integer>> toReturn = new ArrayList<>();
        for(Edge g: g.getEdges()){
            List<Integer> toAdd = Arrays.asList(g.fst(), g.snd());
            toExplore.add(toAdd);
            toReturn.add(toAdd);
        }
        return toReturn;
    }

    public List<List<Integer>> popEverything(){
        List<List<Integer>> toReturn = new ArrayList<>();
        List<List<Integer>> cr;

        while(!(cr = nextPaths()).isEmpty()){
            toReturn.addAll(cr);
        }
        return toReturn;
    }

    public List<List<Integer>> nextPaths(){
        // paths are growing in 2 by 2.
        actualWorkingSize += 2;

        if(actualWorkingSize == 2){
            return getAndStoreTrivialPaths();
        }
        else if(toExplore.isEmpty()){
            return new ArrayList<>();
        }

        ArrayList<List<Integer>> toReturn = new ArrayList<>();
        while(!toExplore.isEmpty() && toExplore.peek().size() <= actualWorkingSize){
            List<Integer> current = toExplore.poll();
            int left = current.get(0);
            int right = current.get(current.size() - 1);

            List<Integer> lNeighbors = g.neighborsOf(left).stream().filter(u -> okToAddLeft(u, current)).collect(Collectors.toList());
            List<Integer> rNeighbors = g.neighborsOf(right).stream().filter(u -> okToAddRight(u, current)).collect(Collectors.toList());

            for(int l : lNeighbors){
                for(int r : rNeighbors){
                    if(l != r){
                        ArrayList<Integer> copy = new ArrayList<>(current);
                        copy.add(0, l);
                        copy.add(r);
                        toReturn.add(copy);
                        if(!g.neighborsOf(left).contains(right)){
                            toExplore.add(copy);
                        }
                    }
                }
            }
        }

        return toReturn;
    }

    private boolean okToAddRight(int u, List<Integer> path){
        int l = path.size()/2;
        for(int i = 0; i < 2*l; i++){
            int lookingAt = path.get(i);
            if(u == lookingAt || (i < l && g.neighborsOf(lookingAt).contains(u))){
                return false;
            }
        }
        return true;
    }

    private boolean okToAddLeft(int u, List<Integer> path){
        int l = path.size()/2;
        for(int i = 0; i < 2*l; i++){
            int lookingAt = path.get(i);
            Set<Integer> neighbors = g.neighborsOf(lookingAt);
            if(u == lookingAt || (i >= l && neighbors.contains(u))){
                return false;
            }
        }
        return true;
    }







}
