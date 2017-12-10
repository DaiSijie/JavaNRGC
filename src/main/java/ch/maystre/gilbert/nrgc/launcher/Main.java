package ch.maystre.gilbert.nrgc.launcher;

import ch.maystre.gilbert.nrgc.datastructures.Graph;
import ch.maystre.gilbert.nrgc.io.Log;
import ch.maystre.gilbert.nrgc.io.Parser;
import ch.maystre.gilbert.nrgc.io.Reader;
import ch.maystre.gilbert.nrgc.pathfinders.TreePathsFinder;
import ch.maystre.gilbert.nrgc.solvers.ThueNumberSolver;
import gurobi.GRBException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            String path = "/Users/gilbert/Desktop/Tree6/tree6.3.txt";
            String fst = (new Reader(path)).readNextLines(1).get(0);
            Graph g = Parser.parseTree(fst);
            Log.printPaths(TreePathsFinder.allPaths(g));
            ThueNumberSolver.computeThueNumber(g).prettyPrint();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (GRBException e) {
            e.printStackTrace();
        }
    }


}
