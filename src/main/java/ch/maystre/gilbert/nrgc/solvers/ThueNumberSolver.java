package ch.maystre.gilbert.nrgc.solvers;

import ch.maystre.gilbert.nrgc.datastructures.Graph;
import ch.maystre.gilbert.nrgc.pathfinders.SeparatePathsFinder;
import gurobi.GRBException;

import java.util.List;

public class ThueNumberSolver {

    private Graph graph;


    private List<List<Integer>> paths;

    private AbstractNonRepetitiveSolver solver;

    public ThueNumberSolver(Graph graph){
        this.graph = graph;
        this.solver = new AbstractNonRepetitiveSolver(graph.size());
    }

    /**
     * Adds to the instance all separate paths provided by SeparatePathsFinder
     *
     * @return This instance
     */
    public ThueNumberSolver addSeparatePaths(){
        solver.setNonRepetitiveConstraints((new SeparatePathsFinder(graph)).popEverything());
        return this;
    }

    /**
     * Sets the paths to consider
     *
     * @param paths The paths that cannot be repetitive
     * @return This instance
     */
    public ThueNumberSolver setPaths(List<List<Integer>> paths){
        solver.setNonRepetitiveConstraints(paths);
        return this;
    }

    /**
     * Finds a large clique to accelerate the computation
     *
     * @return This instance
     */
    public ThueNumberSolver computeLargeClique(){
        //todo add clique support
        return this;
    }

    /**
     * Sets the maximum number of colors that the solution can use. By default it's the number of vertices.
     *
     * @param maxNumberOfColors The maximum number of color usable
     *
     * @return This instance
     */
    public ThueNumberSolver setMaxNumberOfColors(int maxNumberOfColors){
        solver.setMaxNumberOfColors(maxNumberOfColors);
        return this;
    }

    public AbstractNonRepetitiveSolver.NonRepetitiveSolution compute() throws GRBException {
        return solver.compute();
    }
}
