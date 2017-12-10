package ch.maystre.gilbert.nrgc.solvers;

import ch.maystre.gilbert.nrgc.datastructures.Edge;
import ch.maystre.gilbert.nrgc.datastructures.Graph;
import ch.maystre.gilbert.nrgc.io.Log;
import gurobi.GRBException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThueIndexSolver{

    private final Graph graph;
    private final HashMap<Edge, Integer> fwd;
    private final HashMap<Integer, Edge> bkw;
    private final AbstractNonRepetitiveSolver solver;

    public ThueIndexSolver(Graph graph, List<List<Integer>> paths){
        this.graph = graph;
        fwd = new HashMap<>();
        bkw = new HashMap<>();
        solver = new AbstractNonRepetitiveSolver(graph.getEdges().size());

        buildMaps();
        addPaths(paths);
    }

    public EdgeNonRepetitiveColoring compute() throws GRBException {
        return new EdgeNonRepetitiveColoring(solver.compute(), bkw);
    }

    private void buildMaps(){
        int counter = 0;
        for(Edge e : graph.getEdges()){
            fwd.put(e, counter);
            bkw.put(counter, e);
            counter++;

        }
    }

    private void addPaths(List<List<Integer>> paths){
        List<List<Integer>> nPaths = new ArrayList<>();
        for(List<Integer> path : paths){
            if(path.size() % 2 == 1){
                List<Integer> nPath = new ArrayList<>();
                int last = path.get(0);
                for(int i = 1; i < path.size(); i++){
                    Edge current = new Edge(last, path.get(i));
                    last = path.get(i);
                    nPath.add(fwd.get(current));
                }
                nPaths.add(nPath);
            }
        }
        solver.setNonRepetitiveConstraints(nPaths);
    }

    public static class EdgeNonRepetitiveColoring {

        private final int numberOfColors;
        private final HashMap<Edge, Integer> colorOf;

        private EdgeNonRepetitiveColoring(AbstractNonRepetitiveSolver.NonRepetitiveSolution solution, HashMap<Integer, Edge> bkw){
            numberOfColors = solution.getNumberOfColors();
            colorOf = new HashMap<>();
            for(Map.Entry<Integer, Edge> entry: bkw.entrySet()){
                colorOf.put(entry.getValue(), solution.getColorForVar(entry.getKey()));
            }
        }

        public int getNumberOfColors(){
            return numberOfColors;
        }

        public HashMap<Edge, Integer> getColoring(){
            return colorOf;
        }

    }

}
