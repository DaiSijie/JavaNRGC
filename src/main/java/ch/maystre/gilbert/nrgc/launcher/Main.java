package ch.maystre.gilbert.nrgc.launcher;

import ch.maystre.gilbert.nrgc.datastructures.Graph;
import ch.maystre.gilbert.nrgc.io.Log;
import ch.maystre.gilbert.nrgc.io.Parser;
import ch.maystre.gilbert.nrgc.io.Reader;
import ch.maystre.gilbert.nrgc.pathfinders.SeparatePathsFinder;
import ch.maystre.gilbert.nrgc.pathfinders.TreePathsFinder;
import ch.maystre.gilbert.nrgc.solvers.ThueNumberSolver;
import gurobi.GRBException;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            String path = "/Users/gilbert/Desktop/Tree6/tree6.3.txt";
            String fst = (new Reader(path)).readNextLines(1).get(0);
            Graph g = Parser.parseTree(fst);
            List<List<Integer>> allPaths = (new SeparatePathsFinder(g)).popEverything();
            Log.printPaths(allPaths);


        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
