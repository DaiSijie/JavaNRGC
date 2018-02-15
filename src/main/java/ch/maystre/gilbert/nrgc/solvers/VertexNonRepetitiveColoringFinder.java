package ch.maystre.gilbert.nrgc.solvers;

import ch.maystre.gilbert.nrgc.datastructures.Graph;
import ch.maystre.gilbert.nrgc.graphproperties.GraphUtils;
import ch.maystre.gilbert.nrgc.pathfinders.SeparatePathsFinder;
import gurobi.GRBException;

import java.util.List;
import java.util.Set;

public class VertexNonRepetitiveColoringFinder {

    private Graph graph;

    private List<List<Integer>> paths;

    private AbstractNonRepetitiveSolver solver;

    public VertexNonRepetitiveColoringFinder(Graph graph){
        this.graph = graph;
        this.solver = new AbstractNonRepetitiveSolver(graph.size());
    }

    /**
     * Adds to the instance all separate paths provided by SeparatePathsFinder
     *
     * @return This instance
     */
    public VertexNonRepetitiveColoringFinder addSeparatePaths(){
        solver.setNonRepetitiveConstraints((new SeparatePathsFinder(graph)).popEverything());
        return this;
    }

    /**
     * Sets the paths to consider
     *
     * @param paths the paths that cannot be repetitive
     * @return this instance
     */
    public VertexNonRepetitiveColoringFinder setPaths(List<List<Integer>> paths){
        solver.setNonRepetitiveConstraints(paths);
        return this;
    }

    /**
     * Finds a large clique to accelerate the computation
     *
     * @return this instance
     */
    public VertexNonRepetitiveColoringFinder computeLargeClique(){
        solver.setExclusiveSet(GraphUtils.largeClique(graph));
        return this;
    }

    /**
     *  Sets the largest clique to speedup the computation
     *
     * @param clique the clique to consider
     * @return this instance
     */
    public VertexNonRepetitiveColoringFinder setLargestClique(Set<Integer> clique){
        solver.setExclusiveSet(clique);
        return this;
    }

    /**
     * Sets the maximum number of colors that the solution can use. By default it's the number of vertices.
     *
     * @param maxNumberOfColors The maximum number of usable colors
     *
     * @return This instance
     */
    public VertexNonRepetitiveColoringFinder setMaxNumberOfColors(int maxNumberOfColors){
        solver.setMaxNumberOfColors(maxNumberOfColors);
        return this;
    }

    public AbstractNonRepetitiveSolver.NonRepetitiveSolution compute() throws GRBException {
        return solver.compute();
    }
}
