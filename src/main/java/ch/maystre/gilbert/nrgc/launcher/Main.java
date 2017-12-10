package ch.maystre.gilbert.nrgc.launcher;

import ch.maystre.gilbert.nrgc.datastructures.Graph;
import ch.maystre.gilbert.nrgc.io.Log;
import ch.maystre.gilbert.nrgc.io.Parser;
import ch.maystre.gilbert.nrgc.io.Reader;
import ch.maystre.gilbert.nrgc.pathfinders.SeparatePathsFinder;
import ch.maystre.gilbert.nrgc.pathfinders.TreePathsFinder;
import ch.maystre.gilbert.nrgc.solvers.AbstractNonRepetitiveSolver;
import ch.maystre.gilbert.nrgc.solvers.ThueIndexSolver;
import ch.maystre.gilbert.nrgc.solvers.ThueNumberSolver;
import gurobi.GRBException;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            String path = "/Users/gilbert/Desktop/Tree6/tree6.3.txt";
            String fst = (new Reader(path)).readNextLines(1).get(0);
            Graph tree = Parser.parseTree(fst);

            /*
             * compute the Thue index
             */
            List<List<Integer>> paths = TreePathsFinder.allPaths(tree);
            ThueIndexSolver edgeSolver = new ThueIndexSolver(tree, paths);
            Log.print(edgeSolver.compute());

            /*
             * compute the Thue number
             */
            ThueNumberSolver vertexSolver = new ThueNumberSolver(tree);
            Log.print(vertexSolver.addSeparatePaths().compute());
        } catch (IOException e) {
            e.printStackTrace();

        } catch (GRBException e) {
            e.printStackTrace();
        }
    }


}
